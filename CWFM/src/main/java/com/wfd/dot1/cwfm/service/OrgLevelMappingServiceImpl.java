package com.wfd.dot1.cwfm.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.OrgLevelMappingDao;
import com.wfd.dot1.cwfm.dto.OrgLevelEntryDTO;
import com.wfd.dot1.cwfm.pojo.OrgLevelMapping;

@Service
public class OrgLevelMappingServiceImpl implements OrgLevelMappingService {

    @Autowired
    private OrgLevelMappingDao orgLevelMappingDao;

    @Override
    public List<OrgLevelMapping> listMappings() {
        return orgLevelMappingDao.findAll();
    }

    @Override
    public void save(OrgLevelMapping mapping) {
        orgLevelMappingDao.save(mapping);
    }

    @Override
    public OrgLevelMapping getMappingById(Long id) {
        return orgLevelMappingDao.findById(id);
    }

    @Override
    public void delete(int id) {
        orgLevelMappingDao.delete(id);
    }

    @Override
    public List<OrgLevelEntryDTO> getAvailableEntries(Long orgLevelDefId) {
        return orgLevelMappingDao.getAvailableEntries(orgLevelDefId);
    }

    @Override
    public List<OrgLevelEntryDTO> getSelectedEntries(Long orgLevelDefId) {
        return orgLevelMappingDao.getSelectedEntries(orgLevelDefId);
    }

	@Override
	public void saveOrgLevelEntries(List<Map<String, Object>> orgLevelMappings) {
		orgLevelMappingDao.saveOrgLevelEntries(orgLevelMappings);
		
	}

	@Override
	public int getNextOrgAcctSetId() {
		// TODO Auto-generated method stub
		return orgLevelMappingDao.getNextOrgAcctSetId();
	}

	@Override
	public int saveOrgAcctSet(OrgLevelMapping orgAcctSet) {
		return orgLevelMappingDao.saveOrgAcctSet(orgAcctSet);
		
	}

	@Override
	public void saveOrgLevelMapping(OrgLevelMapping orgLevelMapping) {
		orgLevelMappingDao.saveOrgLevelMapping(orgLevelMapping)	;
	}

	@Override
	public List<OrgLevelMapping> findAvailableMappings(Long id) {
		return orgLevelMappingDao.findAvailableMappings(id);
	}

	@Override
	public List<OrgLevelMapping> findSelectedMappings(Long id) {
		return orgLevelMappingDao.findSelectedMappings(id);
	}

	@Override
	public OrgLevelMapping findBasicInfo(Long id) {
		return orgLevelMappingDao.findBasicInfo(id);
	}

	@Override
	public List<OrgLevelMapping> findAllMaps() {
		return orgLevelMappingDao.findAllMaps();
	}

	@Override
	public boolean existsByShortName(String name) {
		return orgLevelMappingDao.existsByShortName(name);
	}

	@Override
	public List<OrgLevelMapping> getOrgLevelMappingById(Long id) {
		return orgLevelMappingDao.getOrgLevelMappingById(id);
	}

	@Override
	public List<OrgLevelEntryDTO> getSelectedEntries(Long id, Long orgLevelDefId) {
		return orgLevelMappingDao.getSelectedEntries(id,orgLevelDefId);
	}

	@Override
	public List<OrgLevelEntryDTO> getAvailableEntries(Long id, Long orgLevelDefId) {
		return orgLevelMappingDao.getAvailableEntries(id,orgLevelDefId);
	}

	@Override
	public void updateOrgLevelEntries(Long orgLevelDefId, List<Long> newSelectedEntries) {
		orgLevelMappingDao.updateOrgLevelEntries(orgLevelDefId,newSelectedEntries);	
	}

	@Override
	public boolean doesOrgLevelEntryExist(Integer entryId) {
		return orgLevelMappingDao.doesOrgLevelEntryExist(entryId);
	}

	@Override
	public void saveOrgLevelMapping(long orgAcctSetId, Integer entryId) {
		orgLevelMappingDao.saveOrgLevelMapping(orgAcctSetId,entryId);
	}

	@Override
	public void deleteOrgLevelMapping(long orgAcctSetId, Integer entryId) {
		orgLevelMappingDao.deleteOrgLevelMapping(orgAcctSetId, entryId);
	}

	@Override
	public Set<Integer> getExistingMappings(long orgAcctSetId) {
		return orgLevelMappingDao.getExistingMappings(orgAcctSetId);
	}
	@Override
	public List<OrgLevelMapping> getUserWithShortName(String shortName) {
		return orgLevelMappingDao.getUserWithShortName(shortName);
	}

    
}
