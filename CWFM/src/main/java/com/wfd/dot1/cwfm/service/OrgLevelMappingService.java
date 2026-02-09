package com.wfd.dot1.cwfm.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wfd.dot1.cwfm.dto.OrgLevelEntryDTO;
import com.wfd.dot1.cwfm.pojo.OrgLevelMapping;

public interface OrgLevelMappingService {
	   List<OrgLevelMapping> listMappings();
	    void save(OrgLevelMapping mapping);
	    OrgLevelMapping getMappingById(Long id);
	    void delete(int id);
		List<OrgLevelEntryDTO> getAvailableEntries(Long orgLevelDefId);
		List<OrgLevelEntryDTO> getSelectedEntries(Long orgLevelDefId);
		void saveOrgLevelEntries(List<Map<String, Object>> orgLevelMappings);
		int getNextOrgAcctSetId();
		int saveOrgAcctSet(OrgLevelMapping orgAcctSet);
		void saveOrgLevelMapping(OrgLevelMapping orgLevelMapping);
		List<OrgLevelMapping> findAvailableMappings(Long id);
		List<OrgLevelMapping> findSelectedMappings(Long id);
		OrgLevelMapping findBasicInfo(Long id);
		List<OrgLevelMapping> findAllMaps();
		boolean existsByShortName(String name);
		List<OrgLevelMapping> getOrgLevelMappingById(Long id);
		List<OrgLevelEntryDTO> getSelectedEntries(Long id, Long orgLevelDefId);
		List<OrgLevelEntryDTO> getAvailableEntries(Long id, Long orgLevelDefId);
		void updateOrgLevelEntries(Long orgLevelDefId, List<Long> newSelectedEntries);
		boolean doesOrgLevelEntryExist(Integer entryId);
		void saveOrgLevelMapping(long orgAcctSetId, Integer entryId);
		void deleteOrgLevelMapping(long orgAcctSetId, Integer entryId);
		Set<Integer> getExistingMappings(long orgAcctSetId);
		List<OrgLevelMapping> getUserWithShortName(String shortName);
}
