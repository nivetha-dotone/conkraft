package com.wfd.dot1.cwfm.service;

import java.util.List;

import org.springframework.security.core.userdetails.User;

import com.wfd.dot1.cwfm.dto.GeneralMasterDTO;
import com.wfd.dot1.cwfm.dto.SectionDto;
import com.wfd.dot1.cwfm.pojo.CMSGMType;
import com.wfd.dot1.cwfm.pojo.CMSRoleRights;
import com.wfd.dot1.cwfm.pojo.CmsContractorWC;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.PersonOrgLevel;
import com.wfd.dot1.cwfm.pojo.State;
import com.wfd.dot1.cwfm.pojo.Workorder;

public interface CommonService {
//	  State getStateById(int stateId);
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
		CmsGeneralMaster findByGMId(Long id);
		List<CmsGeneralMaster> getCmsGeneralMasterOptionsByName(String string);
		 boolean isGMTypeNameDuplicate(String gmTypeName);
		    boolean isCmsGeneralMasterDuplicate(String masterName, String masterValue);
		    
		    List<CMSGMType> getAllGMTypes();
		    String saveGMType(String gmType);
		    void deleteGMType(List<Long> gmTypeIds);

		    // CmsGeneralMaster-related methods
		    List<GeneralMasterDTO> getAllCmsGeneralMasters();
		    void saveCmsGeneralMaster(CmsGeneralMaster gm);
		    void deleteCmsGeneralMaster(Long gmId);
		    
		    List<CMSRoleRights> getRoleRightsByRoleId(Long roleId);
		    CMSRoleRights saveRoleRights(CMSRoleRights roleRights);
		    CmsGeneralMaster findCmsGeneralMasterById(String string);  // Fetch CmsGeneralMaster by ID

//		    CmsGeneralMaster findDefaultAddRights();  // Fetch default Add Rights
//		    CmsGeneralMaster findDefaultEditRights();  // Fetch default Edit Rights
//		    CmsGeneralMaster findDefaultDeleteRights();  // Fetch default Delete Rights
//		    CmsGeneralMaster findDefaultImportRights();  // Fetch default Import Rights
//		    CmsGeneralMaster findDefaultExportRights();
		    List<CMSRoleRights> getAllRoleRights();
		    
		    List<CmsGeneralMaster> getGMByType(String type);
		    CmsGeneralMaster getGMById(Long id);
			void saveUser(User user);
			List<CmsGeneralMaster> getAllPages();
			List<CmsGeneralMaster> getAllRoles();
			List<CmsGeneralMaster> getPagesByRole(Long roleId);
			List<GeneralMasterDTO> getGeneralMastersWithTypeName(Long gmTypeId);
			void deleteGmDataById(Long gmId);
			void saveGeneralMaster(GeneralMasterDTO generalMasterDTO);
			boolean checkDuplicateRoleRight(Long roleId, Long pageId);
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

			List<CMSRoleRights> getRoleRightsByRoleIdAndPageId(Long selectedRoleId, Long pageId);
			boolean isMasterNameDuplicate(Long gmTypeId, String gmName);
			void deleteRoleRights(List<Integer> roleIds);
			boolean isDuplicateGMName(Long gmTypeId, String gmName);
			CMSRoleRights hasPageActionPermissionForRole(String roleId, String pageDescription);
			List<CmsGeneralMaster> getImportOptionsByRole(String roleId);
			 
}
