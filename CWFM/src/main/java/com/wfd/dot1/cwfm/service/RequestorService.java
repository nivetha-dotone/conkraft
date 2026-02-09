package com.wfd.dot1.cwfm.service;


import com.wfd.dot1.cwfm.dto.*;
import com.wfd.dot1.cwfm.pojo.Contractor;
import com.wfd.dot1.cwfm.pojo.ContractorRegistration;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@Service
public class RequestorService {


    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(RequestorService.class.getName());


    public String insertRequestorquery() {
        return QueryFileWatcher.getQuery("INSERT_REQUESTOR");
    }

    public String insertAttachmentQuery() {
        return QueryFileWatcher.getQuery("INSERT_ATTACHMENTQUERY");
    }

    public String updateRequestorquery() {
        return QueryFileWatcher.getQuery("UPDATE_REQUESTER_STATUS");
    }

    public String getTranscation() {
        return QueryFileWatcher.getQuery("GETTRANSCATION_VALUE");
    }

    public String getRequestorList() {
        return QueryFileWatcher.getQuery("GETREQUESTORLIST");
    }

    public String getAttachmentTRANSACTION() {
        return QueryFileWatcher.getQuery("GET_ATTACHMENTS_BY_TRANSACTION");
    }

    public String getAttachmentTRANSACTIONBYID() {
        return QueryFileWatcher.getQuery("GET_ATTACHMENTS_BY_ATTACHID");
    }

    public String getRequestorListForHR() {
        return QueryFileWatcher.getQuery("GETREQUESTORLISTFORHR");
    }

    public String getRequestorByID() {
        return QueryFileWatcher.getQuery("GETREQUESTORBYID");
    }


    public String insertRequestor(RequesterDto dto,MultipartFile files, String updatedBy) {
        try{
            Integer yadva=null;
            String ID = getTranscation();

            SqlRowSet rs = jdbcTemplate.queryForRowSet(ID);
            if(rs.next()){
                String uniqueNumber = rs.getString("UniqueNumber");
                dto.setTransactionId(uniqueNumber);
            }


            String query = insertRequestorquery();
            String filePath = uploadDocuments(files, dto.getTransactionId());
            dto.setAttachmentCv(filePath);
            dto.setFileName(files.getOriginalFilename());
            dto.setFileType(files.getContentType());
//            INSERT INTO REQUESTER (TransactionId, PrEmpId, ContractorId, Name, AadharNumber, ForPostId, AcademicId, AdditionalQualification, AttachmentCv, ShortNote, Status, UpdatedBy, UpdatedDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(),?);
            yadva = jdbcTemplate.update(query, dto.getTransactionId(), dto.getPrEmpId(), dto.getContractorId(), dto.getName(),
                    dto.getAadharNumber(), dto.getForPostId(), dto.getAcademicId(), dto.getAdditionalQualification(), dto.getAttachmentCv(),dto.getFileName(),dto.getFileType(), dto.getShortNote(), dto.getStatus(),
                    updatedBy);


            return String.valueOf(yadva);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void saveFile(MultipartFile file, String path) throws IOException {
        byte[] bytes = file.getBytes();
        Path filePath = Paths.get(path);
        Files.write(filePath, bytes);
    }

    private static final String ROOT_DIRECTORY = "D:/utkarsh/attachment_docs/";

    private static final String ROOT_DIRECTORYHR = "D:/utkarsh/TRMR_HR_DOC/";

    public String uploadDocuments(MultipartFile attachCV, String tranId) {
        try {
            if (attachCV == null || attachCV.isEmpty()) {
                return null;
            }

            // Sanitize file name: remove spaces and invalid characters
            String originalFileName = attachCV.getOriginalFilename();
            String safeFileName = originalFileName
                    .replaceAll("[^a-zA-Z0-9._-]", "_");  // keep only safe chars

            // Create folder based on tranId
            Path transactionFolder = Paths.get(ROOT_DIRECTORY, tranId);
            if (!Files.exists(transactionFolder)) {
                Files.createDirectories(transactionFolder);
            }

            // Final file path inside transaction folder
            Path filePath = transactionFolder.resolve(safeFileName);

            // Save the file
            saveFile(attachCV, filePath.toString());

            // Return stored file path (relative or absolute)
            return filePath.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return "failed";
        }
    }





    public List<RequestorListDTO> getListOfRequestor(String userAccount,HttpServletRequest request) {
        try {
            log.info("Fetching Requestor list");
            String fetchListRequestor =  getRequestorList();
            if (userAccount == null || userAccount.trim().isEmpty()) {
                throw new RuntimeException("User account is missing (null). Cannot fetch requestors.");
            }
            SqlRowSet rs = jdbcTemplate.queryForRowSet(fetchListRequestor,userAccount);
            List<RequestorListDTO> peList = new ArrayList<>();

            while (rs.next()) {
                RequestorListDTO dto = new RequestorListDTO();

                dto.setTransactionId(rs.getString("TransactionId"));
                dto.setUnitID(getIntOrNull(rs, "PrEmpId"));
                dto.setUnitName(rs.getString("PrEmpName"));
                dto.setContractorId(getIntOrNull(rs, "ContractorId"));
                dto.setContractName(rs.getString("ContractorName"));
                dto.setName(rs.getString("Name"));
                dto.setAadharNumber(rs.getString("AadharNumber"));
                dto.setForPostId(rs.getString( "ForPostId"));
                dto.setAcademicId(getIntOrNull(rs, "AcademicId"));
                dto.setAcademicString(rs.getString("AcademicName"));
                dto.setAdditionalQualification(rs.getString("AdditionalQualification"));
                dto.setFileName(rs.getString("file_name"));
                dto.setFileType(rs.getString("file_type"));
                dto.setShortNote(rs.getString("ApproveBy"));
                dto.setAttachmentCv(generateFileUrl(rs.getString("TransactionId")));
                Boolean status = null;
                boolean value = rs.getBoolean("Status");
                if (!rs.wasNull()) {
                    status = value;
                }
                dto.setStatus(status);
                dto.setUpdatedDate(rs.getString("UpdatedDate"));
                dto.setUpdatedBy(rs.getString("UpdatedBy"));
                dto.setApproveBy(rs.getString("ApproveBy"));
                dto.setRemarkApprover(rs.getString("RemarkApprover"));
                dto.setRequesterAttachmentDTOList(getAttachmentsForTransaction(rs.getString("TransactionId")));



// Fetch attachments for this transactionId
//                dto.setRequesterAttachmentDTOList(getAttachmentsForTransaction(dto.getTransactionId()));




                peList.add(dto);
            }
            log.info("Fetched {} requestors", peList.size());
            return peList;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching requestor list", e);
        }

    }

    private List<RequesterAttachmentDTO> getAttachmentsForTransaction(String transactionId) {
        String sql = getAttachmentTRANSACTION();

        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql,transactionId);
            ArrayList<RequesterAttachmentDTO> pass = new ArrayList<>();
        while (rs.next()) {
            RequesterAttachmentDTO att = new RequesterAttachmentDTO();
            att.setAttachmentId(rs.getString("AttachmentId"));
            att.setTransactionId(rs.getString("TransactionId"));
            att.setFileName(rs.getString("FileName"));

            att.setFilePath(generateFileUrlFORHRDOC(att.getAttachmentId()));

            att.setFileType(rs.getString("FileType"));
            att.setUploadedDate(rs.getTimestamp("UploadedDate").toString());
            att.setUploadedBy(rs.getString("UploadedBy"));
            pass.add(att);



        }
        return pass;


        }

    private String generateFileUrlFORHRDOC(String filePath) {
        if (filePath == null) return null;

        // Extract just the filename
        String fileName = Paths.get(filePath).getFileName().toString();

        // Build public URL (adjust localhost/port if needed)
        return "http://localhost:8080/CWFM/RequestorCon/getHRDOC/"+ fileName;
    }

    private Integer getIntOrNull(SqlRowSet rs, String column) {
        String value = rs.getString(column);
        return (value != null) ? Integer.valueOf(value) : null;
    }

    private String generateFileUrl(String filePath) {
        if (filePath == null) return null;

        // Extract just the filename
        String fileName = Paths.get(filePath).getFileName().toString();

        // Build public URL (adjust localhost/port if needed)
        return "http://localhost:8080/CWFM/RequestorCon/getAttachCV/"+ fileName;
    }

    public ResponseEntity<Resource>  callFileUrlHR(String attachId, HttpServletRequest request)  {
        try{

            String sql = getAttachmentTRANSACTIONBYID();
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql,attachId);
            RequesterAttachmentDTO att =null;
            if(rs.next()) {

              att=  new RequesterAttachmentDTO();
                att.setAttachmentId(rs.getString("AttachmentId"));
                att.setTransactionId(rs.getString("TransactionId"));
                att.setFileName(rs.getString("FileName"));
                att.setFilePath(rs.getString("FilePath"));
                att.setFileType(rs.getString("FileType"));
                att.setUploadedDate(rs.getTimestamp("UploadedDate").toString());
                att.setUploadedBy(rs.getString("UploadedBy"));
                Path filePaths = Paths.get(att.getFilePath());

                Resource resource = new UrlResource(filePaths.toUri());
                if (resource.exists() || resource.isReadable()) {
                    return ResponseEntity.ok()
                            .contentType(MediaType.parseMediaType(att.getFileType()))
                            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + att.getFileName() + "\"")
                            .body(resource);
                }else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                }

            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<Resource>  callFileUrl(String trnID, HttpServletRequest request) throws MalformedURLException {

        String requestorByID = getRequestorByID();
        SqlRowSet rs = jdbcTemplate.queryForRowSet(requestorByID,trnID);
        RequestorListDTO dto = null;

        if(rs.next()){

         dto= new RequestorListDTO();

        dto.setTransactionId(rs.getString("TransactionId"));
        dto.setUnitID(getIntOrNull(rs, "PrEmpId"));
        dto.setUnitName(rs.getString("PrEmpName"));
        dto.setContractorId(getIntOrNull(rs, "ContractorId"));
        dto.setContractName(rs.getString("ContractorName"));
        dto.setName(rs.getString("Name"));
        dto.setAadharNumber(rs.getString("AadharNumber"));
        dto.setForPostId(rs.getString( "ForPostId"));
        dto.setAcademicId(getIntOrNull(rs, "AcademicId"));
        dto.setAcademicString(rs.getString("AcademicName"));
        dto.setAdditionalQualification(rs.getString("AdditionalQualification"));
        dto.setFileName(rs.getString("file_name"));
        dto.setFileType(rs.getString("file_type"));
        dto.setShortNote(rs.getString("ShortNote"));
        dto.setAttachmentCv(rs.getString("AttachmentCV"));
            Boolean status = null;
            boolean value = rs.getBoolean("Status");  // returns false if 0 or NULL
            if (!rs.wasNull()) {
                status = value;  // true or false
            }
            dto.setStatus(status);

        dto.setUpdatedDate(rs.getString("UpdatedDate")); // could also map to Timestamp
        dto.setUpdatedBy(rs.getString("UpdatedBy"));
            dto.setApproveBy(rs.getString("ApproveBy"));
            dto.setRemarkApprover(rs.getString("RemarkApprover"));

            Path filePaths = Paths.get(dto.getAttachmentCv());

        Resource resource = new UrlResource(filePaths.toUri());
        if (resource.exists() || resource.isReadable()) {
            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dto.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\""+dto.getFileName() +"\"")
                .body(resource);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
}else{
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

}

}

    public RequestorListDTO  findSingle(String trnID) {

        String requestorByID = getRequestorByID();
        SqlRowSet rs = jdbcTemplate.queryForRowSet(requestorByID,trnID);
        RequestorListDTO dto = null;

        if(rs.next()){

         dto= new RequestorListDTO();

        dto.setTransactionId(rs.getString("TransactionId"));
        dto.setUnitID(getIntOrNull(rs, "PrEmpId"));
        dto.setUnitName(rs.getString("PrEmpName"));
        dto.setContractorId(getIntOrNull(rs, "ContractorId"));
        dto.setContractName(rs.getString("ContractorName"));
        dto.setName(rs.getString("Name"));
        dto.setAadharNumber(rs.getString("AadharNumber"));
        dto.setForPostId(rs.getString( "ForPostId"));
        dto.setAcademicId(getIntOrNull(rs, "AcademicId"));
        dto.setAcademicString(rs.getString("AcademicName"));
        dto.setAdditionalQualification(rs.getString("AdditionalQualification"));
        dto.setFileName(rs.getString("file_name"));
        dto.setFileType(rs.getString("file_type"));
        dto.setShortNote(rs.getString("ShortNote"));
        dto.setAttachmentCv(generateFileUrl(rs.getString("TransactionId")));
            Boolean status = null;
            boolean value = rs.getBoolean("Status");  // returns false if 0 or NULL
            if (!rs.wasNull()) {
                status = value;  // true or false
            }
            dto.setStatus(status);
//        dto.setStatus(rs.getBoolean( "Status"));
            dto.setUpdatedDate(rs.getString("UpdatedDate")); // could also map to Timestamp
            dto.setUpdatedBy(rs.getString("UpdatedBy"));
            dto.setApproveBy(rs.getString("ApproveBy"));
            dto.setRemarkApprover(rs.getString("RemarkApprover"));
            dto.setRequesterAttachmentDTOList(getAttachmentsForTransaction(dto.getTransactionId()));


        }
        return dto;

    }

    public List<RequestorListDTO> getRequestorListHR(String userAccount) {
        try {
            log.info("Fetching Requestor list");
            String fetchListRequestor = getRequestorListForHR();

            SqlRowSet rs = jdbcTemplate.queryForRowSet(fetchListRequestor,userAccount);
            List<RequestorListDTO> peList = new ArrayList<>();

            while (rs.next()) {
                RequestorListDTO dto = new RequestorListDTO();

                dto.setTransactionId(rs.getString("TransactionId"));
                dto.setUnitID(getIntOrNull(rs, "PrEmpId"));
                dto.setUnitName(rs.getString("PrEmpName"));
                dto.setContractorId(getIntOrNull(rs, "ContractorId"));
                dto.setContractName(rs.getString("ContractorName"));
                dto.setName(rs.getString("Name"));
                dto.setAadharNumber(rs.getString("AadharNumber"));
                dto.setForPostId(rs.getString( "ForPostId"));
                dto.setAcademicId(getIntOrNull(rs, "AcademicId"));
                dto.setAcademicString(rs.getString("AcademicName"));
                dto.setAdditionalQualification(rs.getString("AdditionalQualification"));
                dto.setFileName(rs.getString("file_name"));
                dto.setFileType(rs.getString("file_type"));
                dto.setShortNote(rs.getString("ApproveBy"));

                dto.setAttachmentCv(generateFileUrl(rs.getString("TransactionId")));
                Boolean status = null;
                boolean value = rs.getBoolean("Status");  // returns false if 0 or NULL
                if (!rs.wasNull()) {
                    status = value;  // true or false
                }
                dto.setStatus(status);
//                dto.setStatus(rs.getBoolean( "Status"));

                dto.setUpdatedDate(rs.getString("UpdatedDate")); // could also map to Timestamp
                dto.setUpdatedBy(rs.getString("UpdatedBy"));
                dto.setApproveBy(rs.getString("ApproveBy"));
                dto.setRemarkApprover(rs.getString("RemarkApprover"));
                dto.setRequesterAttachmentDTOList(getAttachmentsForTransaction(dto.getTransactionId()));

                peList.add(dto);
            }
            log.info("Fetched {} requestors", peList.size());
            return peList;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching requestor list", e);
        }

    }

    public String updateRequestor(UpdateRequestDTO dto,String userAccount) {
        try{
            Integer yadva=null;

            String query = updateRequestorquery();

            yadva = jdbcTemplate.update(
                    query,
                    dto.getStatus(),
                    userAccount,
                    dto.getReMark(),
                    dto.getTransactionId()
            );
            return String.valueOf(yadva);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String updateRequestor(UpdateRequestDTO dto, String userAccount, MultipartFile[] files) {
        try {
            // 1. Update REQUESTER row
            String query = updateRequestorquery();

            String updatedBy = (dto.getAORstatusBy() != null && !dto.getAORstatusBy().isEmpty())
                    ? dto.getAORstatusBy()
                    : userAccount;

            int updated = jdbcTemplate.update(
                    query,
                    dto.getStatus(),
                    updatedBy,
                    dto.getReMark(),
                    dto.getTransactionId()
            );

            // 2. Save attachments if provided
            if (files != null && files.length > 0) {
                int counter = 1;

                for (MultipartFile file : files) {

                    if (!file.isEmpty()) {
                        String baseName = file.getOriginalFilename().contains(".")
                                ? file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf('.'))
                                : file.getOriginalFilename();
                        String extension = file.getOriginalFilename().contains(".")
                                ? file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'))
                                : "";

                        String newFileName = baseName + "_" + counter + extension;
                        counter++;

                        saveAttachment(dto.getTransactionId(), file, updatedBy, newFileName);
                    }
                }
            }

            return String.valueOf(updated);

        } catch (Exception e) {
            throw new RuntimeException("Error updating requestor", e);
        }
    }

    public int saveAttachment(String transactionId, MultipartFile file, String uploadedBy , String fileName) {
        try {
            // Store the file physically
            String filePath = uploadDocumentsforHR(file, transactionId,fileName);

            // Prepare DB insert query
            String insertAttachmentQuery = insertAttachmentQuery();

            return jdbcTemplate.update(insertAttachmentQuery,
                    transactionId,
                    fileName,
                    filePath,
                    file.getContentType(),
                    uploadedBy
            );

        } catch (Exception e) {
            throw new RuntimeException("Error saving attachment", e);
        }
    }

    public String uploadDocumentsforHR(MultipartFile attachCV, String tranId, String fileName) {
        try {
            if (attachCV == null || attachCV.isEmpty()) {
                return null;
            }

            // Sanitize file name: remove spaces and invalid characters
            String safeFileName = fileName
                    .replaceAll("[^a-zA-Z0-9._-]", "_");  // keep only safe chars

            // Create folder based on tranId (only once, skip if exists)
            Path transactionFolder = Paths.get(ROOT_DIRECTORYHR, tranId);
            if (!Files.exists(transactionFolder)) {
                Files.createDirectories(transactionFolder);
            }

            // Final file path inside transaction folder
            Path filePath = transactionFolder.resolve(safeFileName);

            // Save the file
            saveFile(attachCV, filePath.toString());

            // Return stored file path (relative or absolute)
            return filePath.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return "failed";
        }
    }

    public String getDropdownOfContractor() {
        return QueryFileWatcher.getQuery("GET_LIST_OF_CONTRACTOR");
    }

    public List<Contractor> getAllContractor(String userAccour, String unitId) {
       try{


        String fetchListState = getDropdownOfContractor();

        SqlRowSet rs = jdbcTemplate.queryForRowSet(fetchListState,userAccour,unitId);


        ArrayList<Contractor> pass = new ArrayList<>();
        while (rs.next()) {
            Contractor addCon =new Contractor();
            addCon.setContractorId(rs.getString("conId"));
            addCon.setContractorName(rs.getString("name"));
            pass.add(addCon);
        }
        return pass;

       } catch (Exception e) {
           throw new RuntimeException(e);
       }







    }







}
