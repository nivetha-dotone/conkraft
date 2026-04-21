package com.wfd.dot1.cwfm.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wfd.dot1.cwfm.dao.ReconciliationDao;
import com.wfd.dot1.cwfm.dto.ChallanEmployeeDTO;
import com.wfd.dot1.cwfm.dto.ReconciliationMismatchDTO;
import com.wfd.dot1.cwfm.dto.ReconciliationResultDTO;
import com.wfd.dot1.cwfm.dto.WorkmenReconciliationDTO;

import jakarta.servlet.ServletContext;

@Service
public class ReconciliationServiceImpl implements ReconciliationService {

    @Autowired
    private ReconciliationDao reconciliationDao;

    @Autowired
    private ServletContext servletContext;

    @Override
    public List<WorkmenReconciliationDTO> getContractorWorkmenList(Long contractorId) {
        return reconciliationDao.getContractorWorkmenList(contractorId);
    }

    @Override
    public ReconciliationResultDTO processReconciliation(Long contractorId, String reconType,
                                                         MultipartFile file, String uploadedBy) throws Exception {

        ReconciliationResultDTO result = new ReconciliationResultDTO();

        if (file == null || file.isEmpty()) {
            result.setStatus("UNVERIFIED");
            result.setMessage("Please upload a valid challan file.");
            return result;
        }

        String uploadDir = servletContext.getRealPath("/") + File.separator + "recon_uploads";
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String originalFileName = file.getOriginalFilename();
        String savedFilePath = uploadDir + File.separator + System.currentTimeMillis() + "_" + originalFileName;
        File dest = new File(savedFilePath);
        file.transferTo(dest);

        List<WorkmenReconciliationDTO> dbList = reconciliationDao.getContractorWorkmenList(contractorId);
        List<ChallanEmployeeDTO> documentList = parseChallanDocument(dest, reconType);

        List<ReconciliationMismatchDTO> mismatchList = compareData(dbList, documentList, reconType);

        int totalCount = dbList.size();
        int unverifiedCount = mismatchList.size();
        int verifiedCount = totalCount - unverifiedCount;
        if (verifiedCount < 0) {
            verifiedCount = 0;
        }

        String overallStatus = unverifiedCount == 0 ? "VERIFIED" : "UNVERIFIED";

        Long uploadId = reconciliationDao.saveUploadMaster(
                contractorId,
                reconType,
                originalFileName,
                savedFilePath,
                overallStatus,
                totalCount,
                verifiedCount,
                unverifiedCount,
                uploadedBy
        );

        if (!mismatchList.isEmpty()) {
            reconciliationDao.saveMismatchList(uploadId, mismatchList);
        }

        result.setUploadId(uploadId);
        result.setStatus(overallStatus);
        result.setTotalCount(totalCount);
        result.setVerifiedCount(verifiedCount);
        result.setUnverifiedCount(unverifiedCount);
        result.setMismatchList(mismatchList);
        result.setMessage("Reconciliation completed successfully.");

        return result;
    }

    private List<ReconciliationMismatchDTO> compareData(List<WorkmenReconciliationDTO> dbList,
                                                        List<ChallanEmployeeDTO> docList,
                                                        String reconType) {

        List<ReconciliationMismatchDTO> mismatchList = new ArrayList<>();
        Map<String, ChallanEmployeeDTO> documentMap = new HashMap<>();

        for (ChallanEmployeeDTO dto : docList) {
            String key = "PF".equalsIgnoreCase(reconType)
                    ? safe(dto.getPfNumber())
                    : safe(dto.getEsicNumber());

            if (!key.isEmpty()) {
                documentMap.put(key, dto);
            }
        }

        for (WorkmenReconciliationDTO db : dbList) {

            String dbNumber = "PF".equalsIgnoreCase(reconType)
                    ? safe(db.getPfNumber())
                    : safe(db.getEsicNumber());

            BigDecimal dbAmount = "PF".equalsIgnoreCase(reconType)
                    ? safeAmount(db.getPfPrice())
                    : safeAmount(db.getEsicPrice());

            ChallanEmployeeDTO doc = documentMap.get(dbNumber);

            if (doc == null) {
                ReconciliationMismatchDTO mm = new ReconciliationMismatchDTO();
                mm.setGatePassId(db.getGatePassId());
                mm.setWorkmenName(db.getWorkmenName());
                mm.setDbNumber(dbNumber);
                mm.setDocNumber(null);
                mm.setDbAmount(dbAmount);
                mm.setDocAmount(null);
                mm.setMismatchReason(reconType + " number not found in uploaded challan");
                mm.setReconType(reconType);
                mismatchList.add(mm);
                continue;
            }

            boolean amountMismatch = dbAmount.compareTo(safeAmount(doc.getAmount())) != 0;
            boolean nameMismatch = !safe(db.getWorkmenName()).equalsIgnoreCase(safe(doc.getWorkmenName()));

            if (amountMismatch || nameMismatch) {
                ReconciliationMismatchDTO mm = new ReconciliationMismatchDTO();
                mm.setGatePassId(db.getGatePassId());
                mm.setWorkmenName(db.getWorkmenName());
                mm.setDbNumber(dbNumber);
                mm.setDocNumber(dbNumber);
                mm.setDbAmount(dbAmount);
                mm.setDocAmount(safeAmount(doc.getAmount()));

                StringBuilder reason = new StringBuilder();
                if (nameMismatch) {
                    reason.append("Name mismatch");
                }
                if (amountMismatch) {
                    if (reason.length() > 0) {
                        reason.append(", ");
                    }
                    reason.append("Amount mismatch");
                }

                mm.setMismatchReason(reason.toString());
                mm.setReconType(reconType);
                mismatchList.add(mm);
            }
        }

        return mismatchList;
    }

    private List<ChallanEmployeeDTO> parseChallanDocument(File file, String reconType) throws Exception {
        String fileName = file.getName().toLowerCase();

        if (fileName.endsWith(".xlsx") || fileName.endsWith(".xls")) {
            return parseExcelChallan(file, reconType);
        } else if (fileName.endsWith(".csv")) {
            return parseCsvChallan(file, reconType);
        } else if (fileName.endsWith(".pdf")) {
            return parsePdfChallan(file, reconType);
        } else {
            throw new IllegalArgumentException("Unsupported file format. Please upload PDF, XLS, XLSX or CSV.");
        }
    }

    private List<ChallanEmployeeDTO> parseExcelChallan(File file, String reconType) throws Exception {
        List<ChallanEmployeeDTO> list = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                return list;
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                return list;
            }

            Map<String, Integer> columnMap = buildColumnIndexMap(headerRow);

            Integer nameCol = findColumn(columnMap, "name", "employee name", "member name", "workmen name");
            Integer pfCol = findColumn(columnMap, "pf number", "pfno", "pf", "member id");
            Integer esicCol = findColumn(columnMap, "esic number", "esicno", "esic", "ip number");
            Integer amountCol = findColumn(columnMap, "amount", "total contribution", "price", "total", "contribution");

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null || isRowEmpty(row)) {
                    continue;
                }

                ChallanEmployeeDTO dto = new ChallanEmployeeDTO();

                if (nameCol != null) {
                    dto.setWorkmenName(getCellStringValue(row.getCell(nameCol)));
                }

                if ("PF".equalsIgnoreCase(reconType)) {
                    if (pfCol != null) {
                        dto.setPfNumber(cleanString(getCellStringValue(row.getCell(pfCol))));
                    }
                } else if ("ESIC".equalsIgnoreCase(reconType)) {
                    if (esicCol != null) {
                        dto.setEsicNumber(cleanString(getCellStringValue(row.getCell(esicCol))));
                    }
                }

                if (amountCol != null) {
                    dto.setAmount(parseBigDecimal(getCellStringValue(row.getCell(amountCol))));
                }

                if (isValidChallanRow(dto, reconType)) {
                    list.add(dto);
                }
            }
        }

        return list;
    }

    private List<ChallanEmployeeDTO> parseCsvChallan(File file, String reconType) throws Exception {
        List<ChallanEmployeeDTO> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

            String headerLine = br.readLine();
            if (headerLine == null || headerLine.trim().isEmpty()) {
                return list;
            }

            String[] headers = splitCsvLine(headerLine);
            Map<String, Integer> columnMap = new HashMap<>();
            for (int i = 0; i < headers.length; i++) {
                columnMap.put(normalizeHeader(headers[i]), i);
            }

            Integer nameCol = findColumn(columnMap, "name", "employee name", "member name", "workmen name");
            Integer pfCol = findColumn(columnMap, "pf number", "pfno", "pf", "member id");
            Integer esicCol = findColumn(columnMap, "esic number", "esicno", "esic", "ip number");
            Integer amountCol = findColumn(columnMap, "amount", "total contribution", "price", "total", "contribution");

            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] values = splitCsvLine(line);
                ChallanEmployeeDTO dto = new ChallanEmployeeDTO();

                if (nameCol != null && nameCol < values.length) {
                    dto.setWorkmenName(values[nameCol].trim());
                }

                if ("PF".equalsIgnoreCase(reconType)) {
                    if (pfCol != null && pfCol < values.length) {
                        dto.setPfNumber(cleanString(values[pfCol]));
                    }
                } else if ("ESIC".equalsIgnoreCase(reconType)) {
                    if (esicCol != null && esicCol < values.length) {
                        dto.setEsicNumber(cleanString(values[esicCol]));
                    }
                }

                if (amountCol != null && amountCol < values.length) {
                    dto.setAmount(parseBigDecimal(values[amountCol]));
                }

                if (isValidChallanRow(dto, reconType)) {
                    list.add(dto);
                }
            }
        }

        return list;
    }

    private List<ChallanEmployeeDTO> parsePdfChallan(File file, String reconType) throws Exception {
        List<ChallanEmployeeDTO> list = new ArrayList<>();

        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);

            if (text == null || text.trim().isEmpty()) {
                return list;
            }

            String[] lines = text.split("\\r?\\n");

            for (String line : lines) {
                if (line == null || line.trim().isEmpty()) {
                    continue;
                }

                ChallanEmployeeDTO dto = parsePdfLine(line, reconType);
                if (dto != null && isValidChallanRow(dto, reconType)) {
                    list.add(dto);
                }
            }
        }

        return list;
    }

    private ChallanEmployeeDTO parsePdfLine(String line, String reconType) {
        line = line.replaceAll("\\s+", " ").trim();

        if (line.isEmpty()) {
            return null;
        }

        ChallanEmployeeDTO dto = new ChallanEmployeeDTO();
        Pattern pattern = Pattern.compile("^(.*?)\\s+([A-Za-z0-9/\\-]+)\\s+(\\d+(?:\\.\\d{1,2})?)$");
        Matcher matcher = pattern.matcher(line);

        if (!matcher.find()) {
            return null;
        }

        dto.setWorkmenName(matcher.group(1).trim());
        dto.setAmount(parseBigDecimal(matcher.group(3)));

        if ("PF".equalsIgnoreCase(reconType)) {
            dto.setPfNumber(cleanString(matcher.group(2)));
        } else if ("ESIC".equalsIgnoreCase(reconType)) {
            dto.setEsicNumber(cleanString(matcher.group(2)));
        } else {
            return null;
        }

        return dto;
    }

    private Map<String, Integer> buildColumnIndexMap(Row headerRow) {
        Map<String, Integer> columnMap = new HashMap<>();

        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            Cell cell = headerRow.getCell(i);
            String header = normalizeHeader(getCellStringValue(cell));
            if (!header.isEmpty()) {
                columnMap.put(header, i);
            }
        }

        return columnMap;
    }

    private Integer findColumn(Map<String, Integer> columnMap, String... aliases) {
        for (String alias : aliases) {
            String key = normalizeHeader(alias);
            if (columnMap.containsKey(key)) {
                return columnMap.get(key);
            }
        }
        return null;
    }

    private String normalizeHeader(String value) {
        if (value == null) {
            return "";
        }
        return value.trim()
                .toLowerCase()
                .replaceAll("[^a-z0-9]", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }

    private String getCellStringValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();

            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    double value = cell.getNumericCellValue();
                    if (value == (long) value) {
                        return String.valueOf((long) value);
                    }
                    return String.valueOf(value);
                }

            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());

            case FORMULA:
                try {
                    return cell.getStringCellValue().trim();
                } catch (Exception e) {
                    try {
                        double value = cell.getNumericCellValue();
                        if (value == (long) value) {
                            return String.valueOf((long) value);
                        }
                        return String.valueOf(value);
                    } catch (Exception ex) {
                        return "";
                    }
                }

            default:
                return "";
        }
    }

    private boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }

        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null && !getCellStringValue(cell).trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private BigDecimal parseBigDecimal(String value) {
        if (value == null || value.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }

        value = value.replaceAll(",", "").trim();

        try {
            return new BigDecimal(value);
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    private String cleanString(String value) {
        return value == null ? "" : value.trim().replaceAll("\\s+", "");
    }

    private boolean isValidChallanRow(ChallanEmployeeDTO dto, String reconType) {
        if (dto == null) {
            return false;
        }

        if ("PF".equalsIgnoreCase(reconType)) {
            return dto.getPfNumber() != null && !dto.getPfNumber().trim().isEmpty();
        } else if ("ESIC".equalsIgnoreCase(reconType)) {
            return dto.getEsicNumber() != null && !dto.getEsicNumber().trim().isEmpty();
        }

        return false;
    }

    private String[] splitCsvLine(String line) {
        return line.split("\\s*,\\s*");
    }

    private String safe(String val) {
        return val == null ? "" : val.trim();
    }

    private BigDecimal safeAmount(BigDecimal val) {
        return val == null ? BigDecimal.ZERO : val;
    }
}