package com.wfd.dot1.cwfm.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.wfd.dot1.cwfm.dto.OrgLevelDefDTO;
import com.wfd.dot1.cwfm.dto.OrgLevelEntryDTO;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.Contractor;
import com.wfd.dot1.cwfm.pojo.OrgLevel;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.Workorder;

public interface OrgLevelDao {
    void saveOrgLevel(OrgLevelDefDTO orgLevel) throws DataAccessException;
    boolean isHierarchyLevelExists(int hierarchyLevel) throws DataAccessException;
    boolean hasDependencies(int orgLevelDefId) throws DataAccessException;
    void deleteOrgLevel(int id) throws DataAccessException;
    List<OrgLevel> fetchAllOrgLevels();
	void saveOrUpdate(OrgLevel orgLevel);
	boolean existsByNameShortNameAndHierarchy(String name, String shortName, int hierarchyLevel);
	void updateOrgLevel(int id);
	void updateOrgLevel(int id, String newName, String newShortName, int newHierarchyLevel);
	List<OrgLevelDefDTO> saveOrgLevels(List<OrgLevelDefDTO> newEntries);
	void updateOrgLevel(List<OrgLevelDefDTO> updates);
	boolean existsById(Long id);
	List<OrgLevelDefDTO> getActiveOrgLevels();
	List<OrgLevelEntryDTO> getOrgLevelEntriesByDefId(int orgLevelDefId);
	void saveOrgLevelEntry(OrgLevelEntryDTO orgLevelEntry);
	void updateOrgLevelEntry(OrgLevelEntryDTO orgLevelEntry);
	void deleteOrgLevelEntry(int orgLevelEntryId);
	OrgLevelEntryDTO getOrgLevelEntryById(int orgLevelEntryId);
	 List<OrgLevelEntryDTO> getAvailableEntries(Long orgLevelDefId);
	    List<OrgLevelEntryDTO> getSelectedEntries(Long orgLevelDefId);
		boolean isDuplicateEntry(int orgLevelDefId, String name);
		void deleteOrgLevel(List<Long> orgLevelDefId);
		 List<OrgLevel> getAllOrgLevel();
		 
		 OrgLevelDefDTO getOrgLevelById(Integer orgLevelDefId);
			List<PrincipalEmployer> getPrincipalEmployers();
			List<Contractor> getContractors();
			List<CmsGeneralMaster> getGeneralMasters(String type);
			List<Workorder> getWorkorders();
}

