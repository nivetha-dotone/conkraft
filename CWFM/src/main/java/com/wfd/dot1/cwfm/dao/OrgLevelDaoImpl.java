package com.wfd.dot1.cwfm.dao;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.dto.OrgLevelDefDTO;
import com.wfd.dot1.cwfm.dto.OrgLevelEntryDTO;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.Contractor;
import com.wfd.dot1.cwfm.pojo.OrgLevel;
import com.wfd.dot1.cwfm.pojo.OrgLevelMapping;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.Workorder;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;

@Repository
public class OrgLevelDaoImpl implements OrgLevelDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrgLevelDaoImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public String saveorglevel() {
	    return QueryFileWatcher.getQuery("SAVE_ORG_LEVEL");
    }
    public String findbynameandshortname() {
	    return QueryFileWatcher.getQuery("FIND_BY_NAME_AND_SHORTNAME");
    }
    public String existsbynameshortnameandhierarchy() {
	    return QueryFileWatcher.getQuery("EXISTS_BY_NAME_SHORTNAME_AND_HIERARCHY");
    }
    public String saveorglevelcheck() {
	    return QueryFileWatcher.getQuery("SAVE_OR_LEVEL_CHECK");
    }
    public String saveorglevelupdate() {
	    return QueryFileWatcher.getQuery("SAVE_OR_LEVEL_UPDATE");
    }
    public String saveorglevelinsert() {
	    return QueryFileWatcher.getQuery("SAVE_OR_LEVEL_INSERT");
    }
    public String ishierarchylevelexists() {
	    return QueryFileWatcher.getQuery("IS_HIERARCHY_LEVEL");
    }
    public String hasdependencies() {
	    return QueryFileWatcher.getQuery("HAS_DEPENDENCIES");
    }
    public String deleteorglevel() {
	    return QueryFileWatcher.getQuery("DELETE_ORG_LEVEL");
    }
    public String fetchallorglevels() {
	    return QueryFileWatcher.getQuery("FETCH_ALL_ORG_LEVELS");
    }
    public String updateorglevel() {
	    return QueryFileWatcher.getQuery("UPDATE_ORG_LEVEL");
    }
    public String uupdateorlevel() {
	    return QueryFileWatcher.getQuery("UPDATE_OR_LEVEL");
    }
    public String updateorglevels() {
	    return QueryFileWatcher.getQuery("UPDATE_ORG_LEVELS");
    }
    public String existsbyid() {
	    return QueryFileWatcher.getQuery("EXISTS_BY_ID");
    }
    public String getactiveorglevels() {
	    return QueryFileWatcher.getQuery("GET_ACTIVE_ORG_LEVELS");
    }
    public String getorglevelentriesbydefid() {
	    return QueryFileWatcher.getQuery("GET_ORG_ENTRIES_BY_DEF_ID");
    }
    public String saveorglevelentry() {
	    return QueryFileWatcher.getQuery("SAVE_ORG_LEVEL_ENTRY");
    }
    public String updateorglevelentry() {
	    return QueryFileWatcher.getQuery("UPDATE_ORG_LEEVEL_ENTRY");
    }
    public String deleteorglevelentry() {
	    return QueryFileWatcher.getQuery("DELETE_ORG_LEEVEL_ENTRY");
    }
    public String getorglevelentrybyid() {
	    return QueryFileWatcher.getQuery("GET_ORG_LEVEL_ENTRY_BY_ID");
    }
    public String getavailableentries() {
	    return QueryFileWatcher.getQuery("GET_AVAILABLE_ENTRIES");
    }
    public String getselectedentries() {
	    return QueryFileWatcher.getQuery("GET_SELECTED_ENTRIES");
    }
    public String findbasicinfo() {
	    return QueryFileWatcher.getQuery("FIND_BASIC_INFO");
    }
    public String isDuplicateEntry() {
	    return QueryFileWatcher.getQuery("IS_DUPLICATE_ENTRY");
    }
    public String dependencyOrglevelCheckSql() {
	    return QueryFileWatcher.getQuery("DEPENDENCEY_ORGLEVEL_CHECK");
	}
 public String dependencyOrglevelUpdateSql() {
	    return QueryFileWatcher.getQuery("DEPENDENCY_ORGLEVEL_UPDATE");
	}
 public String getAllOrgLevelQuery() {
	    return QueryFileWatcher.getQuery("GET_ALL_ORG_LEVELS");
	}
 public String dependencyOrglevelUpdatesSql() {
	    return QueryFileWatcher.getQuery("DEPENDENCY_ORGLEVEL_UPDATE_SQL");
	}
 public String getOrgLevelById() {
	    return QueryFileWatcher.getQuery("GET_ORGLEVEL_BY_ORGDEFID");
	}
 public String getAllPrincipalEmployers() {
	    return QueryFileWatcher.getQuery("GET_ALL_PRINCIPALEMPLOYER");
	}
 public String getAllContractors() {
	    return QueryFileWatcher.getQuery("GET_ALL_CONTRACTORS_FOR_ORGLEVEL");
	}
 public String getGeneralMasters() {
	    return QueryFileWatcher.getQuery("GET_GENERALMASTERS_FOR_ORGLEVEL");
	}
 public String getAllWorkorders() {
	    return QueryFileWatcher.getQuery("GET_ALL_WORKORDERS_FOR_ORGLEVEL");
	}
 
    @Override
    public void saveOrgLevel(OrgLevelDefDTO orgLevel) {
    	 System.out.println("Saving org level: " + orgLevel.getName() + ", " + orgLevel.getShortName() + ", " + orgLevel.getOrgHierarchyLevel());
    	 String query=saveorglevel();
        jdbcTemplate.update(query, orgLevel.getName(), orgLevel.getShortName(), orgLevel.getOrgHierarchyLevel(),
                            orgLevel.getMinimumLength(), orgLevel.getMaximumLength(),orgLevel.getUpdatedByUsrAcctId());
    }
    
    public OrgLevel findByNameAndShortName(String name, String shortName) {
    	String query=findbynameandshortname();
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{name, shortName}, new BeanPropertyRowMapper<>(OrgLevel.class));
        } catch (EmptyResultDataAccessException e) {
            return null; 
        }
    }
    @Override
    public boolean existsByNameShortNameAndHierarchy(String name, String shortName, int hierarchyLevel) {
    	 String query=existsbynameshortnameandhierarchy();
        name = name.trim();
        shortName = shortName.trim();
        System.out.println("Checking for existing record with Name: " + name + ", Short Name: " + shortName + ", Hierarchy Level: " + hierarchyLevel);
        int count = jdbcTemplate.queryForObject(query, Integer.class, name, shortName, hierarchyLevel);
        return count > 0;  
    }

    public void saveOrUpdate(OrgLevel orgLevel) {
    	String checkquery=saveorglevelcheck();
    	String updatequery=saveorglevelupdate();
    	String insertquery=saveorglevelinsert();
        int count = jdbcTemplate.queryForObject(checkquery, Integer.class, orgLevel.getName(), orgLevel.getShortName());
        if (count > 0) {
            jdbcTemplate.update(updatequery, orgLevel.getOrgHierarchyLevel(), orgLevel.getMinimumLength(),
                    orgLevel.getMaximumLength(), orgLevel.getUpdatedByUsrAcctId(),
                    orgLevel.getName(), orgLevel.getShortName());
        } else {
            jdbcTemplate.update(insertquery, orgLevel.getName(), orgLevel.getShortName(), orgLevel.getOrgHierarchyLevel(),
                    orgLevel.getMinimumLength(), orgLevel.getMaximumLength(),
                    orgLevel.getUpdatedByUsrAcctId());
        }
    }
   
    @Override
    public boolean isHierarchyLevelExists(int hierarchyLevel) {
    	String query=ishierarchylevelexists();
        return jdbcTemplate.queryForObject(query, Integer.class, hierarchyLevel) > 0;
    }
   
    @Override
    public boolean hasDependencies(int orgLevelDefId) {
    	String query=hasdependencies();
        return jdbcTemplate.queryForObject(query, Integer.class, orgLevelDefId) > 0;
    }
   
    @Override
    public void deleteOrgLevel(int id) {
    	String query=deleteorglevel();
        try {
            int rowsAffected = jdbcTemplate.update(query, id);
            if (rowsAffected > 0) {
                System.out.println("Org Level with ID " + id + " successfully marked as inactive.");
            } else {
                System.out.println("No Org Level found with ID " + id + ". No rows were updated.");
            }
        } catch (Exception e) {
            System.err.println("Error during delete operation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<OrgLevel> fetchAllOrgLevels() {
    	String query=fetchallorglevels();
        return jdbcTemplate.query(query, (rs, rowNum) -> {
            OrgLevel orgLevel = new OrgLevel();
            orgLevel.setOrgLevelDefId(rs.getInt("ORGLEVELDEFID"));
            orgLevel.setName(rs.getString("NAME"));
            orgLevel.setShortName(rs.getString("SHORTNAME"));
            orgLevel.setOrgHierarchyLevel(rs.getInt("ORGHIERARCHYLEVEL"));
            orgLevel.setUpdateDtm(rs.getTimestamp("UPDATE_DTM"));
            orgLevel.setUpdatedByUsrAcctId(rs.getInt("UPDATEDBYUSRACCTID"));
            orgLevel.setMinimumLength(rs.getInt("MINIMUMLENGTH"));
            orgLevel.setMaximumLength(rs.getInt("MAXIMUMLENGTH"));
            orgLevel.setInactive(rs.getInt("INACTIVE"));
            return orgLevel;
        });
    }
   
    @Override
    public void updateOrgLevel(int id, String newName, String newShortName, int newHierarchyLevel) {
    	String query=updateorglevel();
        System.out.println("updateOrgLevel method called for ID: " + id); // Basic log to check if method is entered
        // Log the attempt to update
        System.out.println("Attempting to update Org Level with ID: " + id);
        System.out.println("Executing SQL: " + query + " with ID: " + id + ", newName: " + newName + ", newShortName: " + newShortName + ", newHierarchyLevel: " + newHierarchyLevel);
        try {
            // Executing the update query
            int rowsAffected = jdbcTemplate.update(query, newName, newShortName, newHierarchyLevel, id);
            // Log the number of rows affected
            System.out.println("Rows affected by the update: " + rowsAffected);
            if (rowsAffected > 0) {
                System.out.println("Org Level with ID " + id + " successfully updated.");
            } else {
                System.out.println("No Org Level found with ID " + id + ". No rows were updated.");
            }
        } catch (Exception e) {
            // Log detailed error message for further debugging
            System.err.println("Error during update operation: " + e.getMessage());
            e.printStackTrace();
        }
    }
   
    @Override
	public void updateOrgLevel(int id) {
		String query=uupdateorlevel();
	        try {
	            int rowsAffected = jdbcTemplate.update(query, id);
	            if (rowsAffected > 0) {
	                System.out.println("Org Level with ID " + id + " successfully marked as inactive.");
	            } else {
	                System.out.println("No Org Level found with ID " + id + ". No rows were updated.");
	            }
	        } catch (Exception e) {
	            System.err.println("Error during update operation: " + e.getMessage());
	            e.printStackTrace();
	        }
	}
	 
	public void updateOrgLevel(List<OrgLevelDefDTO> orgLevels) {
		String query=updateorglevels();
	    List<Object[]> batchArgs = new ArrayList<>();
	    for (OrgLevelDefDTO orgLevel : orgLevels) {
	        batchArgs.add(new Object[]{
	            orgLevel.getName(),
	            orgLevel.getShortName(),
	            orgLevel.getOrgHierarchyLevel(),
	            orgLevel.getMinimumLength(),
	            orgLevel.getMaximumLength(),
	            orgLevel.getUpdatedByUsrAcctId(),
	            orgLevel.getOrgLevelDefId()
	        });
	    }
	    // Execute the batch update
	    jdbcTemplate.batchUpdate(query, batchArgs);
	}

	@Override
	public List<OrgLevelDefDTO> saveOrgLevels(List<OrgLevelDefDTO> orgLevels) {
	    List<OrgLevelDefDTO> savedEntries = new ArrayList<>();
	    // Loop through each orgLevel and save it using saveOrgLevel method
	    for (OrgLevelDefDTO orgLevel : orgLevels) {
	        try {
	            System.out.println("Saving org level: " + orgLevel.getName() + ", " + orgLevel.getShortName() + ", " + orgLevel.getOrgHierarchyLevel());
	            saveOrgLevel(orgLevel);  // Save each orgLevel using the existing saveOrgLevel method
	            savedEntries.add(orgLevel);  // Track the successfully saved entries
	        } catch (Exception e) {
	            System.err.println("Error saving org level: " + orgLevel.getName());
	            e.printStackTrace();
	        }
	    }
	    // Return the list of successfully saved orgLevels
	    return savedEntries;
	  }
	 
	    public boolean existsById(Long id) {
		 String query=existsbyid();
	        Integer count = jdbcTemplate.queryForObject(query, new Object[]{id}, Integer.class);
	        return count != null && count > 0;
	    }
	
	    public List<OrgLevelDefDTO> getActiveOrgLevels() {
		 String query=getactiveorglevels();
		    return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(OrgLevelDefDTO.class));
		}
	
		public List<OrgLevelEntryDTO> getOrgLevelEntriesByDefId(int orgLevelDefId) {
			 String query=getorglevelentriesbydefid();
		    return jdbcTemplate.query(query, new Object[]{orgLevelDefId}, new BeanPropertyRowMapper<>(OrgLevelEntryDTO.class));
		}
		
		public void saveOrgLevelEntry(OrgLevelEntryDTO orgLevelEntry) {
			String query=saveorglevelentry();
		    jdbcTemplate.update(query, orgLevelEntry.getOrgLevelDefId(), orgLevelEntry.getName(), orgLevelEntry.getDescription(), 1, orgLevelEntry.getUpdatedByUsrAcctId());
		}
		
		public void updateOrgLevelEntry(OrgLevelEntryDTO orgLevelEntry) {
			String query=updateorglevelentry();
		    jdbcTemplate.update(query, orgLevelEntry.getName(), orgLevelEntry.getDescription(), orgLevelEntry.getUpdatedByUsrAcctId(), orgLevelEntry.getOrgLevelEntryId());
		}
		 
		public void deleteOrgLevelEntry(int orgLevelEntryId) {
			String query=deleteorglevelentry();
		    jdbcTemplate.update(query, orgLevelEntryId);
		}
		 
		public OrgLevelEntryDTO getOrgLevelEntryById(int orgLevelEntryId) {
			String query=getorglevelentrybyid();
		    return jdbcTemplate.queryForObject(query, new Object[]{orgLevelEntryId}, new BeanPropertyRowMapper<>(OrgLevelEntryDTO.class));
		}
		
		  @Override
		    public List<OrgLevelEntryDTO> getAvailableEntries(Long orgLevelDefId) {
			  String query=getavailableentries();
		        return jdbcTemplate.query(query, new Object[]{orgLevelDefId}, (rs, rowNum) -> {
		            OrgLevelEntryDTO entry = new OrgLevelEntryDTO();
		            entry.setOrgLevelEntryId((int) rs.getLong("ORGLEVELENTRYID"));
		            entry.setName(rs.getString("NAME"));
		            entry.setDescription(rs.getString("DESCRIPTION"));
		            return entry;
		        });
		    }
		  
		    @Override
		    public List<OrgLevelEntryDTO> getSelectedEntries(Long orgLevelDefId) {
		    	String query=getselectedentries();
		        return jdbcTemplate.query(query, new Object[]{orgLevelDefId}, (rs, rowNum) -> {
		            OrgLevelEntryDTO entry = new OrgLevelEntryDTO();
		            entry.setOrgLevelEntryId((int) rs.getLong("ORGLEVELENTRYID"));
		            entry.setName(rs.getString("NAME"));
		            entry.setDescription(rs.getString("DESCRIPTION"));
		            return entry;
		        });
		    }
		   
		    public OrgLevelMapping findBasicInfo(Long id) {
		    	String query=findbasicinfo();
		        return jdbcTemplate.queryForObject(query, new Object[]{id}, new BeanPropertyRowMapper<>(OrgLevelMapping.class));
		    }
			@Override
			public boolean isDuplicateEntry(int orgLevelDefId, String name) {
				String query=isDuplicateEntry();
				 //String query = "SELECT COUNT(*) FROM ORGLEVELENTRY WHERE ORGLEVELDEFID = ? AND NAME=? AND INACTIVE = 1 ";
				    Integer count = jdbcTemplate.queryForObject(query, new Object[]{orgLevelDefId, name}, Integer.class);

				    return count != null && count > 0; 
			}
			public void deleteOrgLevel(List<Long> orgLevelDefId) {
		        List<Long> undeletableIds = new ArrayList<>();
		        String CheckSql = dependencyOrglevelCheckSql();
		        String updateSql = dependencyOrglevelUpdateSql();
		        String updateSql1 = dependencyOrglevelUpdatesSql();
		        for (Long orgLevelDefIds : orgLevelDefId) {
		            Integer count = jdbcTemplate.queryForObject(CheckSql, Integer.class, orgLevelDefIds);
		            if (count != null && count > 0) {
		                undeletableIds.add(orgLevelDefIds); // Add to list of undeletable IDs if dependencies exist
		            } else {
		               jdbcTemplate.update(updateSql, orgLevelDefIds);
		               jdbcTemplate.update(updateSql1, orgLevelDefIds);
		                LOGGER.info("Org Level deleted with orgLevelDefId: {}", orgLevelDefIds);
		            }
		        }
		        if (!undeletableIds.isEmpty()) {
		            throw new DataIntegrityViolationException("Cannot delete Org Level due to dependencies: " + undeletableIds);
		        }
		    }
			@Override
		    public List<OrgLevel> getAllOrgLevel() {
		    	String query = getAllOrgLevelQuery();
		        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(OrgLevel.class));
		    }
			
			@Override
			public OrgLevelDefDTO getOrgLevelById(Integer orgLevelDefId) {
				String sql=getOrgLevelById();
			   // String sql = "SELECT ORGLEVELDEFID, NAME FROM ORGLEVELDEF WHERE ORGLEVELDEFID = ?";
			    try {
			        return jdbcTemplate.queryForObject(
			            sql,
			            new Object[]{orgLevelDefId},
			            (rs, rowNum) -> {
			                OrgLevelDefDTO dto = new OrgLevelDefDTO();
			                dto.setOrgLevelDefId(rs.getLong("ORGLEVELDEFID")); // ✅ use getLong
			                dto.setName(rs.getString("NAME"));
			                return dto;
			            }
			        );
			    } catch (EmptyResultDataAccessException e) {
			        return null; // if no row found
			    }
			}

			@Override
			public List<PrincipalEmployer> getPrincipalEmployers() {
				List<PrincipalEmployer> peList= new ArrayList<PrincipalEmployer>();
				String sql=getAllPrincipalEmployers();
			   // String sql = "SELECT CODE, NAME FROM CMSPRINCIPALEMPLOYER ";
			    SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
			    while(rs.next()) {
			        PrincipalEmployer pe = new PrincipalEmployer();
			        pe.setCode(rs.getString("CODE"));
			        pe.setName(rs.getString("NAME"));
			        peList.add(pe);
			    }
			        return peList;
			}
			
			
			@Override
			public List<Contractor> getContractors() {
				List<Contractor> contList= new ArrayList<Contractor>();
				String sql=getAllContractors();
			    //String sql = "SELECT CODE, NAME FROM CMSCONTRACTOR";
				 SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
				    while(rs.next()) {
			        Contractor c = new Contractor();
			        c.setVendorCode(rs.getString("CODE"));
			        c.setContractorName(rs.getString("NAME"));
			        contList.add(c);
				    }
			        return contList;
			}
			@Override
			public List<CmsGeneralMaster> getGeneralMasters(String type) {
				String sql=getGeneralMasters();
			  //  String sql = "SELECT cmsgm.GMNAME, cmsgm.GMDESCRIPTION \r\n"
			   // 		+ "FROM CMSGENERALMASTER cmsgm \r\n"
			   // 		+ "JOIN CMSGMTYPE cmsgt ON cmsgt.GMTYPEID = cmsgm.GMTYPEID \r\n"
			   // 		+ "WHERE \r\n"
			  //  		+ "    cmsgm.ISACTIVE = 1 AND\r\n"
			  //  		+ "    (\r\n"
			  //  		+ "        (? = 'area' AND cmsgt.GMTYPE = 'area') \r\n"
			  //  		+ "        OR \r\n"
			  //  		+ "        (? = 'dept' AND cmsgt.GMTYPE LIKE 'Dep%')\r\n"
			 //   		+ "    );\r\n";
			    		
			    return jdbcTemplate.query(sql, new Object[]{type, type}, (rs, rowNum) -> {
			        CmsGeneralMaster gm = new CmsGeneralMaster();
			        gm.setGmName(rs.getString("GMNAME"));
			        gm.setGmDescription(rs.getString("GMDESCRIPTION"));
			        return gm;
			    });
			}
			@Override
			public List<Workorder> getWorkorders() {
				String sql=getAllWorkorders();
			   // String sql = "select SAP_WORKORDER_NUM,NAME from CMSWORKORDER where VALIDDT>GETDATE()";
			    return jdbcTemplate.query(sql, (rs, rowNum) -> {
			    	Workorder w = new Workorder();
			        w.setSapWorkorderNumber(rs.getString("SAP_WORKORDER_NUM"));
			        w.setName(rs.getString("NAME"));
			        return w;
			    });
			}

}

