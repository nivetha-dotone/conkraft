package com.wfd.dot1.cwfm.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wfd.dot1.cwfm.controller.CreateEmpFetchByGatePassAPICALL;
import com.wfd.dot1.cwfm.dao.CommonDao;
import com.wfd.dot1.cwfm.dao.FileUploadDao;
import com.wfd.dot1.cwfm.dao.WorkmenBulkUploadDao;
import com.wfd.dot1.cwfm.dao.WorkmenDao;
import com.wfd.dot1.cwfm.dto.GatePassStatusLogDto;
import com.wfd.dot1.cwfm.enums.GatePassStatus;
import com.wfd.dot1.cwfm.enums.GatePassType;
import com.wfd.dot1.cwfm.enums.WorkFlowType;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.GatePassMain;
import com.wfd.dot1.cwfm.pojo.WorkmenBulkUpload;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;

@Service
public class WorkmenBulkUploadServiceImpl implements WorkmenBulkUploadService {
	
	@Autowired
    private WorkmenBulkUploadDao workmenUploadDao;
	
	@Autowired
	WorkmenDao workmenDao;
	
	@Autowired
	WorkmenService workmenService;
	
	@Autowired
    private FileUploadDao fileUploadDao;

	@Override
	public List<WorkmenBulkUpload> getAllWorkmenBulkUploadData() {
		return workmenUploadDao.getAllWorkmenBulkUploadData();
	}
	@Override
	public Map<String, Object> validateAndSaveSelectedWorkmen(List<Integer> transactionIds,String createdBy) {
	    List<Map<String, Object>> successData = new ArrayList<>();
	    List<Map<String, Object>> errorData = new ArrayList<>();

	    for (Integer txnId : transactionIds) {
	        WorkmenBulkUpload record = workmenUploadDao.getByTransactionId(txnId);

	        if (record == null) {
	            String errMsg = "No record found for transactionId: " + txnId;
	            errorData.add(Map.of("transactionId", txnId, "error", errMsg));
	            workmenUploadDao.updateRecordStatusByTransactionId(txnId, errMsg);
	            continue;
	        }

	        Map<String, String> fieldErrors = new LinkedHashMap<>();

	        

	        // ✅ Mandatory + business validations (you can modularize these too)
	        if (isBlank(record.getFirstName())) fieldErrors.put("firstName", "Missing");
	        if (isBlank(record.getLastName())) fieldErrors.put("lastName", "Missing");
	        if (isBlank(record.getRelationName())) fieldErrors.put("relationName", "Missing");
	        if (isBlank(record.getAadhaarNumber())) fieldErrors.put("aadhaarNumber", "Missing");
	        //if (isBlank(record.getIdMark())) fieldErrors.put("IdMark", "Missing");
	        String aadhaar = record.getAadhaarNumber();

	        if (!isBlank(aadhaar)) {
	            if (!aadhaar.matches("\\d{12}")) {
	                fieldErrors.put("aadhaarNumber", "Must be 12 digits");
				} /*
					 * else if (workmenUploadDao.isAadharExists(aadhaar)) {
					 * fieldErrors.put("aadhaarNumber", "Duplicate Aadhaar number"); }
					 */
	        }
	        String workorderNumber = record.getWorkorderNumber();
	        LocalDate validTillDate = workmenUploadDao.workorderValidityCheck(workorderNumber);

	        if (validTillDate == null) {
	            fieldErrors.put("workOrderValidFrom", "Work order not found");
	        } else if (validTillDate.isBefore(LocalDate.now())) {
	            fieldErrors.put("workOrderValidFrom", "Work order expired");
	        }

	        if (!isBlank(record.getFirstName()) && !record.getFirstName().matches("[A-Za-z ]+")) {
	            fieldErrors.put("firstName", "Only alphabets allowed");
	        }
	        if (!isBlank(record.getIdMark()) && !record.getIdMark().matches("[A-Za-z ]+")) {
	            fieldErrors.put("IdMark", "Only alphabets allowed");
	        }
	        if (!isBlank(record.getLastName()) && !record.getLastName().matches("[A-Za-z ]+")) {
	            fieldErrors.put("lastName", "Only alphabets allowed");
	        }
	        
	        if (record.getFirstName().equalsIgnoreCase(record.getLastName())) {
	            fieldErrors.put("nameValidation", "First and Last name cannot be the same");
	        }
	        if (!isBlank(record.getRelationName()) && !record.getRelationName().matches("[A-Za-z ]+")) {
	            fieldErrors.put("RelationName", "Only alphabets allowed");
	        }
	        if (!isBlank(record.getEmergencyName()) && !record.getEmergencyName().matches("[A-Za-z ]+")) {
	            fieldErrors.put("EmergencyName", "Only alphabets allowed");
	        }
	        if (!isBlank(record.getGender()) && !record.getGender().matches("[A-Za-z ]+")) {
	            fieldErrors.put("Gender", "Only alphabets allowed");
	        }
	        if (!isBlank(record.getMaritalStatus()) && !record.getMaritalStatus().matches("[A-Za-z ]+")) {
            fieldErrors.put("MaritalStatus", "Only alphabets allowed");
        }
	        if (!isBlank(record.getSkill()) && !record.getSkill().matches("^(?=.*[A-Za-z0-9])[A-Za-z0-9 &/\\-]+$")) {
	            fieldErrors.put("Skill", "Only alphabets allowed");
	        }
	        if (!isBlank(record.getNatureOfWork()) && !record.getNatureOfWork().matches("[A-Za-z ]+")) {
	            fieldErrors.put("NatureOfWork", "Only alphabets allowed");
	        }
	        if (!isBlank(record.getHazardousArea()) && !record.getHazardousArea().matches("[A-Za-z ]+")) {
	            fieldErrors.put("HazardousArea", "Only alphabets allowed");
	        }
	        if (!isBlank(record.getAccommodation()) && !record.getAccommodation().matches("[A-Za-z ]+")) {
	            fieldErrors.put("Accommodation", "Only alphabets allowed");
	        }
	        if (!isBlank(record.getAcademic()) &&!record.getAcademic().matches("^(?=.*[A-Za-z0-9])[A-Za-z0-9 ,.()&|/\\-]+$")) {
	        	    fieldErrors.put("Academic", "Only alphabets allowed");
	        }if (!isBlank(record.getTechnical()) &&!record.getTechnical().matches("^(?=.*[A-Za-z0-9])[A-Za-z0-9 &/\\-]+$")) {
	        	    fieldErrors.put("Technical", "Only alphabets allowed");
	        }if (!isBlank(record.getMobileNumber()) && !record.getMobileNumber().matches("^[6-9]\\d{9}$")) {
	            fieldErrors.put("MobileNumber", "Must be 10 digits and start with 6-9");
	        }

	        if (!isBlank(record.getEmergencyNumber()) && !record.getEmergencyNumber().matches("^[6-9]\\d{9}$")) {
	            fieldErrors.put("EmergencyNumber", "Must be 10 digits");
	        }
	        // ➕ Add your other validation rules here (DOJ, account number, etc.)
	     // --- 5. DOB: Must be ≥ 18 years old ---
		     if (!isBlank(record.getDateOfBirth())) {
		        try {
		             LocalDate dob = LocalDate.parse(record.getDateOfBirth()); // Expected format: yyyy-MM-dd
		             if (Period.between(dob, LocalDate.now()).getYears() < 18) {
		                 fieldErrors.put("dateOfBirth", "Age must be 18 years or older");
		             }
		         } catch (DateTimeParseException e) {
		             fieldErrors.put("dateOfBirth", "Invalid date format");
		         }
		       }
		     if (!isBlank(record.getPoliceVerificationDate())) {
		    	    try {
		    	        LocalDate policeDate = LocalDate.parse(record.getPoliceVerificationDate()); // Format: yyyy-MM-dd
		    	        LocalDate oneYearAgo = LocalDate.now().minusYears(1);

		    	        if (policeDate.isBefore(oneYearAgo)) {
		    	            fieldErrors.put("policeVerificationDate", "Police Verification Date must be within the last 1 year");
		    	        }
		    	    } catch (DateTimeParseException e) {
		    	        fieldErrors.put("policeVerificationDate", "Invalid date format (expected yyyy-MM-dd)");
		    	    }
		    	}
		     
		     if (!isBlank(record.getHealthCheckDate())) {
		    	    try {
		    	        LocalDate healthDate = LocalDate.parse(record.getHealthCheckDate()); // Format: yyyy-MM-dd
		    	        LocalDate sixmonAgo = LocalDate.now().minusMonths(6);

		    	        if (healthDate.isBefore(sixmonAgo)) {
		    	            fieldErrors.put("HealthCheckDate", "getHealthCheckDate Date must be within the last 6 Months");
		    	        }
		    	    } catch (DateTimeParseException e) {
		    	        fieldErrors.put("HealthCheckDate", "Invalid date format (expected yyyy-MM-dd)");
		    	    }
		    	}
		  // --- 7. Bank Name: Must be "New Joinee" or alphanumeric ---
		     if (!isBlank(record.getBankName()) && 
		    		 !(record.getBankName().matches("(?i)new\\s?joinee") || record.getBankName().matches("^[A-Za-z0-9]+$"))) {
		             fieldErrors.put("bankName", "Must be 'New Joinee' or alphanumeric");
		     }

		     // --- 8. Account Number: Must be "New Joinee" or alphanumeric ---
		     if (!isBlank(record.getAccountNumber()) &&
		    		    !(record.getAccountNumber().matches("(?i)new\\s?joinee") || record.getAccountNumber().matches("\\d+"))) {
		    		    fieldErrors.put("accountNumber", "Must be 'New Joinee' or Numeric");
		    		}


		     // --- 9. PF Number: Must be "New Joinee" or alphanumeric ---
		     if (!isBlank(record.getPfNumber()) &&
		    		    !(record.getPfNumber().matches("(?i)new\\s?joinee") || record.getPfNumber().matches("^[A-Za-z0-9]+$"))) {
		    		    fieldErrors.put("pfNumber", "Must be 'New Joinee' or alphanumeric");
		    		}


		     // --- 10. DOJ: Must be within 15 days from current date ---
		     if (!isBlank(record.getDoj())) {
		    	    try {
		    	        LocalDate doj = LocalDate.parse(record.getDoj()); // Expected format: yyyy-MM-dd
		    	        LocalDate today = LocalDate.now();
		    	        LocalDate todayPlus15 = today.plusDays(15);

		    	        if (doj.isBefore(today) || doj.isAfter(todayPlus15)) {
		    	            fieldErrors.put("doj", "DOJ must be within the next 15 days from today");
		    	        }
		    	    } catch (DateTimeParseException e) {
		    	        fieldErrors.put("doj", "Invalid date format (expected yyyy-MM-dd)");
		    	    }
		    	}

		     
		     try {
		            record.setUnitCode(String.valueOf(fileUploadDao.getUnitIdByName(record.getUnitCode())));
		            record.setVendorCode(String.valueOf(fileUploadDao.getContractorIdByName(record.getVendorCode())));
		            record.setWorkorderNumber(String.valueOf(fileUploadDao.getWorkorderId(record.getWorkorderNumber(),Integer.parseInt(record.getUnitCode()),Integer.parseInt(record.getVendorCode()))));
		            record.setTrade(String.valueOf(fileUploadDao.getTradeIdByUnitId(Integer.parseInt(record.getUnitCode()),record.getTrade())));
		            record.setSkill(String.valueOf(fileUploadDao.getSkillIdByTradeId(Integer.parseInt(record.getUnitCode()),Integer.parseInt(record.getTrade()),record.getSkill())));
		           // record.setDepartment(String.valueOf(fileUploadDao.getGeneralMasterId(record.getDepartment())));
		            //record.setArea(String.valueOf(fileUploadDao.getAreaByDeptID(Integer.parseInt(record.getUnitCode()),Integer.parseInt(record.getDepartment()),record.getArea())));
		            record.setAccessArea(String.valueOf(fileUploadDao.getGeneralMasterId(record.getAccessArea())));
		            record.setBloodGroup(String.valueOf(fileUploadDao.getGeneralMasterId(record.getBloodGroup())));
		            record.setAcademic(String.valueOf(fileUploadDao.getGeneralMasterId(record.getAcademic())));
		            record.setECnumber(String.valueOf(fileUploadDao.getWCECId(record.getECnumber(),Integer.parseInt(record.getUnitCode()),Integer.parseInt(record.getVendorCode()))));
		           // record.setZone(String.valueOf(fileUploadDao.getGeneralMasterId(record.getZone())));
		            record.setGender(String.valueOf(fileUploadDao.getGeneralMasterId(record.getGender())));
		            record.setEICNumber(String.valueOf(fileUploadDao.geteicId(record.getDepartment(),Integer.parseInt(record.getUnitCode()),record.getEICNumber())));
		            record.setDepartment(String.valueOf(fileUploadDao.getdepartmentIdByUnitId(Integer.parseInt(record.getUnitCode()),record.getDepartment())));
		           // record.setLLnumber(String.valueOf(fileUploadDao.getLlNumber(record.getLLnumber())));
		            record.setArea(String.valueOf(fileUploadDao.getAreaByDeptID(Integer.parseInt(record.getUnitCode()),Integer.parseInt(record.getDepartment()),record.getArea())));
		        } catch (Exception e) {
		            fieldErrors.put("IDMapping", "Failed to resolve IDs: " + e.getMessage());
		        }
	        if (!fieldErrors.isEmpty()) {
	            String combinedErrors = fieldErrors.entrySet().stream()
	                .map(e -> e.getKey() + ": " + e.getValue())
	                .collect(Collectors.joining("; "));

	            errorData.add(Map.of("transactionId", txnId, "fieldErrors", fieldErrors));
	            workmenUploadDao.updateRecordStatusByTransactionId(txnId, combinedErrors);
	        } else {
	            //workmenUploadDao.saveToGatePassMain(record,createdBy);
//	            successData.add(Map.of("transactionId", txnId));
//	            workmenUploadDao.updateRecordProcessedByTransactionId(txnId);
	           // WorkmenBulkUpload record = workmenUploadDao.getByTransactionId(txnId);
	            
	        	String transactionId=workmenUploadDao.getNextTransactionId();
	        	GatePassMain gatePassMain = new GatePassMain();

	        	// ---- Basic Identity ----
	        	gatePassMain.setTransactionId(transactionId);
	        	gatePassMain.setAadhaarNumber(record.getAadhaarNumber());
	        	gatePassMain.setFirstName(record.getFirstName());
	        	gatePassMain.setLastName(record.getLastName());
	        	gatePassMain.setRelationName(record.getRelationName());
	        	gatePassMain.setIdMark(record.getIdMark());
	        	gatePassMain.setGender(record.getGender());
	        	gatePassMain.setMaritalStatus(record.getMaritalStatus());

	        	// ---- Dates ----
	        	gatePassMain.setDateOfBirth(record.getDateOfBirth());
	        	gatePassMain.setDoj(record.getDoj());
	        	gatePassMain.setHealthCheckDate(record.getHealthCheckDate());
	        	gatePassMain.setPoliceVerificationDate(record.getPoliceVerificationDate());

	        	// ---- Contact ----
	        	gatePassMain.setMobileNumber(record.getMobileNumber());
	        	gatePassMain.setEmergencyNumber(record.getEmergencyNumber());
	        	gatePassMain.setEmergencyName(record.getEmergencyName());
	        	gatePassMain.setAddress(record.getAddress());
	        	// ---- Organization Mapping ----
	        	gatePassMain.setPrincipalEmployer(record.getUnitCode());
	        	gatePassMain.setContractor(record.getVendorCode());
	        	gatePassMain.setWorkorder(record.getWorkorderNumber());
	        	gatePassMain.setTrade(record.getTrade());
	        	gatePassMain.setSkill(record.getSkill());
	        	gatePassMain.setDepartment(record.getDepartment());
	        	gatePassMain.setSubdepartment(record.getArea());
	        	gatePassMain.setEic(record.getEICNumber());
	        	gatePassMain.setUnitId(record.getUnitCode());
	        	// ---- Job Details ----
	        	gatePassMain.setNatureOfJob(record.getNatureOfWork());
	        	gatePassMain.setHazardousArea(record.getHazardousArea());
	        	gatePassMain.setAccessArea(record.getAccessArea());
	        	gatePassMain.setAccommodation(record.getAccommodation());

	        	// ---- PF / ESIC ----
	        	gatePassMain.setPfApplicable(record.getPfApplicable());
	        	gatePassMain.setPfNumber(record.getPfNumber());
	        	gatePassMain.setUanNumber(record.getUanNumber());
	        	gatePassMain.setEsicNumber(record.getEsicNumber());
	        	gatePassMain.setWcEsicNo(record.getECnumber());

	        	// ---- Bank ----
	        	gatePassMain.setIfscCode(record.getBankName());
	        	gatePassMain.setAccountNumber(record.getAccountNumber());

	        	// ---- Wages ----
	        	gatePassMain.setBasic(BigDecimal.ZERO);
	        	gatePassMain.setDa(BigDecimal.ZERO);
	        	gatePassMain.setHra(BigDecimal.ZERO);
	        	gatePassMain.setOtherAllowance(BigDecimal.ZERO);
	        	gatePassMain.setWashingAllowance(BigDecimal.ZERO);
	        	gatePassMain.setUniformAllowance(BigDecimal.ZERO);
	        	gatePassMain.setPfCap("0");

	        	// ---- Misc ----
	        	gatePassMain.setAcademic(record.getAcademic());
	        	gatePassMain.setTechnical(record.getTechnical());
	        	gatePassMain.setBloodGroup(record.getBloodGroup());
	        	gatePassMain.setZone(String.valueOf(record.getZone()!=null?record.getZone():""));
	        	gatePassMain.setLlNo(record.getLLnumber());

	        	// ---- Documents (default empty) ----
	        	gatePassMain.setAadharDocName("");
	        	gatePassMain.setPhotoName("");
	        	gatePassMain.setBankDocName("");
	        	gatePassMain.setPoliceVerificationDocName("");
	        	gatePassMain.setMedicalDocName("");
	        	gatePassMain.setEducationDocName("");
	        	gatePassMain.setForm11DocName("");
	        	gatePassMain.setTrainingDocName("");
	        	gatePassMain.setOtherDocName("");

	        	// ---- Workflow ----
	        	gatePassMain.setGatePassStatus("4");          // 
	        	gatePassMain.setGatePassAction("CREATE");
	        	gatePassMain.setWorkFlowType(0);
	        	gatePassMain.setCreatedBy(createdBy);
	        	gatePassMain.setWorkFlowType(1);
	        	gatePassMain.setUserId(createdBy);
	        	gatePassMain.setComments(""); 
	        	gatePassMain.setOnboardingType("regular");
	        	String saveResult = workmenService.saveWorkmenBulkUploadGatePass(gatePassMain);

	            if (saveResult == null) {
	                errorData.add(Map.of("transactionId", txnId, "error", "Save failed"));
	                continue;
	            }

	            if (saveResult.contains("mandatory")) {
	                errorData.add(Map.of("transactionId", txnId, "error", "LL and WC is Mandatory"));
	                workmenUploadDao.updateRecordStatusByTransactionId(txnId, "LL and WC is Mandatory");
	            } else if (saveResult.contains("exceeded")) {
	                errorData.add(Map.of("transactionId", txnId, "error", "LL and WC limit exceeded"));
	                workmenUploadDao.updateRecordStatusByTransactionId(txnId, "LL and WC limit exceeded");
	            } else {
	                successData.add(Map.of("transactionId", saveResult));
	                workmenUploadDao.updateRecordProcessedByTransactionId(txnId);
	            }
	        }
	    }
	    return Map.of("successData", successData, "errorData", errorData);
	

	}
	private boolean isBlank(String val) {
	    return val == null || val.trim().isEmpty();
	}
	
	
	
}
