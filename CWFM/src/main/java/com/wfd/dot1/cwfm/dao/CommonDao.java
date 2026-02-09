package com.wfd.dot1.cwfm.dao;

import java.util.List;

import org.springframework.security.core.userdetails.User;

import com.wfd.dot1.cwfm.dto.GeneralMasterDTO;
import com.wfd.dot1.cwfm.dto.SectionDto;
import com.wfd.dot1.cwfm.pojo.CMSGMType;
//import com.wfd.dot1.cwfm.pojo.CMSPerson;
//import com.wfd.dot1.cwfm.pojo.CMSPersonCustomData;
import com.wfd.dot1.cwfm.pojo.CMSRoleRights;
import com.wfd.dot1.cwfm.pojo.CmsContractorWC;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.OrgLevel;
import com.wfd.dot1.cwfm.pojo.PersonOrgLevel;
import com.wfd.dot1.cwfm.pojo.State;
import com.wfd.dot1.cwfm.pojo.Workorder;

public interface  CommonDao {
//	 State getStateById(int stateId);
	    List<State> getAllStates();
		List<CmsContractorWC> getMappingsByContractorIdAndUnitIdAndLicenseType(Long id, Long principalEmployerId,
				String string);
		List<CmsContractorWC> getMappingsByContractorIdAndUnitIdAndLicenseTypes(Long id, Long principalEmployerId,
				List<String> licenseTypes);
		List<Workorder> getWorkOrdersByContractorIdAndUnitId(Long id, Long principalEmployerId);
		Workorder getWorkorderById(Long id);
		CMSGMType findById(Long gmTypeId);
//		List<CMSPerson> getAllPersons();
//		List<CMSPerson> getAllPersonsByPrincipalEmployerAndContractor(Long principalEmployerId, Long contractorId);
//		List<CMSPersonCustomData> getCustomDataByEmployeeId(Long employeeId);
		CmsGeneralMaster findByGMId(Long bloodGroupId);
		List<CmsGeneralMaster> getCmsGeneralMasterOptionsByName(String string);
		 boolean isGMTypeNameDuplicate(String gmTypeName);
		    boolean isCmsGeneralMasterDuplicate(String masterName, String masterValue);
			CMSGMType findByGMTypeName(String gmTypeName);
			CmsGeneralMaster findByMasterNameAndValue(String masterName, String masterValue);
			
			
			    List<CMSGMType> getAllGMTypes();
			//    String saveGMType(CMSGMType gmType);
			    void deleteGMType(List<Long> gmTypeId);

			    // CmsGeneralMaster-related methods
			    List<GeneralMasterDTO> getAllCmsGeneralMasters();
			    void saveCmsGeneralMaster(CmsGeneralMaster gm);
			    void deleteCmsGeneralMaster(Long gmId);
			    CmsGeneralMaster findCmsGeneralMasterById(String id);  // Fetch CmsGeneralMaster by ID
				List<CMSRoleRights> getRoleRightsByRoleId(Long roleId);
				CMSRoleRights saveRoleRights(CMSRoleRights roleRights);
				List<CMSRoleRights> getAllRoleRights();
				 List<CmsGeneralMaster> getGMByType(String type);
				    CmsGeneralMaster getGMById(Long id);
					void save(User user);
					List<CmsGeneralMaster> getAllPages();
					List<CmsGeneralMaster> getAllRoles();
					List<CmsGeneralMaster> getPagesByRole(Long roleId);
					String saveGMType(String gmType);
					List<GeneralMasterDTO> getGeneralMastersWithTypeName(Long gmTypeId);
					void deleteGmDataById(Long gmId);
					void saveGeneralMaster(GeneralMasterDTO generalMasterDTO);
					boolean checkDuplicateRoleRight(Long roleId, Long roleId2);
					List<CmsGeneralMaster> getRolesByUserId(Integer userId);
					List<CmsGeneralMaster> getPagesByRoleId(String gmId);
					List<CmsGeneralMaster> getAllSections();
					List<CmsGeneralMaster> getAvailablePagesForSection(Long sectionId);
					List<CmsGeneralMaster> getSelectedPagesForSection(Long sectionId);
					void saveSectionPage(Long sectionId, List<Long> pageId, String string);
					List<SectionDto> getAllSectionsWithPages();
					List<SectionDto> getSectionsByRoleId(String roleId);
					List<Long> getPageIdsByRoleId(String roleId);
					boolean hasPageAccessForRole(String roleId, Long pageId);

					List<PersonOrgLevel> getPersonOrgLevelDetails(String userAccount);

					List<CMSRoleRights> getRoleRightsByRoleAndPage(Long roleId, Long pageId);
					CmsGeneralMaster findByGMName(Long gmTypeId, String gmName);
					void deleteRoleRights(List<Integer> roleIds);
					boolean isDuplicateGMName(Long gmTypeId, String gmName);
					CMSRoleRights hasPageActionPermissionForRole(String roleId, String pageDescription);
					void deleteOrgLevel(List<Long> orgLevelDefIds);
					List<OrgLevel> getAllOrgLevels();
					List<CmsGeneralMaster> getImportOptionsByRole(String roleId);
					String getGMID(Long gmtypeId, String gmName);
}
