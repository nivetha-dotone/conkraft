package com.wfd.dot1.cwfm.service;

import java.util.Collection;
import java.util.List;

import com.wfd.dot1.cwfm.dao.OrgLevelDao;
import com.wfd.dot1.cwfm.dto.OrgLevelDefDTO;
import com.wfd.dot1.cwfm.dto.OrgLevelEntryDTO;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.Contractor;
import com.wfd.dot1.cwfm.pojo.OrgLevel;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.Workorder;

public interface OrgLevelService {
    void saveOrgLevels(List<OrgLevelDefDTO> orgLevels) throws Exception;
    void deleteOrgLevel(int id) throws Exception;
    List<OrgLevel> getAllOrgLevels();
    boolean existsByNameShortNameAndHierarchy(String name, String shortName, int hierarchyLevel);
	void updateOrgLevel(int id) throws Exception;
	void updateOrgLevel(List<OrgLevelDefDTO> updates);
	boolean existsById(Long id);
	List<OrgLevelDefDTO> getActiveOrgLevels();
	List<OrgLevelEntryDTO> getOrgLevelEntriesByDefId(int orgLevelDefId);
	void saveOrgLevelEntry(OrgLevelEntryDTO orgLevelEntry);
	void updateOrgLevelEntry(OrgLevelEntryDTO orgLevelEntry);
	void deleteOrgLevelEntry(int orgLevelEntryId);
	OrgLevelEntryDTO getOrgLevelEntryById(int orgLevelEntryId);
	boolean isDuplicateEntry(int orgLevelDefId, String name);
	void deleteOrgLevel(List<Long> orgLevelDefId);
	 List<OrgLevel> getAllOrgLevel();
	 
	 OrgLevelDefDTO getOrgLevelById(Integer orgLevelDefId);
		List<PrincipalEmployer> getPrincipalEmployers();
		List<Contractor> getContractors();
		List<CmsGeneralMaster> getGeneralMasters(String type);
		List<Workorder> getWorkorders();

}