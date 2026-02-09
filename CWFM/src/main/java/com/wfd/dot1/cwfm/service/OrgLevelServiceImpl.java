package com.wfd.dot1.cwfm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.OrgLevelDao;
import com.wfd.dot1.cwfm.dto.OrgLevelDefDTO;
import com.wfd.dot1.cwfm.dto.OrgLevelEntryDTO;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.Contractor;
import com.wfd.dot1.cwfm.pojo.OrgLevel;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.Workorder;

@Service
public class OrgLevelServiceImpl implements OrgLevelService {
    @Autowired
    private OrgLevelDao orgLevelDao;

//    @Override
//    public void saveOrgLevels(List<OrgLevelDefDTO> orgLevels) throws Exception {
//        for (OrgLevelDefDTO level : orgLevels) {
//            if (orgLevelDao.isHierarchyLevelExists(level.getOrgHierarchyLevel())) {
//                throw new Exception("Hierarchy level " + level.getOrgHierarchyLevel() + " already exists.");
//            }
//            orgLevelDao.saveOrgLevel(level);
//        }
//    }
    public void saveOrgLevels(List<OrgLevelDefDTO> orgLevels) {
        for (OrgLevelDefDTO dto : orgLevels) {
            OrgLevel orgLevel = new OrgLevel();
            orgLevel.setName(dto.getName());
            orgLevel.setShortName(dto.getShortName());
            orgLevel.setOrgHierarchyLevel(dto.getOrgHierarchyLevel());
            orgLevel.setMinimumLength(dto.getMinimumLength());
            orgLevel.setMaximumLength(dto.getMaximumLength());
            orgLevel.setOverrideSw(dto.isOverrideSwitch() ? 1 : 0);
            orgLevel.setUpdatedByUsrAcctId(dto.getUpdatedByUsrAcctId());
            orgLevelDao.saveOrUpdate(orgLevel);
        }
    }
    public boolean existsByNameShortNameAndHierarchy(String name, String shortName, int hierarchyLevel) {
        return orgLevelDao.existsByNameShortNameAndHierarchy(name, shortName, hierarchyLevel);
    }
    @Override
    public void deleteOrgLevel(int id) throws Exception {
        if (orgLevelDao.hasDependencies(id)) {
            throw new Exception("Cannot delete level with dependencies.");
        }
        orgLevelDao.deleteOrgLevel(id);
    } 
    @Override
    public void updateOrgLevel(int id) throws Exception {
        orgLevelDao.updateOrgLevel(id);
    } 
    @Override
    public List<OrgLevel> getAllOrgLevels() {
        return orgLevelDao.fetchAllOrgLevels();
    }
	@Override
	public void updateOrgLevel(List<OrgLevelDefDTO> updates) {
		orgLevelDao.updateOrgLevel(updates);
		
	}
	@Override
	public boolean existsById(Long id) {
		return orgLevelDao.existsById(id);
	}
	@Override
	public List<OrgLevelDefDTO> getActiveOrgLevels() {
		return orgLevelDao.getActiveOrgLevels();
	}
	@Override
	public List<OrgLevelEntryDTO> getOrgLevelEntriesByDefId(int orgLevelDefId) {
		return orgLevelDao.getOrgLevelEntriesByDefId(orgLevelDefId);
	}
	@Override
	public void saveOrgLevelEntry(OrgLevelEntryDTO orgLevelEntry) {
		orgLevelDao.saveOrgLevelEntry(orgLevelEntry);		
	}
	@Override
	public void updateOrgLevelEntry(OrgLevelEntryDTO orgLevelEntry) {
		orgLevelDao.updateOrgLevelEntry(orgLevelEntry);		
	}
	@Override
	public void deleteOrgLevelEntry(int orgLevelEntryId) {
		orgLevelDao.deleteOrgLevelEntry(orgLevelEntryId);	
	}
	@Override
	public OrgLevelEntryDTO getOrgLevelEntryById(int orgLevelEntryId) {
		return orgLevelDao.getOrgLevelEntryById(orgLevelEntryId);
	}
	@Override
	public boolean isDuplicateEntry(int orgLevelDefId, String name) {
		return orgLevelDao.isDuplicateEntry(orgLevelDefId,name);
	}
	 @Override
	    public void deleteOrgLevel(List<Long> orgLevelDefId) {
		 orgLevelDao.deleteOrgLevel(orgLevelDefId);
	    }
	 @Override
	    public List<OrgLevel> getAllOrgLevel() {
	        return orgLevelDao.getAllOrgLevel();
	    }
	 
	 @Override
		public OrgLevelDefDTO getOrgLevelById(Integer orgLevelDefId) {
			return orgLevelDao.getOrgLevelById(orgLevelDefId);
		}
		@Override
		public List<PrincipalEmployer> getPrincipalEmployers() {
			return orgLevelDao.getPrincipalEmployers();
		}
		@Override
		public List<Contractor> getContractors() {
			return orgLevelDao.getContractors();
		}
		@Override
		public List<CmsGeneralMaster> getGeneralMasters(String type) {
			return orgLevelDao.getGeneralMasters(type);
		}
		@Override
		public List<Workorder> getWorkorders() {
			return orgLevelDao.getWorkorders();
		}
}

