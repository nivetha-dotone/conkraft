package com.wfd.dot1.cwfm.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.dto.OrgLevelEntryDTO;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.OrgLevelMapping;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;
@Repository
public class OrgLevelMappingDaoImpl implements OrgLevelMappingDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public String findall() {
	    return QueryFileWatcher.getQuery("FIND_ALL");
    }
    public String findbyall() {
	    return QueryFileWatcher.getQuery("FIND_BY_ALL");
    }
    public String findorgbyid() {
	    return QueryFileWatcher.getQuery("FIND_ORG_BY_ID");
    }
    public String save() {
	    return QueryFileWatcher.getQuery("SAVE");
    }
    public String delete() {
	    return QueryFileWatcher.getQuery("DELETE");
    }
    public String getavailableentries() {
	    return QueryFileWatcher.getQuery("GET_AVAILABLE_ENTRIES_QUERY");
    }
    public String getselectedentries() {
	    return QueryFileWatcher.getQuery("GET_SELECTED_ENTRIES_QUERY");
    }
    public String insertintoorgactset() {
	    return QueryFileWatcher.getQuery("INSERT_INTO_ORG_ACT_SET");
    }
    public String deleteexistingrecords() {
	    return QueryFileWatcher.getQuery("DELETE_EXISTING_RECORDS");
    }
    public String insertintoorgactsetmm() {
	    return QueryFileWatcher.getQuery("INSERT_INTO_ORG_ACT_SETMM");
    }
    public String getnextorgacctsetid() {
	    return QueryFileWatcher.getQuery("GET_NEXT_ORG_ACCT_SET_ID");
    }
    public String saveorgacctset() {
	    return QueryFileWatcher.getQuery("SAVE_ORG_ACCT_SET");
    }
    public String saveorglevelmapping() {
	    return QueryFileWatcher.getQuery("SAVE_ORG_LEVEL_MAPPING");
    }
    public String findavailablemappings() {
	    return QueryFileWatcher.getQuery("FIND_AVAILABLE_MAPPINGS");
    }
    public String findselectedmappings() {
	    return QueryFileWatcher.getQuery("FIND_SELECTED_MAPPINGS");
    }
    public String findbasicinformation() {
	    return QueryFileWatcher.getQuery("FIND_BASIC_INFORMATION");
    }
    public String findallmaps() {
	    return QueryFileWatcher.getQuery("FIND_ALL_MAPS");
    }
    public String existsByShortName() {
	    return QueryFileWatcher.getQuery("EXISTS_BY_SHORTNAME");
    }
    public String getOrgLevelMappingById() {
	    return QueryFileWatcher.getQuery("GET_ORG_LEVEL_MAPPING_BY_ID");
    }
    public String getSelectedEntrie() {
	    return QueryFileWatcher.getQuery("GET_SELECTED_ENTRIE");
    }
    public String getAvailableEntrie() {
	    return QueryFileWatcher.getQuery("GET_AVAILABLE_ENTRIE");
    }

	/*
	 * public String updateOrgLevelEntriesdeletequery() { return
	 * QueryFileWatcher.getQuery("UPDATE_ORG_LEVEL_ENTRIES_DELETE"); }
	 */
    public String updateOrgLevelEntriescheckquery() {
	    return QueryFileWatcher.getQuery("UPDATE_ORG_LEVEL_ENTRIES_CHECK");
    }
    public String updateOrgLevelEntriesinsertquery() {
	    return QueryFileWatcher.getQuery("UPDATE_ORG_LEVEL_ENTRIES_INSERT");
    }
    public String doesOrgLevelEntryExist() {
	    return QueryFileWatcher.getQuery("ORG_LEVEL_ENTRY_EXIST");
    }
    public String saveOrgLevelMap() {
	    return QueryFileWatcher.getQuery("SAVE_ORG_LEVEL_MAP");
    }
    public String deleteOrgLevelMapping() {
	    return QueryFileWatcher.getQuery("DELETE_ORG_LEVEL_MAPPING");
    }
    public String getExistingMappings() {
	    return QueryFileWatcher.getQuery("GET_EXIST_MAPPINGS");
    }
    public String getUserWithShortName() {
	    return QueryFileWatcher.getQuery("GET_USER_WITH_SHORTNAME");
    }
    
    @Override
    public List<OrgLevelMapping> findAll() {
    	String query=findall();
        List<OrgLevelMapping> result = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(OrgLevelMapping.class));
        System.out.println("Fetched OrgLevelMappings: " + result);  // Log the fetched data
        return result;
    }

    @Override
    public OrgLevelMapping findById(Long id) {
    	String query=findbyall();
        return jdbcTemplate.queryForObject(query, new Object[]{id}, new BeanPropertyRowMapper<>(OrgLevelMapping.class));
    }
    
    public List<OrgLevelMapping> findOrgById(Long id) {
    	String query=findorgbyid();
        return jdbcTemplate.query(query, new Object[]{id}, new BeanPropertyRowMapper<>(OrgLevelMapping.class));
    }
    
    @Override
    public void save(OrgLevelMapping mapping) {
    	String query=save();
        jdbcTemplate.update(query, mapping.getOrgLevelEntryId(), mapping.getOrgAcctSetId());
    }

    @Override
    public void delete(int id) {
    	String query=delete();
        jdbcTemplate.update(query, id);
    }

        @Override
        public List<OrgLevelEntryDTO> getAvailableEntries(Long orgLevelDefId) {
        	String query=getavailableentries();
            return jdbcTemplate.query(query, new Object[]{orgLevelDefId}, new OrgLevelEntryRowMapper());
        }

        @Override
        public List<OrgLevelEntryDTO> getSelectedEntries(Long orgLevelDefId) {
        	String query=getselectedentries();
            return jdbcTemplate.query(query, new Object[]{orgLevelDefId}, new OrgLevelEntryRowMapper());
        }

        // RowMapper to map ResultSet rows to OrgLevelEntryDTO
        private static class OrgLevelEntryRowMapper implements RowMapper<OrgLevelEntryDTO> {
            @Override
            public OrgLevelEntryDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                OrgLevelEntryDTO entry = new OrgLevelEntryDTO();
                entry.setOrgLevelDefId((int) rs.getLong("ORGLEVELENTRYID"));
                entry.setName(rs.getString("NAME"));
                entry.setDescription(rs.getString("DESCRIPTION"));
                return entry;
            }
        }
        
        public void saveOrgLevelEntries(List<Map<String, Object>> orgLevelMappings) {
            if (orgLevelMappings == null || orgLevelMappings.isEmpty()) {
                throw new IllegalArgumentException("Input data cannot be null or empty");
            }

            for (Map<String, Object> mapping : orgLevelMappings) {
                // Extract orgLevelDefId with null and type check
                String orgLevelDefIdObj = (String) mapping.get("orgLevelDefId");
                List<Map<String, String>> selectedEntries = (List<Map<String, String>>) mapping.get("selected");
                // Step 1: Delete existing records for this orgLevelDefId
                deleteExistingRecords(orgLevelDefIdObj);
                // Step 2: Insert new records
                for (Map<String, String> entry : selectedEntries) {
                    String shortName = entry.get("shortName");
                    String longDescription = entry.get("longDescription");
                    if (shortName == null || longDescription == null) {
                        throw new IllegalArgumentException("shortName and longDescription cannot be null");
                    }
                    // Insert into ORGACCTSET
                    int orgAcctSetId = insertIntoORGACCTSET(shortName, longDescription);
                    // Insert into OLACCTSETMM
                    insertIntoOLACCTSETMM(orgLevelDefIdObj, orgAcctSetId);
                }
            }
        }
        
        private int insertIntoORGACCTSET(String shortName, String longDescription) {
        	String query=insertintoorgactset();
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, shortName);
                ps.setString(2, longDescription);
                return ps;
            }, keyHolder);

            return keyHolder.getKey().intValue();
        }
        
        private void deleteExistingRecords(String orgLevelDefId) {
        	String query=insertintoorgactset();
            jdbcTemplate.update(query, orgLevelDefId);
        }
        
        private void insertIntoOLACCTSETMM(String orgLevelDefId, int orgAcctSetId) {
        	String query=insertintoorgactsetmm();
            jdbcTemplate.update(query, orgLevelDefId, orgAcctSetId);
        }
        
        public int getNextOrgAcctSetId() {
        	String query=getnextorgacctsetid();
            Integer maxOrgAcctSetId = jdbcTemplate.queryForObject(query, Integer.class);
            if (maxOrgAcctSetId == null) {
                return 1; // First ID to be created
            }
            return maxOrgAcctSetId + 1;
        }

        @Override
        public int saveOrgAcctSet(OrgLevelMapping orgAcctSet) {
        	String query=saveorgacctset();
            Integer generatedId = jdbcTemplate.queryForObject(query,
                    new Object[] { orgAcctSet.getShortName(), orgAcctSet.getLongDescription() },
                    Integer.class);
            if (generatedId == null) {
                System.out.println("Failed to retrieve the generated ID after insert.");
                throw new RuntimeException("Failed to retrieve the generated ID after insert.");
            }
            System.out.println("Generated ID: " + generatedId);
            return generatedId;
        }

        public void saveOrgLevelMapping(OrgLevelMapping orgLevelMapping) {
        	String query=saveorglevelmapping();
            jdbcTemplate.update(query, orgLevelMapping.getOrgLevelEntryId(), orgLevelMapping.getOrgAcctSetId());
        }
        
        public List<OrgLevelMapping> findAvailableMappings(Long id) {
        	String query=findavailablemappings();
          //  return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(OrgLevelMapping.class), id);
            return jdbcTemplate.query(query, (rs, rowNum) -> {
                OrgLevelMapping mapping = new OrgLevelMapping();
                mapping.setOrgLevelEntryId(rs.getLong("ORGLEVELENTRYID"));
                mapping.setOrgAcctSetId(rs.getLong("ORGACCTSETID"));
                mapping.setShortName(rs.getString("SHORTNM")); // Ensure getter/setter exist
                mapping.setLongDescription(rs.getString("LONGDSC")); // Ensure getter/setter exist
                return mapping;
            }, id);
        }

        public List<OrgLevelMapping> findSelectedMappings(Long id) {
            String query = findselectedmappings();
            return jdbcTemplate.query(query, new RowMapper<OrgLevelMapping>() {
                @Override
                public OrgLevelMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
                    OrgLevelMapping mapping = new OrgLevelMapping();
                    mapping.setOrgLevelEntryId(rs.getLong("ORGLEVELENTRYID"));
                    mapping.setOrgAcctSetId(rs.getLong("ORGACCTSETID")); // Ensure this is Long
                    mapping.setShortName(rs.getString("SHORTNM")); // Map SHORTNM to shortName
                    mapping.setLongDescription(rs.getString("LONGDSC")); // Map LONGDSC to longDescription
                    return mapping;
                }
            }, id);
        }
        
        @SuppressWarnings("deprecation")
        public OrgLevelMapping findBasicInfo(Long id) {
            String query = findbasicinformation();

            try {
                return jdbcTemplate.queryForObject(query, new Object[]{id}, (rs, rowNum) -> {
                    OrgLevelMapping mapping = new OrgLevelMapping();
                    mapping.setOrgAcctSetId(rs.getLong("ORGACCTSETID"));
                    mapping.setShortName(rs.getString("SHORTNM"));
                    mapping.setLongDescription(rs.getString("LONGDSC"));
                    return mapping;
                });
            } catch (EmptyResultDataAccessException e) {
                return null; // No record found
            } catch (IncorrectResultSizeDataAccessException e) {
                throw new RuntimeException("Expected one record, but found multiple for ID: " + id);
            }
        }


        
        @Override
        public List<OrgLevelMapping> findAllMaps() {
        	String query=findallmaps();
            List<OrgLevelMapping> result = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(OrgLevelMapping.class));

            System.out.println("Fetched OrgLevelMappings: " + result);  // Log the fetched data
            return result;
        }
		@Override
		public boolean existsByShortName(String name) {
			  String query = existsByShortName();
			 //String sql = "SELECT COUNT(*) FROM ORGACCTSET WHERE SHORTNM = ?";
		        Integer count = jdbcTemplate.queryForObject(query, Integer.class, name);
		        return count != null && count > 0;
		}
		@SuppressWarnings("deprecation")
		public List<OrgLevelMapping> getOrgLevelMappingById(Long orgAcctSetId) {
			  String query = getOrgLevelMappingById();
		    //String sql = "SELECT om.ORGLEVELENTRYID, om.ORGACCTSETID, os.SHORTNM, os.LONGDSC " +
		     //            "FROM OLACCTSETMM om " +
		      //           "LEFT JOIN ORGACCTSET os ON om.ORGACCTSETID = os.ORGACCTSETID " +
		     //            "WHERE om.ORGACCTSETID = ?";

		    return jdbcTemplate.query(query, new Object[]{orgAcctSetId}, (rs, rowNum) -> {
		        OrgLevelMapping mapping = new OrgLevelMapping();
		        mapping.setOrgLevelEntryId(rs.getLong("ORGLEVELENTRYID"));
		        mapping.setOrgAcctSetId(rs.getLong("ORGACCTSETID"));
		        mapping.setShortName(rs.getString("SHORTNM"));
		        mapping.setLongDescription(rs.getString("LONGDSC"));
		        return mapping;
		    });
		}

		@SuppressWarnings("deprecation")
		public List<OrgLevelEntryDTO> getSelectedEntries(Long orgLevelMappingId, Long orgLevelDefId) {
			  String query = getSelectedEntrie();
				/*
				 * String sql = "SELECT oe.ORGLEVELENTRYID, oe.NAME, oe.DESCRIPTION " +
				 * "FROM OLACCTSETMM om " +
				 * "JOIN ORGLEVELENTRY oe ON om.ORGLEVELENTRYID = oe.ORGLEVELENTRYID " + //
				 * Ensure correct mapping "WHERE om.ORGACCTSETID = ? AND oe.ORGLEVELDEFID = ?";
				 */

		    return jdbcTemplate.query(query, new Object[]{orgLevelMappingId, orgLevelDefId}, (rs, rowNum) -> {
		        OrgLevelEntryDTO entry = new OrgLevelEntryDTO();
		        entry.setOrgLevelEntryId(rs.getLong("ORGLEVELENTRYID"));
		        entry.setName(rs.getString("NAME"));
		        entry.setDescription(rs.getString("DESCRIPTION"));
		        return entry;
		    });
		}

		@SuppressWarnings("deprecation")
		public List<OrgLevelEntryDTO> getAvailableEntries(Long orgLevelMappingId, Long orgLevelDefId) {
			  String query = getAvailableEntrie();
				/*
				 * String sql = "SELECT oe.ORGLEVELENTRYID, oe.NAME, oe.DESCRIPTION " +
				 * "FROM ORGLEVELENTRY oe " + "WHERE oe.ORGLEVELDEFID = ? " +
				 * "AND NOT EXISTS ( " + "    SELECT 1 FROM OLACCTSETMM om " +
				 * "    WHERE om.ORGLEVELENTRYID = oe.ORGLEVELENTRYID " +
				 * "    AND om.ORGACCTSETID = ? " + ")";
				 */
		    return jdbcTemplate.query(query, new Object[]{orgLevelDefId, orgLevelMappingId}, (rs, rowNum) -> {
		        OrgLevelEntryDTO entry = new OrgLevelEntryDTO();
		        entry.setOrgLevelEntryId(rs.getLong("ORGLEVELENTRYID"));
		        entry.setName(rs.getString("NAME"));
		        entry.setDescription(rs.getString("DESCRIPTION"));
		        return entry;
		    });
		}
		@Override
		public void updateOrgLevelEntries(Long orgLevelEntryId, List<Long> selectedOrgAcctSetIds) {
			 // String deletequery = updateOrgLevelEntriesdeletequery();
			  String checkquery = updateOrgLevelEntriescheckquery();
			  String insertquery = updateOrgLevelEntriesinsertquery();
		    if (selectedOrgAcctSetIds == null || selectedOrgAcctSetIds.isEmpty()) {
		        System.out.println("No updates required as selectedOrgAcctSetIds is empty.");
		        return;
		    }

		    // DELETE: Remove entries NOT in the selected list
		    String deleteSql = "DELETE FROM OLACCTSETMM WHERE ORGLEVELENTRYID = ? AND ORGACCTSETID NOT IN (" +
		                       selectedOrgAcctSetIds.stream().map(id -> "?").collect(Collectors.joining(",")) + ")";
		    
		    List<Object> params = new ArrayList<>();
		    params.add(orgLevelEntryId);
		    params.addAll(selectedOrgAcctSetIds);

		    jdbcTemplate.update(deleteSql, params.toArray());

		    // INSERT: Add new entries if they do not exist
		    //String checkSql = "SELECT COUNT(*) FROM OLACCTSETMM WHERE ORGLEVELENTRYID = ? AND ORGACCTSETID = ?";
		    //String insertSql = "INSERT INTO OLACCTSETMM (ORGLEVELENTRYID, ORGACCTSETID, UPDATEDTM) VALUES (?, ?, GETDATE())";

		    for (Long orgAcctSetId : selectedOrgAcctSetIds) {
		        Integer count = jdbcTemplate.queryForObject(checkquery, new Object[]{orgLevelEntryId, orgAcctSetId}, Integer.class);
		        if (count == null || count == 0) {
		            jdbcTemplate.update(insertquery, orgLevelEntryId, orgAcctSetId);
		        }
		    }
		}
		 public boolean doesOrgLevelEntryExist(Integer entryId) {
			  String query = doesOrgLevelEntryExist();
		       // String query = "SELECT COUNT(*) FROM ORGLEVELENTRY WHERE ORGLEVELENTRYID = ?";
		        Integer count = jdbcTemplate.queryForObject(query, Integer.class, entryId);
		        return count != null && count > 0;
		    }

		    public void saveOrgLevelMapping(long orgAcctSetId, Integer entryId) {
		    	  String query = saveOrgLevelMap();
		       // String query = "INSERT INTO OLACCTSETMM (ORGLEVELENTRYID, ORGACCTSETID, UPDATEDTM) VALUES (?, ?, GETDATE())";
		        jdbcTemplate.update(query, entryId, orgAcctSetId);
		    }

		    public void deleteOrgLevelMapping(long orgAcctSetId, Integer entryId) {
		    	  String query = deleteOrgLevelMapping();
		        //String query = "DELETE FROM OLACCTSETMM WHERE ORGACCTSETID = ? AND ORGLEVELENTRYID = ?";
		        jdbcTemplate.update(query, orgAcctSetId, entryId);
		    }

		    public Set<Integer> getExistingMappings(long orgAcctSetId) {
		    	  String query = getExistingMappings();
		        //String query = "SELECT ORGLEVELENTRYID FROM OLACCTSETMM WHERE ORGACCTSETID = ?";
		        return new HashSet<>(jdbcTemplate.queryForList(query, Integer.class, orgAcctSetId));
		    }
			@Override
			public List<OrgLevelMapping> getUserWithShortName(String shortName) {
				// String query = "select ORGACCTSETID,SHORTNM,LONGDSC from ORGACCTSET where SHORTNM=?";
				 String query = getUserWithShortName();
				    return jdbcTemplate.query(query, (rs, rowNum) -> {
				    	OrgLevelMapping user = new OrgLevelMapping();
				    	 user.setOrgAcctSetId(rs.getLong("ORGACCTSETID"));
				        user.setShortName(rs.getString("SHORTNM"));
				        user.setLongDescription(rs.getString("LONGDSC"));
				        return user;
				    }, shortName); // Pass userAccount as query parameter
				}
}
			
