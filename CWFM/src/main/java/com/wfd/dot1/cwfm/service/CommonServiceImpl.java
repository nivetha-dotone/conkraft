package com.wfd.dot1.cwfm.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wfd.dot1.cwfm.controller.CreateEmpFetchByGatePassAPICALL;
import com.wfd.dot1.cwfm.dao.CommonDao;
import com.wfd.dot1.cwfm.dto.GeneralMasterDTO;
import com.wfd.dot1.cwfm.dto.SectionDto;
import com.wfd.dot1.cwfm.pojo.CMSGMType;
//import com.wfd.dot1.cwfm.pojo.CMSGMType;
//import com.wfd.dot1.cwfm.pojo.CMSPerson;
import com.wfd.dot1.cwfm.pojo.CMSRoleRights;
import com.wfd.dot1.cwfm.pojo.CmsContractorWC;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.PersonOrgLevel;
import com.wfd.dot1.cwfm.pojo.State;
import com.wfd.dot1.cwfm.pojo.Workorder;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private CommonDao commonDAO;
    
    @Autowired
	CreateEmpFetchByGatePassAPICALL api;
    
	private static final Logger log = LoggerFactory.getLogger(CommonServiceImpl.class);


//    @Override
//    public State getStateById(int stateId) {
//        return commonDAO.getStateById(stateId);
//    }
//
    @Override
    public List<State> getAllStates() {
        return commonDAO.getAllStates();
    }
    @Override
	public List<CmsContractorWC> getMappingsByContractorIdAndUnitIdAndLicenseType(Long id, Long principalEmployerId,
			String string) {
		return commonDAO.getMappingsByContractorIdAndUnitIdAndLicenseType(id, principalEmployerId, string);
	}

	@Override
	public List<CmsContractorWC> getMappingsByContractorIdAndUnitIdAndLicenseTypes(Long id,
			Long principalEmployerId, List<String> licenseTypes) {
		return commonDAO.getMappingsByContractorIdAndUnitIdAndLicenseTypes(id, principalEmployerId, licenseTypes);
	}
    // Implement other methods by delegating to DAO layer

	@Override
	public List<Workorder> getWorkOrdersByContractorIdAndUnitId(Long id, Long principalEmployerId) {
		return commonDAO.getWorkOrdersByContractorIdAndUnitId(id, principalEmployerId);
	}
	@Override
    public Workorder getWorkorderById(Long id) {
        return commonDAO.getWorkorderById(id);
    }

	@Override
	public CMSGMType findById(Long gmTypeId) {
		 return commonDAO.findById(gmTypeId);
	}

//	@Override
//	public List<CMSPerson> getAllPersons() {
//		return commonDAO.getAllPersons();
//	}
//
//	@Override
//	public List<CMSPerson> getAllPersonsByPrincipalEmployerAndContractor(Long principalEmployerId, Long contractorId) {
//		return commonDAO.getAllPersonsByPrincipalEmployerAndContractor(principalEmployerId,contractorId);
//	}

	@Override
	public CmsGeneralMaster findByGMId(Long bloodGroupId) {
		return commonDAO.findByGMId(bloodGroupId);
	}

	@Override
	public List<CmsGeneralMaster> getCmsGeneralMasterOptionsByName(String string) {
		return commonDAO.getCmsGeneralMasterOptionsByName(string);
	}
	
	@Override
    public boolean isGMTypeNameDuplicate(String gmTypeName) {
        CMSGMType gmType = commonDAO.findByGMTypeName(gmTypeName);
        return gmType != null;  // Return true if the name already exists
    }

	 @Override
    public boolean isCmsGeneralMasterDuplicate(String masterName, String masterValue) {
        CmsGeneralMaster master = commonDAO.findByMasterNameAndValue(masterName, masterValue);
        return master != null;  // Return true if the name and value combination exists
    }
    

    @Override
    public List<CMSGMType> getAllGMTypes() {
        return commonDAO.getAllGMTypes();
    }

    @Override
    public String saveGMType(String gmType) {
    	return commonDAO.saveGMType(gmType);
    }

    @Override
    public void deleteGMType(List<Long> gmTypeId) {
    	commonDAO.deleteGMType(gmTypeId);
    }


    @Override
    public List<GeneralMasterDTO> getAllCmsGeneralMasters() {
        return commonDAO.getAllCmsGeneralMasters();
    }

    @Override
    public void saveCmsGeneralMaster(CmsGeneralMaster gm) {
    	commonDAO.saveCmsGeneralMaster(gm);
    }

    @Override
    public void deleteCmsGeneralMaster(Long gmId) {
    	commonDAO.deleteCmsGeneralMaster(gmId);
    }
    
    @Override
    public List<CMSRoleRights> getRoleRightsByRoleId(Long roleId) {
        return commonDAO.getRoleRightsByRoleId(roleId);
    }

    @Override
    public CMSRoleRights saveRoleRights(CMSRoleRights roleRights) {
        roleRights.setCreationDate(LocalDateTime.now());
        roleRights.setLastUpdatedDate(LocalDateTime.now());
        return commonDAO.saveRoleRights(roleRights);
    }
    
    @Override
    public CmsGeneralMaster findCmsGeneralMasterById(String id) {
        return commonDAO.findCmsGeneralMasterById(id); // Assuming this method exists
    }
    
	/*
	 * @Override public CmsGeneralMaster findDefaultAddRights() { return
	 * findCmsGeneralMasterById(1L); // Assuming default Add Rights is with ID 1 }
	 * 
	 * @Override public CmsGeneralMaster findDefaultEditRights() { return
	 * findCmsGeneralMasterById(2L); // Assuming default Edit Rights is with ID 2 }
	 * 
	 * @Override public CmsGeneralMaster findDefaultDeleteRights() { return
	 * findCmsGeneralMasterById(3L); // Assuming default Delete Rights is with ID 3
	 * }
	 * 
	 * @Override public CmsGeneralMaster findDefaultImportRights() { return
	 * findCmsGeneralMasterById(4L); // Assuming default Import Rights is with ID 4
	 * }
	 * 
	 * @Override public CmsGeneralMaster findDefaultExportRights() { return
	 * findCmsGeneralMasterById(5L); // Assuming default Export Rights is with ID 5
	 * }
	 */

	@Override
	public List<CMSRoleRights> getAllRoleRights() {
		return commonDAO.getAllRoleRights();
	}
	@Override
	 public void saveUser(User user) {
		 commonDAO.save(user); // Only save the user information
	    }

	@Override
	public List<CmsGeneralMaster> getGMByType(String type) {
		return commonDAO.getGMByType(type);
	}

	@Override
	public CmsGeneralMaster getGMById(Long id) {
		return commonDAO.getGMById(id);
	}

	@Override
	public List<CmsGeneralMaster> getAllPages() {
		return commonDAO.getAllPages();
	}
	@Override
	public List<CmsGeneralMaster> getAllSections() {
		return commonDAO.getAllSections();
	}


	@Override
	public List<CmsGeneralMaster> getAllRoles() {
		return commonDAO.getAllRoles();
	}

	@Override
	public List<CmsGeneralMaster> getPagesByRole(Long roleId) {
		return commonDAO.getPagesByRole(roleId);
	}
	@Override
	public List<GeneralMasterDTO> getGeneralMastersWithTypeName(Long gmTypeId) {
		return commonDAO.getGeneralMastersWithTypeName(gmTypeId);
	}
	@Override
	public void deleteGmDataById(Long gmId) {
		commonDAO.deleteGmDataById(gmId);
		
	}
	
	public String getWFDIntegration() {
		return QueryFileWatcher.getQuery("TRADE_SKILL_WFD_INTEGRATION");
	}
	@Override
	public void saveGeneralMaster(GeneralMasterDTO generalMasterDTO) {
		commonDAO.saveGeneralMaster(generalMasterDTO);	
		
		
		try {
        	String wfdIntegration = this.getWFDIntegration();
        	if("yes".equalsIgnoreCase(wfdIntegration)) {
        		String tradeSkillType = commonDAO.getGMID(generalMasterDTO.getGmTypeId(), generalMasterDTO.getGmName());
        		if(null!=tradeSkillType) {
        			api.postSkills(Integer.parseInt(tradeSkillType));
        		}
        	}
        	}catch(Exception e) {
        		log.error(e.getLocalizedMessage());
        	}

	}
	@Override
	public boolean checkDuplicateRoleRight(Long roleId, Long pageId) {
		return commonDAO.checkDuplicateRoleRight(roleId,pageId);
	}
	@Override
	public List<CmsGeneralMaster> getRolesByUserId(Integer userId) {
		return commonDAO.getRolesByUserId(userId);
	}
	@Override
	public List<CmsGeneralMaster> getPagesByRoleId(String gmId) {
		return commonDAO.getPagesByRoleId(gmId);
	}
	@Override
	public List<CmsGeneralMaster> getAvailablePagesForSection(Long sectionId) {
		return  commonDAO.getAvailablePagesForSection(sectionId);
	}
	@Override
	public List<CmsGeneralMaster> getSelectedPagesForSection(Long sectionId) {
		return  commonDAO.getSelectedPagesForSection(sectionId);
	}
	@Override
	public void saveSectionPage(Long sectionId, List<Long> pageId, String string) {
		  commonDAO.saveSectionPage(sectionId,pageId,string);
	}
	@Override
	public List<SectionDto> getAllSectionsWithPages() {
		return  commonDAO.getAllSectionsWithPages();
	}
	@Override
	public List<SectionDto> getSectionsByRoleId(String roleId) {
		return commonDAO.getSectionsByRoleId(roleId);
	}
	@Override
	public List<Long> getPageIdsByRoleId(String roleId) {
		return commonDAO.getPageIdsByRoleId(roleId);
	}
	@Override
	public boolean hasPageAccessForRole(String roleId, Long pageId) {
		return commonDAO.hasPageAccessForRole(roleId,pageId);
	}
	@Override
	public List<PersonOrgLevel> getPersonOrgLevelDetails(String userAccount) {
		// TODO Auto-generated method stub
		return commonDAO.getPersonOrgLevelDetails(userAccount);
	}
	@Override
	public List<CMSRoleRights> getRoleRightsByRoleIdAndPageId(Long selectedRoleId, Long pageId) {
		return commonDAO.getRoleRightsByRoleAndPage(selectedRoleId,pageId);

	}
	@Override
    public boolean isMasterNameDuplicate(Long gmTypeId, String gmName) {
        CmsGeneralMaster gmType = commonDAO.findByGMName(gmTypeId,gmName);
        return gmType != null;  // Return true if the name already exists
    }
	@Override
	public void deleteRoleRights(List<Integer> roleIds) {
		commonDAO.deleteRoleRights(roleIds);		
	}
	@Override
	public boolean isDuplicateGMName(Long gmTypeId, String gmName) {
		return commonDAO.isDuplicateGMName(gmTypeId,gmName);
	}
	
	@Override
	public CMSRoleRights hasPageActionPermissionForRole(String roleId, String pageDescription) {
		return commonDAO.hasPageActionPermissionForRole(roleId, pageDescription);
	}
	@Override
	public List<CmsGeneralMaster> getImportOptionsByRole(String roleId) {
	    return commonDAO.getImportOptionsByRole(roleId);
	}
}