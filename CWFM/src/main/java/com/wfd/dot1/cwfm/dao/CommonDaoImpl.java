package com.wfd.dot1.cwfm.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wfd.dot1.cwfm.dto.GeneralMasterDTO;
import com.wfd.dot1.cwfm.dto.PageDto;
import com.wfd.dot1.cwfm.dto.SectionDto;
//import com.wfd.dot1.cwfm.pojo.CMSContractor;
import com.wfd.dot1.cwfm.pojo.CMSGMType;
//import com.wfd.dot1.cwfm.pojo.CMSPerson;
//import com.wfd.dot1.cwfm.pojo.CMSPersonCustomData;
//import com.wfd.dot1.cwfm.pojo.CMSPrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.CMSRoleRights;
import com.wfd.dot1.cwfm.pojo.CmsContractorWC;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.OrgLevel;
import com.wfd.dot1.cwfm.pojo.PersonOrgLevel;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.State;
import com.wfd.dot1.cwfm.pojo.Workorder;
//import com.wfd.dot1.cwfm.service.CMSPRINCIPALEMPLOYERService;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;
//import com.wfd.dot1.cwfm.service.ContractorService;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;

@Repository
public class CommonDaoImpl implements CommonDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonDaoImpl.class);
	//private static final String INSERT_GMTYPE_QUERY = "INSERT INTO CMSGMTYPE (GMTYPE) VALUES (?)";
	 @Autowired
	    private JdbcTemplate jdbcTemplate;
	 @Autowired
	    private CommonDao commonDAO;
	 

	 public String getPersonOrgLevel() {
		    return QueryFileWatcher.getQuery("GET_PERSON_ORG_LEVEL");
		}

	 public String getMappingsByContractorIdAndUnitIdAndLicenseType() {
		    return QueryFileWatcher.getQuery("GET_MAPPINGS_BY_CONTRACTOR_ID_UNITID_LICENSETYPE");
		}
	 public String getWorkorderById() {
		    return QueryFileWatcher.getQuery("GET_WORKORDER_BY_ID");
		}
	 public String getfindById() {
		    return QueryFileWatcher.getQuery("GET_FINDBY");
		}
	 public String findByGMId() {
		    return QueryFileWatcher.getQuery("GET_FIN_GMDBY_ID");
		}
	 public String getCmsGeneralMasterOptionsByName() {
		    return QueryFileWatcher.getQuery("GET_CMS_GENERALMASTER_OPTION_BY_NAME");
		}
	 public String findByGMTypeName() {
		    return QueryFileWatcher.getQuery("FIND_GMTYPE_NAME");
		}
	 public String findByMasterNameAndValue() {
		    return QueryFileWatcher.getQuery("FIND_MASTER_NAME_AND_VALUE");
		}
	 public String isCmsGeneralMasterDuplicate() {
		    return QueryFileWatcher.getQuery("CMS_GENERAL_MASTER_DUPLICATE");
		}
	 public String getAllGMTypesQuery() {
		    return QueryFileWatcher.getQuery("GET_ALL_GMTYPES");
		}
	 public String saveGMType() {
		    return QueryFileWatcher.getQuery("SAVE_GMTYPE");
		}
	 public String dependencyCheckSql() {
		    return QueryFileWatcher.getQuery("DEPENDENCY_CHECK_SQL");
		}
	 public String dependencyUpdateSql() {
		    return QueryFileWatcher.getQuery("DEPENDENCY_UPDATE_SQL");
		}
	 public String getAllCmsGeneralMastersSql() {
		    return QueryFileWatcher.getQuery("GET_ALL_CMS_GENERAL_MASTER");
		}
	 public String saveCmsGeneralMasterSql() {
		    return QueryFileWatcher.getQuery("SAVE_CMS_GENERAL_MASTER");
		}
	 public String deleteCmsGeneralMaster() {
		    return QueryFileWatcher.getQuery("DELETE_CMS_GENERAL_MASTER");
		}
	 public String findCmsGeneralMasterById() {
		    return QueryFileWatcher.getQuery("FIND_CMS_GENERAL_MASTER_BY_ID");
		}
	 public String getAllRoleRightsSql() {
		    return QueryFileWatcher.getQuery("GET_ALL_ROLE_RIGHTS");
		}
	 public String getRoleRightsByRoleId() {
		    return QueryFileWatcher.getQuery("GET_ALL_ROLE_RIGHTS_BY_ID");
		}
	 public String saveRoleRights() {
		    return QueryFileWatcher.getQuery("SAVE_ROLE_RIGHTS");
		}
	 public String updatesaveRoleRights() {
		    return QueryFileWatcher.getQuery("UPDATE_SAVE_ROLE_RIGHTS");
		}
	 public String getgmbytype() {
		    return QueryFileWatcher.getQuery("GET_GM_BY_TYPE");
		}
	 public String getgmbyid() {
		    return QueryFileWatcher.getQuery("GET_GM_BY_ID");
		}
	 public String getallpages() {
		    return QueryFileWatcher.getQuery("GET_ALL_PAGES");
		}
	 public String getallsections() {
		    return QueryFileWatcher.getQuery("GET_ALL_SECTIONS");
		}
	 public String getallroles() {
		    return QueryFileWatcher.getQuery("GET_ALL_ROLES");
		}
	 public String getroles() {
		    return QueryFileWatcher.getQuery("GET_ROLES");
		}
	 public String getpages() {
		    return QueryFileWatcher.getQuery("GET_PAGES");
		}
	 public String getpagesbyrole() {
		    return QueryFileWatcher.getQuery("GET_PAGES_BY_ROLE");
		}
	 public String getallstates() {
		    return QueryFileWatcher.getQuery("GET_ALL_STATES");
		}
	 public String getgeneralmasterswithtypename() {
		    return QueryFileWatcher.getQuery("GET_GENERAL_MASTERS_WITH_TYPE_NAME");
		}
	 public String deletegmdatabyid() {
		    return QueryFileWatcher.getQuery("DELETE_GM_DATA_BY_ID");
		}
	 public String savegeneralmaster() {
		    return QueryFileWatcher.getQuery("SAVE_GENERAL_MASTER");
		}
	 public String checkduplicateroleright() {
		    return QueryFileWatcher.getQuery("CHECK_DUPLICATE_ROLE_RIGHTS");
		}
	 public String getrolesbyuserid() {
		    return QueryFileWatcher.getQuery("GET_ROLES_BY_USER_ID");
		}
	 public String getrolesuserid() {
		    return QueryFileWatcher.getQuery("GET_ROLES_USER_ID");
		}
	 public String getpagesbyroleid() {
		    return QueryFileWatcher.getQuery("GET_PAGES_BY_ROLES_ID");
		}
	 public String getpagesroleid() {
		    return QueryFileWatcher.getQuery("GET_PAGES_ROLES_ID");
		}
	 public String getavailablepagesforsection() {
		    return QueryFileWatcher.getQuery("GET_AVAILABLE_PAGES_FOR_SECTION");
		}
	 public String getselectedpagesforsections() {
		    return QueryFileWatcher.getQuery("GET_SELECTED_PAGES_FOR_SECTIONS");
		}
	 public String savesectionselect() {
		    return QueryFileWatcher.getQuery("SAVE_SECTION_SELECT_SQL");
		}
	 public String savesectionupdate() {
		    return QueryFileWatcher.getQuery("SAVE_SECTION_UPDATE_SQL");
		}
	 public String savesecctioninsert() {
		    return QueryFileWatcher.getQuery("SAVE_SECTION_INSERT_SQL");
		}
	 public String getallsectionswithpages() {
		    return QueryFileWatcher.getQuery("GET_ALL_SECTIONS_WITH_PAGES");
		}
	 public String getsectionsbyroleid() {
		    return QueryFileWatcher.getQuery("GET_SECTIONS_BY_ROLEID");
		}
	 public String getpageidsbyroleid() {
		    return QueryFileWatcher.getQuery("GET_PAGE_ID_BY_ROLE_ID");
		}
	 public String haspageaccessforrole() {
		    return QueryFileWatcher.getQuery("HAS_PAGE_ACCESS_FOR_ROLE");
		}
	 public String getpersonorgleveldetails() {
		    return QueryFileWatcher.getQuery("GET_PERSON_ORG_LEVEL_DETAILS");
		}
	 public String getrolerightsbyroleandpage() {
		    return QueryFileWatcher.getQuery("GET_ROLE_RIGHTS_BY_ROLE_AND_PAGE");
		}
	 public String getduplicategmmaster() {
		    return QueryFileWatcher.getQuery("CHECK_DUPLICATE_GMMASTER");
		}
	 public String getWorkOrdersByContractorIdAndUnitId() {
		    return QueryFileWatcher.getQuery("GET_WORKORDERS_BY_CONTRACTORID_UNITID");
		}
	 public String getPageActionPermissionForRoleQuery() {
		 return QueryFileWatcher.getQuery("HAS_PAGE_ACTION_PERMISSION_FOR_ROLE");
	 }
	 public String isDuplicateGMName() {
		 return QueryFileWatcher.getQuery("IS_DUPLICATE_GM_NAME");
	 }
	 public String deleteRoleRights() {
		 return QueryFileWatcher.getQuery("DELETE_ROLE_RIGHTS");
	 }
	 public String dependencyUpdate() {
		 return QueryFileWatcher.getQuery("DEPENDENCY_UPDATE");
	 }
	 public String getImportOptionsByRole() {
		 return QueryFileWatcher.getQuery("GET_IMPORT_OPTIONS_BY_ROLE");
	 }
	
	@Autowired
	  private CommonService commonService;

	
	   public List<CmsContractorWC> getMappingsByContractorIdAndUnitIdAndLicenseType(Long contractorId, Long unitId, String licenseType) {
	        List<CmsContractorWC> mappings = new ArrayList<>();
	        String query = getMappingsByContractorIdAndUnitIdAndLicenseType();
	        System.out.println("Executing SQL query: " + query + " with parameters: " + contractorId + ", " + unitId + ", " + licenseType);
	        return mappings;
	    }
	   
	    public List<CmsContractorWC> getMappingsByContractorIdAndUnitIdAndLicenseTypes(Long contractorId, Long unitId, List<String> licenseTypes) {
	        List<CmsContractorWC> mappings = new ArrayList<>();
	        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM CMSContractor_WC WHERE CONTRACTORID = ? AND UNITID = ? AND LICENCE_TYPE IN (");
	        for (int i = 0; i < licenseTypes.size(); i++) {
	            if (i != 0) {
	                sqlBuilder.append(", ");
	            }
	            sqlBuilder.append("?");
	        }
	        sqlBuilder.append(")");
	        return mappings;
	    }

	    public List<Workorder> getWorkOrdersByContractorIdAndUnitId(Long contractorId, Long unitId) {
	        List<Workorder> workOrders = new ArrayList<>();
	        String query = getWorkOrdersByContractorIdAndUnitId();
	       // String sql = "SELECT WORKORDERID, SAP_WORKORDER_NUM, CONTRACTORID, VALIDFROM, VALIDDT, UNITID FROM Workorder WHERE CONTRACTORID = ? AND UNITID = ?";
	        return workOrders;
	    }
	    
	    @Override
	    public Workorder getWorkorderById(Long id) {
	        String query = getWorkorderById();
	        return jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Workorder.class), id);
	    }
	   
	    @Override
	    public CMSGMType findById(Long gmTypeId) {
	        String query = getfindById();
	        return jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(CMSGMType.class), gmTypeId);
	    }

	    
	    @Override
	    public CmsGeneralMaster findByGMId(Long bloodGroupId) {
	        String query = findByGMId();
	        return jdbcTemplate.queryForObject(query, new Object[]{bloodGroupId}, (rs, rowNum) -> {
	            CmsGeneralMaster cmsGeneralMaster = new CmsGeneralMaster();
	            cmsGeneralMaster.setGmName(rs.getString("GMNAME"));
	            cmsGeneralMaster.setGmdescription(rs.getString("GMDESCRIPTION"));
	            return cmsGeneralMaster;
	        });
	    }
	   
	    @Override
	    public List<CmsGeneralMaster> getCmsGeneralMasterOptionsByName(String typeName) {
	    	 String query = getCmsGeneralMasterOptionsByName();
	        return jdbcTemplate.query(query, new Object[]{typeName}, new BeanPropertyRowMapper<>(CmsGeneralMaster.class));
	    }
	   
	    public CMSGMType findByGMTypeName(String gmTypeName) {
	        String query = findByGMTypeName();
	        try {
	            return jdbcTemplate.queryForObject(query, new Object[]{gmTypeName}, new BeanPropertyRowMapper<>(CMSGMType.class));
	        } catch (EmptyResultDataAccessException e) {
	            return null;  // Return null or handle the absence of a result in your service layer
	        }
	    }
	   
	    @Override
	    public CmsGeneralMaster findByMasterNameAndValue(String masterName, String masterValue) {
	    	String query = findByMasterNameAndValue();
	        return jdbcTemplate.queryForObject(query, new Object[]{masterName, masterValue}, new BeanPropertyRowMapper<>(CmsGeneralMaster.class));
	    }

	    @Transactional
	    public boolean isGMTypeNameDuplicate(String gmTypeName) {
	        CMSGMType gmType = commonDAO.findByGMTypeName(gmTypeName);
	        return gmType != null;  // If gmType is found, it’s a duplicate; otherwise, it’s not
	    }
	  
	    @Override
	    public boolean isCmsGeneralMasterDuplicate(String masterName, String masterValue) {
	    	String query = isCmsGeneralMasterDuplicate();
	        Integer count = jdbcTemplate.queryForObject(query, new Object[]{masterName, masterValue}, Integer.class);
	        return count != null && count > 0;  // Returns true if duplicate exists
	    }
	   
	    @Override
	    public List<CMSGMType> getAllGMTypes() {
	    	String query = getAllGMTypesQuery();
	        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(CMSGMType.class));
	    }
	   
	    @Override
	    public String saveGMType(String gmType) {
	    	LOGGER.info("Entering into saveGMType method");
	    	String query = saveGMType();
	        if (gmType != null && !gmType.isEmpty()) {
	        	LOGGER.info("Inserting GMType: " + gmType);

	            try {
	                // Execute the insert query
	                int result = jdbcTemplate.update(query, gmType);

	                if (result > 0) {
	                	LOGGER.info("GMType saved successfully: " + gmType);
	                    return gmType; // Returning the gmType name as confirmation
	                } else {
	                	LOGGER.warn("Failed to save GMType: " + gmType);
	                    return null;
	                }
	            } catch (Exception e) {
	            	LOGGER.error("Error saving GMType: " + gmType, e);
	                return null;
	            }
	        } else {
	        	LOGGER.error("Invalid GMType: " + gmType);
	            return null;
	        }
	    }

	    @Override
	    public void deleteGMType(List<Long> gmTypeIds) {
	        List<Long> undeletableIds = new ArrayList<>();
	        String dependencyCheckSql = dependencyCheckSql();
	        String updateSql = dependencyUpdateSql();
	        String updateSql1 = dependencyUpdate();
	        for (Long gmTypeId : gmTypeIds) {
	            Integer count = jdbcTemplate.queryForObject(dependencyCheckSql, Integer.class, gmTypeId);
	            if (count != null && count > 0) {
	                undeletableIds.add(gmTypeId); // Add to list of undeletable IDs if dependencies exist
	            } else {
	               jdbcTemplate.update(updateSql, gmTypeId);
	               jdbcTemplate.update(updateSql1, gmTypeId);
	                LOGGER.info("GM Type deleted with gmTypeId: {}", gmTypeId);
	            }
	        }
	        if (!undeletableIds.isEmpty()) {
	            throw new DataIntegrityViolationException("Cannot delete GM Types due to dependencies: " + undeletableIds);
	        }
	    }
	   
	    @Override
	    public List<GeneralMasterDTO> getAllCmsGeneralMasters() {
	        String query = getAllCmsGeneralMastersSql();
	        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(GeneralMasterDTO.class));
	    }
	   
	    @Override
	    public void saveCmsGeneralMaster(CmsGeneralMaster gm) {
	        String query = saveCmsGeneralMasterSql();
	        jdbcTemplate.update(query, gm.getGmName(), gm.getGmdescription(), gm.getGmType());
	    }
	   
	    @Override
	    public void deleteCmsGeneralMaster(Long gmId) {
	        String query = deleteCmsGeneralMaster();
	        jdbcTemplate.update(query, gmId);
	    }
	   
	    @Override
	    public CmsGeneralMaster findCmsGeneralMasterById(String id) {
	        String query = findCmsGeneralMasterById();
	        return jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(CmsGeneralMaster.class), id);
	    }
	   
	    @Override
	    public List<CMSRoleRights> getAllRoleRights() {
	    	 String query = getAllRoleRightsSql();
	        return jdbcTemplate.query(query, (rs, rowNum) -> {
	            CMSRoleRights rights = new CMSRoleRights();
	            rights.setRoleRightId(rs.getLong("ROLE_RIGHT_ID"));
	            rights.setRoleId(rs.getLong("ROLE_ID"));
	            rights.setPageId(rs.getLong("PAGE_ID"));

	            CmsGeneralMaster role = new CmsGeneralMaster();
	            role.setGmId(String.valueOf(rs.getLong("ROLE_ID")));
	            role.setGmName(rs.getString("roleName"));
	            rights.setRole(role);

	            CmsGeneralMaster page = new CmsGeneralMaster();
	            page.setGmId(String.valueOf(rs.getLong("PAGE_ID")));
	            page.setGmName(rs.getString("pageName"));
	            rights.setPage(page);

	            rights.setAddRights(rs.getInt("ADD_RIGHTS"));
	            rights.setEditRights(rs.getInt("EDIT_RIGHTS"));
	            rights.setDeleteRights(rs.getInt("DELETE_RIGHTS"));
	            rights.setImportRights(rs.getInt("IMPORT_RIGHTS"));
	            rights.setExportRights(rs.getInt("EXPORT_RIGHTS"));
	            rights.setViewRights(rs.getInt("VIEW_RIGHTS"));
	            rights.setListRights(rs.getInt("LIST_RIGHTS"));
	            rights.setEnabledFlag(rs.getInt("ENABLED_FLAG"));
	            rights.setDeletedFlag(rs.getInt("DELETED_FLAG"));

	            return rights;
	        });
	    }
	    
	    @Override
	    public List<CMSRoleRights> getRoleRightsByRoleId(Long roleId) {
	        	    	String query = getRoleRightsByRoleId();
	        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(CMSRoleRights.class), roleId);
	    }
	   
	    @Override
	    public CMSRoleRights saveRoleRights(CMSRoleRights roleRights) {
	    	String query = saveRoleRights();
	    	String query1 = updatesaveRoleRights();
	        if (roleRights.getRoleRightId() == null) {
	            // Insert new entry
	            jdbcTemplate.update(query,
	                    roleRights.getRoleId(),
	                    roleRights.getPageId(),
	                    roleRights.getAddRights(),
	                    roleRights.getEditRights(),
	                    roleRights.getDeleteRights(),
	                    roleRights.getImportRights(),
	                    roleRights.getExportRights(),
	                    roleRights.getViewRights(),
	                    roleRights.getListRights(),
	                    roleRights.getEnabledFlag(),
	                    roleRights.getDeletedFlag(),
	                    roleRights.getCreatedBy(),
	                    roleRights.getCreationDate(),
	                    roleRights.getLastUpdatedBy(),
	                    roleRights.getLastUpdatedDate()
	            );
	            Long generatedId = jdbcTemplate.queryForObject("SELECT SCOPE_IDENTITY()", Long.class);
	            roleRights.setRoleRightId(generatedId);
	        } else {
	        	System.out.println("Updating action rights for RoleRight ID: " + roleRights.getRoleRightId());
	        	System.out.println("SQL Query: " + query1);
	        	System.out.println("Values: " +
	        	        roleRights.getAddRights() + ", " +
	        	        roleRights.getEditRights() + ", " +
	        	        roleRights.getDeleteRights() + ", " +
	        	        roleRights.getImportRights() + ", " +
	        	        roleRights.getExportRights() + ", " +
	        	        roleRights.getViewRights() + ", " +
	        	        roleRights.getListRights() + ", " +
	        	        roleRights.getEnabledFlag() + ", " +
	        	        roleRights.getDeletedFlag() + ", " +
	        	        roleRights.getCreatedBy() + ", " +
	        	        roleRights.getCreationDate() + ", " +
	        	        roleRights.getLastUpdatedBy() + ", " +
	        	        roleRights.getLastUpdatedDate() + ", " +
	        	        roleRights.getRoleRightId()
	        	);
	            // Update existing entry
	            jdbcTemplate.update(query1,
	                    roleRights.getAddRights(),
	                    roleRights.getEditRights(),
	                    roleRights.getDeleteRights(),
	                    roleRights.getImportRights(),
	                    roleRights.getExportRights(),
	                    roleRights.getViewRights(),
	                    roleRights.getListRights(),
	                    roleRights.getEnabledFlag(),
	                    roleRights.getDeletedFlag(),
	                    roleRights.getCreatedBy(),
	                    roleRights.getCreationDate(),
	                    roleRights.getLastUpdatedBy(),
	                    roleRights.getLastUpdatedDate(),
	                    roleRights.getRoleRightId()
	            );
	        }
	        return roleRights;
	    }

	    @Override
	    public List<CmsGeneralMaster> getGMByType(String type) {
	    	String query = getgmbytype();
	        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(CmsGeneralMaster.class), type);
	    }
	   
	    @Override
	    public CmsGeneralMaster getGMById(Long id) {
	    	String query = getgmbyid();
	        return jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(CmsGeneralMaster.class), id);
	    }
	   
	    public List<CmsGeneralMaster> getAllPages() {
	    	String query = getallpages();
	        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(CmsGeneralMaster.class), "PAGE");
	    }
	   
	    public List<CmsGeneralMaster> getAllSections() {
	    	String query = getallsections();
	        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(CmsGeneralMaster.class), "SECTION");
	    }
	   
	    public List<CmsGeneralMaster> getAllRoles() {
	    	String query = getallroles();
	        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(CmsGeneralMaster.class), "ROLE");
	    }
	    
	    public List<CmsGeneralMaster> getRoles() {
	    	String query = getroles();
	        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(CmsGeneralMaster.class), "ROLE");
	    }
	   
	    public List<CmsGeneralMaster> getPages() {
	    	String query = getpages();
	        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(CmsGeneralMaster.class), "PAGE");
	    }
	    
	    public List<CmsGeneralMaster> getPagesByRole(Long roleId) {
	    	String query = getpagesbyrole();
	        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(CmsGeneralMaster.class), roleId);
	    }
		@Override
		public void save(User user) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public List<State> getAllStates() {
			String query = getallstates();
		        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(State.class));
		}
		
		@Override
		public List<GeneralMasterDTO> getGeneralMastersWithTypeName(Long gmTypeId) {
			String query = getgeneralmasterswithtypename();
		    return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(GeneralMasterDTO.class), gmTypeId);
		}
		
		@Override
		public void deleteGmDataById(Long gmId) {
			String query = deletegmdatabyid();
		    jdbcTemplate.update(query, gmId);
		}
		
		 @Override
		    public void saveGeneralMaster(GeneralMasterDTO generalMasterDTO) {
			 String query = savegeneralmaster();
		        jdbcTemplate.update(query, generalMasterDTO.getGmTypeId(), generalMasterDTO.getGmName(), generalMasterDTO.getGmDescription(),"System Admin");
		   
		 }
		
		 @Override
		    public boolean checkDuplicateRoleRight(Long roleId, Long pageId) {
			 String query = checkduplicateroleright();
			 Integer count = jdbcTemplate.queryForObject(query, Integer.class, roleId, pageId);
			 return count != null && count > 0;
		    }
		
		@Override
		public List<CmsGeneralMaster> getRolesByUserId(Integer userId) {
			String query = getrolesbyuserid();
		        return jdbcTemplate.query(
		        		query,
		            new BeanPropertyRowMapper<>(CmsGeneralMaster.class),
		            userId
		        );
		}
		 
		 public List<CmsGeneralMaster> getRolesByUserId(Long userId) {
			 String query = getrolesuserid();
		        return jdbcTemplate.query(
		        		query,
		            new BeanPropertyRowMapper<>(CmsGeneralMaster.class),
		            userId
		        );
		    }
		
		 public List<CmsGeneralMaster> getPagesByRoleId(Long roleId) {
			 String query = getpagesbyroleid();
			    LOGGER.info("Executing query with ROLE_ID: {}", roleId);
			    return jdbcTemplate.query(
			    		query,
			        new BeanPropertyRowMapper<>(CmsGeneralMaster.class),
			        roleId
			    );
			}
		
		@Override
		public List<CmsGeneralMaster> getPagesByRoleId(String roleId) {
			String query = getpagesroleid();
			 System.out.println("Controller hit: saveUsers"+roleId);
				    return jdbcTemplate.query(
				    		query,
				        new BeanPropertyRowMapper<>(CmsGeneralMaster.class),
				        roleId
				    );
		}
		 
		@Override
		public List<CmsGeneralMaster> getAvailablePagesForSection(Long sectionId) {
			String query = getavailablepagesforsection();
				    return jdbcTemplate.query(
				    		query,
				        new BeanPropertyRowMapper<>(CmsGeneralMaster.class),
				         sectionId
				    );
		}
		
		@Override
		public List<CmsGeneralMaster> getSelectedPagesForSection(Long sectionId) {
			String query = getselectedpagesforsections();
			    return jdbcTemplate.query(
			    		query,
			        new BeanPropertyRowMapper<>(CmsGeneralMaster.class),
			         sectionId
			    );
		}
		
		public void saveSectionPage(Long sectionId, List<Long> pageIds, String createdBy) {
			String query = savesectionselect();
			String query1 = savesectionupdate();
			String query2 = savesecctioninsert();
		    try {
		        for (Long pageId : pageIds) {
		            // Check if the record already exists
		            int count = jdbcTemplate.queryForObject(query, Integer.class, sectionId, pageId);
		            System.out.println("count---"+count);
		            if (count > 0) {
		                // Update the existing record to set is_active = 1
		                jdbcTemplate.update(query1, createdBy, sectionId, pageId);
		            } else {
		                // Insert a new record
		                jdbcTemplate.update(query2, sectionId, pageId, 0, createdBy, createdBy);
		            }
		        }
		        // Deactivate pages that are not selected
		        String deactivateQuery = String.format(
		        	    "UPDATE CMSSectionPage SET is_active = 1 WHERE section_id = ? AND page_id NOT IN (%s)",
		        	    String.join(",", pageIds.stream().map(String::valueOf).collect(Collectors.toList()))
		        	);
		        	jdbcTemplate.update(deactivateQuery, sectionId);
		       // jdbcTemplate.update(deactivateSql, createdBy, sectionId, String.join(",", pageIds.stream().map(String::valueOf).collect(Collectors.toList())));
		    } catch (Exception e) {
		        e.printStackTrace();
		        throw new RuntimeException("Error saving section-page mapping");
		    }
		}
		
		@Override
		public List<SectionDto> getAllSectionsWithPages() {
			String query = getallsectionswithpages();
		    List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);


		    if (rows.isEmpty()) {
		        return Collections.emptyList();
		    }
		    Map<Long, SectionDto> sectionMap = rows.stream()
		        .collect(Collectors.groupingBy(
		            row -> ((Number) row.get("sectionId")).longValue(),
		            Collectors.collectingAndThen(Collectors.toList(), sectionRows -> {
		                SectionDto sectionDto = new SectionDto();
		                sectionDto.setSectionId(((Number) sectionRows.get(0).get("sectionId")).longValue());
		                sectionDto.setSectionName((String) sectionRows.get(0).get("sectionName"));

		                sectionDto.setSectionIcon((String) sectionRows.get(0).get("sectionIcon"));


		                List<PageDto> pages = sectionRows.stream()
		                    .filter(row -> row.get("pageId") != null)
		                    .map(row -> {
		                        PageDto pageDto = new PageDto();
		                        pageDto.setPageId(((Number) row.get("pageId")).longValue());
		                        pageDto.setPageName((String) row.get("pageName"));
		                        pageDto.setPageUrl((String) row.get("pageUrl"));
		                        return pageDto;
		                    }).collect(Collectors.toList());
		                sectionDto.setPages(pages);
		                return sectionDto;
		            })
		        ));
		    return new ArrayList<>(sectionMap.values());
		}
		
		@Override
		public List<SectionDto> getSectionsByRoleId(String roleId) {
			String query = getsectionsbyroleid();
				    List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, roleId);

				    if (rows.isEmpty()) {
				        return Collections.emptyList();
				    }
				    // Group rows by sectionId and construct SectionDto objects
				    Map<Long, SectionDto> sectionMap = rows.stream()
				        .collect(Collectors.groupingBy(
				            row -> ((Number) row.get("sectionId")).longValue(),
				            Collectors.collectingAndThen(Collectors.toList(), sectionRows -> {
				                SectionDto sectionDto = new SectionDto();
				                sectionDto.setSectionId(((Number) sectionRows.get(0).get("sectionId")).longValue());
				                sectionDto.setSectionName((String) sectionRows.get(0).get("sectionName"));

				                sectionDto.setSectionIcon((String) sectionRows.get(0).get("sectionIcon"));

				                // Collect pages for this section
				                List<PageDto> pages = sectionRows.stream()
				                    .filter(row -> row.get("pageId") != null)
				                    .map(row -> {
				                        PageDto pageDto = new PageDto();
				                        pageDto.setPageId(((Number) row.get("pageId")).longValue());
				                        pageDto.setPageName((String) row.get("pageName"));
				                        pageDto.setPageUrl((String) row.get("pageUrl"));
				                        return pageDto;
				                    }).collect(Collectors.toList());
				                sectionDto.setPages(pages);
				                return sectionDto;
				            })
				        ));
				    return new ArrayList<>(sectionMap.values());
		}

		@Override
		public List<Long> getPageIdsByRoleId(String roleId) {
			String query = getpageidsbyroleid();
		    System.out.println("Fetching page IDs for roleId: " + roleId);
		    return jdbcTemplate.queryForList(query, Long.class, roleId);
		}
		 
		public boolean hasPageAccessForRole(String roleId, Long pageId) {
			String query = haspageaccessforrole();
			 System.out.println("common roleId: " + roleId);
			 System.out.println("common for pageId: " + pageId);
		    Integer count = jdbcTemplate.queryForObject(query, Integer.class, roleId, pageId);
		    System.out.println("common for count: " + count);
		    return count > 0;
		}
		
		@Override
		public List<PersonOrgLevel> getPersonOrgLevelDetails(String userAccount) {
			String query = getpersonorgleveldetails();
			List<PersonOrgLevel> personOrgList= new ArrayList<PersonOrgLevel>();
			SqlRowSet rs = jdbcTemplate.queryForRowSet(getPersonOrgLevel(),userAccount);

			while(rs.next()) {
				PersonOrgLevel pol = new PersonOrgLevel();
				pol.setLevelDef(rs.getString("LevelDef"));
				pol.setOleId(rs.getString("OleId"));
				pol.setId(rs.getString("Id"));
				pol.setDescription(rs.getString("Description"));
				personOrgList.add(pol);
			}
			return personOrgList;
		}

	@Override
	public List<CMSRoleRights> getRoleRightsByRoleAndPage(Long roleId, Long pageId) {
		return List.of();
	}


	@SuppressWarnings("deprecation")
		public List<CMSRoleRights> getPersonOrgLevelDetailsFORMAPPING(Long roleId, Long pageId) {
			String query = getrolerightsbyroleandpage();
		    return jdbcTemplate.query(query, new Object[]{roleId, pageId}, (rs, rowNum) -> {
		        CMSRoleRights rights = new CMSRoleRights();
		        rights.setRoleRightId(rs.getLong("ROLE_RIGHT_ID"));
		        rights.setRoleId(rs.getLong("ROLE_ID"));
		        rights.setPageId(rs.getLong("PAGE_ID"));

		        CmsGeneralMaster role = new CmsGeneralMaster();
		        role.setGmId(String.valueOf(rs.getLong("ROLE_ID")));
		        role.setGmName(rs.getString("roleName"));
		        rights.setRole(role);

		        CmsGeneralMaster page = new CmsGeneralMaster();
		        page.setGmId(String.valueOf(rs.getLong("PAGE_ID")));
		        page.setGmName(rs.getString("pageName"));
		        rights.setPage(page);

		        rights.setAddRights(rs.getInt("ADD_RIGHTS"));
		        rights.setEditRights(rs.getInt("EDIT_RIGHTS"));
		        rights.setDeleteRights(rs.getInt("DELETE_RIGHTS"));
		        rights.setImportRights(rs.getInt("IMPORT_RIGHTS"));
		        rights.setExportRights(rs.getInt("EXPORT_RIGHTS"));
		        rights.setViewRights(rs.getInt("VIEW_RIGHTS"));
		        rights.setListRights(rs.getInt("LIST_RIGHTS"));
		        rights.setEnabledFlag(rs.getInt("ENABLED_FLAG"));
		        rights.setDeletedFlag(rs.getInt("DELETED_FLAG"));

		        return rights;
		    });

		}
		public CmsGeneralMaster findByGMName(Long gmTypeId, String gmName) {
			String query = getduplicategmmaster();
	        try {
	            return jdbcTemplate.queryForObject(query, new Object[]{gmName,gmTypeId}, new BeanPropertyRowMapper<>(CmsGeneralMaster.class));
	        } catch (EmptyResultDataAccessException e) {
	            return null;  // Return null or handle the absence of a result in your service layer
	        }
	    }
		public boolean isDuplicateGMName(Long gmTypeId, String gmName) {
			String query = isDuplicateGMName();
		   // String query = "SELECT COUNT(*) from CMSGMTYPE cgt join CMSGENERALMASTER cgm on cgt.GMTYPEID=cgm.GMTYPEID where cgm.GMNAME=? and cgt.GMTYPEID=? and cgm.ISACTIVE=1 ";
		    Integer count = jdbcTemplate.queryForObject(query, new Object[]{gmName, gmTypeId}, Integer.class);

		    return count != null && count > 0; 
		}
		
		public void deleteRoleRights(List<Integer> roleIds) {
			String query = deleteRoleRights();
		  //  String sql = "UPDATE CMSROLERIGHTS SET DELETED_FLAG = '1' WHERE ROLE_RIGHT_ID = ?";
		    for (Integer roleId : roleIds) {
		    	 System.out.println("Updating ROLE_RIGHT_ID: " + roleId);
		    	    int updatedRows = jdbcTemplate.update(query, roleId);
		    	    System.out.println("Rows affected: " + updatedRows);
		    }
		}
		@Override
		public CMSRoleRights hasPageActionPermissionForRole(String roleId, String pageDescription) {
		    String query=getPageActionPermissionForRoleQuery();
		    CMSRoleRights rr = new CMSRoleRights();
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query,pageDescription,roleId);
			if(rs.next()) {
				rr.setAddRights(rs.getBoolean("ADD_RIGHTS")? 1 : 0);  // Changed getInt() to getBoolean()
		        rr.setEditRights(rs.getBoolean("EDIT_RIGHTS")? 1 : 0);
		        rr.setDeleteRights(rs.getBoolean("DELETE_RIGHTS")? 1 : 0);
		        rr.setImportRights(rs.getBoolean("IMPORT_RIGHTS")? 1 : 0);
		        rr.setExportRights(rs.getBoolean("EXPORT_RIGHTS")? 1 : 0);
		        rr.setViewRights(rs.getBoolean("VIEW_RIGHTS")? 1 : 0);
			}
			return rr;
		}

		@Override
		public void deleteOrgLevel(List<Long> orgLevelDefIds) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public List<OrgLevel> getAllOrgLevels() {
			// TODO Auto-generated method stub
			return null;
		}
		
		public List<CmsGeneralMaster> getImportOptionsByRole(String roleId) {
			String sql=getImportOptionsByRole();
		    //String sql = "select cgm.GMNAME from CMSGENERALMASTER cgm join CMSROLERIGHTS crr on crr.PAGE_ID=cgm.GMID where crr.ROLE_ID=? and cgm.GMNAME like 'data%' and crr.DELETED_FLAG=0";

		    return jdbcTemplate.query(sql, new Object[]{roleId}, (rs, rowNum) -> {
		        CmsGeneralMaster gm = new CmsGeneralMaster();
		        gm.setGmName(rs.getString("GMNAME"));
		        return gm;
		    });
}
		@Override
		public String getGMID(Long gmtypeId,String gmName) {
			String result=null;
			String query ="SELECT gm.GMID FROM CmsGeneralMaster gm "
					+ " INNER JOIN CMSGMType gmt ON gm.gmTypeId = gmt.gmTypeId "
					+ " WHERE gmt.gmType in('SKILL','TRADE') and gm.GMTYPEID=? and gm.ISACTIVE=1 and gm.GMNAME=?  ";
			 SqlRowSet rs = jdbcTemplate.queryForRowSet(query,gmtypeId,gmName);
			 while(rs.next()) {
				result =String.valueOf( rs.getInt("GMID"));
				}
			 return result;
		}
}