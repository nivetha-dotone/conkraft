package com.wfd.dot1.cwfm.service;

import com.wfd.dot1.cwfm.dao.FileUploadDao;
import com.wfd.dot1.cwfm.dto.KTCWorkorderDTO;
import com.wfd.dot1.cwfm.pojo.KTCWorkorderStaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JsonUploadService {

    @Autowired
    private FileUploadDao fileUploadDao;

    private static final Logger log = LoggerFactory.getLogger(JsonUploadService.class);

    @Transactional
    public Map<String, Object> processWorkordersFromJson(List<KTCWorkorderDTO> dtoList) {

        List<Map<String, Object>> successData = new ArrayList<>();
        List<Map<String, Object>> errorData = new ArrayList<>();
        List<KTCWorkorderStaging> stagingList = new ArrayList<>();

        int rowNum = 0;

        for (KTCWorkorderDTO dto : dtoList) {
            rowNum++;

            try {

                // Mandatory validation
                if (dto.getWorkOrderNumber() == null || dto.getWorkOrderNumber().isBlank()) {
                    throw new RuntimeException("workOrderNumber is mandatory");
                }

                if (dto.getVendorCode() == null || dto.getVendorCode().isBlank()) {
                    throw new RuntimeException("vendorCode is mandatory");
                }

                if (dto.getPlantcode() == null || dto.getPlantcode().isBlank()) {
                    throw new RuntimeException("plantcode is mandatory");
                }

                KTCWorkorderStaging staging = new KTCWorkorderStaging();

                staging.setWorkOrderNumber(dto.getWorkOrderNumber());
                staging.setItem(dto.getItem());
                staging.setLine(dto.getLine());
                staging.setLineNumber(dto.getLineNumber());
                staging.setServiceCode(dto.getServiceCode());
                staging.setShortText(dto.getShortText());
                staging.setDeliveryCompletion(dto.getDeliveryCompletion());
                staging.setItemChangedON(dto.getItemChangedON());
                staging.setVendorCode(dto.getVendorCode());
                staging.setVendorName(dto.getVendorName());
                staging.setVendorAddress(dto.getVendorAddress());
                staging.setBlockedVendor(dto.getBlockedVendor());
                staging.setWorkOrderValiditiyFrom(dto.getWorkOrderValiditiyFrom());
                staging.setWorkOrderValiditiyTo(dto.getWorkOrderValiditiyTo());
                staging.setWorkOrderType(dto.getWorkOrderType());
                staging.setPlantcode(dto.getPlantcode());
                staging.setSectionCode(dto.getSectionCode());
                staging.setDepartmentCode(dto.getDepartmentCode());
                staging.setGLCode(dto.getGLCode());
                staging.setCostCenter(dto.getCostCenter());
                staging.setNatureofJob(dto.getNatureofJob());
                staging.setRateUnit(
                        dto.getRateUnit() == null || dto.getRateUnit().isBlank() ? "0" : dto.getRateUnit());
                staging.setQuantity(
                        dto.getQuantity() == null || dto.getQuantity().isBlank() ? "0" : dto.getQuantity());
                staging.setBaseUnitofMeasure(dto.getBaseUnitofMeasure());
                staging.setWorkOrderReleased(dto.getWorkOrderReleased());
                staging.setPMOrderNo(dto.getPMOrderNo());
                staging.setWBSElement(dto.getWBSElement());
                staging.setQtyCompleted(dto.getQtyCompleted());
                staging.setWorkOrderReleaseDate(dto.getWorkOrderReleaseDate());
                staging.setServiceEntryCreatedDate(dto.getServiceEntryCreatedDate());
                staging.setServiceEntryUpdatedDate(dto.getServiceEntryUpdatedDate());
                staging.setPurchaseOrgLevel(dto.getPurchaseOrgLevel());
                staging.setCompanycode(dto.getCompanycode());

                if (fileUploadDao.workorderExists(
                        dto.getWorkOrderNumber(),
                        dto.getVendorCode(),
                        dto.getPlantcode(),
                        dto.getItem(),
                        dto.getLine(),
                        dto.getLineNumber())) {

                    fileUploadDao.updateWorkorderToStaging(staging);
                } else {
                    fileUploadDao.saveWorkorderToStaging(staging);
                }

                stagingList.add(staging);
                successData.add(Map.of("row", rowNum, "message", "Processed Successfully"));

            } catch (Exception e) {
                errorData.add(Map.of("row", rowNum, "error", e.getMessage()));
            }
        }

        InsertWorkorderOrgLevelEntry(stagingList);

        try {
            fileUploadDao.callWorkorderProcessingSP();
        } catch (Exception e) {
            errorData.add(Map.of("row", "Procedure", "error",
                    "Stored Procedure Failed: " + e.getMessage()));
        }

        Map<String, Object> result = new HashMap<>();
        result.put("successData", successData);
        result.put("errorData", errorData);

        return result;
    }

    @Transactional
    public boolean InsertWorkorderOrgLevelEntry(List<KTCWorkorderStaging> list) {

        if (list == null || list.isEmpty()) {
            return true; // nothing to insert
        }

        try {
            long orgLevelDefId = fileUploadDao.getOrgLevelDefId("work order");

            if (!logAndCheck("ORGLEVELDEF", orgLevelDefId > 0)) {
                return false;
            }

            boolean saved = fileUploadDao.SaveWorkorderOrglevelEntry(list, orgLevelDefId);

            if (!logAndCheck("ORGLEVELENTRY", saved)) {
                return false;
            }

            return true;

        } catch (Exception e) {
            log.error("ORGLEVELENTRY Batch Insert FAILED : " + e.getMessage(), e);
            return false;
        }
    }

    private boolean logAndCheck(String label, boolean success) {
        log.info(label + " : " + (success ? "SUCCESS" : "FAILED"));
        return success;
    }
}




