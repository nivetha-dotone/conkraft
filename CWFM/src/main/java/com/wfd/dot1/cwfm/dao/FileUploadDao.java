package com.wfd.dot1.cwfm.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.wfd.dot1.cwfm.dto.MinimumWageDTO;
import com.wfd.dot1.cwfm.pojo.CMSContrPemm;
import com.wfd.dot1.cwfm.pojo.CMSSubContractor;
import com.wfd.dot1.cwfm.pojo.CMSWorkorderLLWC;
import com.wfd.dot1.cwfm.pojo.CMSWorkorderLN;
import com.wfd.dot1.cwfm.pojo.CmsContractorWC;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.Contractor;
import com.wfd.dot1.cwfm.pojo.ContractorWorkorderTYP;
import com.wfd.dot1.cwfm.pojo.DeptMapping;
import com.wfd.dot1.cwfm.pojo.KTCWorkorderStaging;
import com.wfd.dot1.cwfm.pojo.MimumWageMasterTemplate;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.WorkmenBulkUpload;
import com.wfd.dot1.cwfm.pojo.Workorder;

public interface FileUploadDao {
    void saveData(String[] data);

    void saveGeneralMaster(CmsGeneralMaster gm);
    void saveMinimumWage(MimumWageMasterTemplate mw);
    Long savePrincipalEmployer(PrincipalEmployer p);
    Long insertIntoWageTable(MinimumWageDTO dto);
    void insertIntoMinimumWageTable(LocalDate fromDate, Long wageId);
    Long saveContractor(Contractor contractor);
    void savePemm(CMSContrPemm pemm);
   // int savepe(String code);
	void savewc(CmsContractorWC wc);
	void savecsc(CMSSubContractor csc);

	Long saveWorkorder(Workorder workorder);

	void saveWorkorderLN(CMSWorkorderLN woln);

	void saveWorkorderTyp(ContractorWorkorderTYP wotyp);

	Long getUnitIdByPlantCodeAndOrg(String plantCode, String organization);

	Long getContractorIdbyUnitId(Long unitId);

	//Long getWorkorderIdBySapNumber(String sapWorkorderNumber);	
	
	String getCSVHeaders(String templateType);
	
	boolean isPrincipalEmployerCodeExists(String code);
	
	boolean isContractorCodeExists(String contractorCode);
	
	Long getStateIdByName(String stateName);
	
	void savePEState(Long unitId, Long stateId);
	
	void saveWorkorderToStaging(KTCWorkorderStaging workorderStaging);
	
	void callWorkorderProcessingSP();

	boolean isGmNameGmDescriptionExists(String gmName, String gmDescription);

	void saveWorkmenBulkUploadToStaging(WorkmenBulkUpload staging);

	void callWorkmenBulkUploadDraftProcessingSP();

	Integer getUnitIdByName(String unitCode);
	
	Integer getContractorIdByName(String vendorCode);

	Integer getTradeIdByName(String tradeName);

    // Get Workmen Wage Category ID by name
    Integer getWCECId(String ECNumber, Integer unitId, Integer contractorId);

    // Get GMID (Generic Master ID) by GMNAME and master type (like skill, dept, access area, etc.)
    Integer getGeneralMasterId(String gmName);

	Integer getWorkorderId(String workorderNumber,Integer unitId, Integer contractorId);

	Integer getSkillIdByName(String skill);

	Integer geteicId(String department, Integer unitId, String ECnumber);

	int saveWorkmenBulkDraftUploadToStaging(WorkmenBulkUpload staging);

	//void saveToGatePassMain(WorkmenBulkUpload record);
	WorkmenBulkUpload getByTransactionId(int transactionId);

	void saveToGatePassMain(WorkmenBulkUpload data);
	
	void updateRecordStatusByTransactionId(int txnId, String combinedErrors);

	Integer getLlNumber(String LLNumber, Integer unitId, Integer contractorId);

	boolean isAadharNumberExists(String aadharNumber);

	Integer getdepartmentIdByUnitId(Integer unitId, String department);

	Integer getAreaByDeptID(Integer unitId, Integer departmentId, String area);

	Integer getTradeIdByUnitId(Integer unitId, String trade);

	Integer getSkillIdByTradeId(Integer unitId, Integer tradeId, String skill);


    Integer getGeneralMasterId(String gmType, String gmName);

    Integer insertGeneralMaster(String gmType, String gmName);

    boolean existsUnitTradeSkillMapping(Integer unitId, Integer tradeId, Integer skillId);

    void insertUnitTradeSkillMapping(Integer unitId, Integer tradeId, Integer skillId);

	void insertUnitDepartmentSubDepartmentMapping(Integer unitId, Integer departmentId, Integer subDepartmentId);

	long getOrgLevelDefId(String name);

	boolean SavePEOrglevelEntry(List<PrincipalEmployer> list, long orgLevelDefId);

	boolean SaveContOrglevelEntry(List<Contractor> list, long orgLevelDefId);

	boolean SaveWorkorderOrglevelEntry(List<KTCWorkorderStaging> list, long orgLevelDefId);

	boolean saveDeptOrgLevelEntry(List<DeptMapping> finalList, long orgLevelDefId);

	boolean saveAreaOrgLevelEntry(List<DeptMapping> finalList, long orgLevelDefId);

	//void insertOrgLevelEntry(String name, long orgLevelDefId);

	boolean existsInOrgLevelEntry(String name, long orgLevelDefId);

	Long getContractorIdByCode(String contractorCode);	

	boolean hasActiveWorkorder(Long unitId, Long contractorId, String workOrder);

	void updateContractor(Contractor contractor);

	boolean pemmExists(Long contractorId, Long unitId);

	void updatePemm(CMSContrPemm pemm);

	boolean subContractorExists(String contractorCode, Long unitId, String workOrder);

	void updatecsc(CMSSubContractor csc);

	boolean wcExists(Long contractorId, Long unitId, String wcCode,String licenceType);

	void updatewc(CmsContractorWC wc);

	void saveWorkorderLLWC(CMSWorkorderLLWC llwc);

	void updateWorkorderLLWC(CMSWorkorderLLWC llwc);

	boolean llwcExists(String workOrderNumber, String licenseType, String license);

	boolean isLicenseMappedToOtherContractor(Long contractorId,String licenseNumber,String licenseType);

	boolean codeExistsInOrgLevelEntry(String contractorCode,long orgLevelDefId);

	boolean codeExistsInOrgLevelEntry(List<Contractor> list, long orgLevelDefId);

	boolean workorderExists(String workOrder, String contractorCode, String plantCode, String item, String lines, String lineNumber);

	void updateWorkorderToStaging(KTCWorkorderStaging staging);
}
