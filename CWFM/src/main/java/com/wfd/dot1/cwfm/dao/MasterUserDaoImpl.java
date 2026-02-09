package com.wfd.dot1.cwfm.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.queries.MasterUserQueryBank;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;
@Repository
public class MasterUserDaoImpl implements MasterUserDao{

	private static final Logger log = LoggerFactory.getLogger(MasterUserDaoImpl.class.getName());
	
	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	 
	 public String getMasterUserByUsernameAndPassword() {
		    return QueryFileWatcher.getQuery("GET_MASTERUSER_BY_USERACCOUNT");
	    }
	 public String getMasterUserByUserAccount() {
		    return QueryFileWatcher.getQuery("GET_MASTERUSER_BY_USERACCOUNT");
	    }
	 public String getPasswordByUserIdQuery() {
		    return QueryFileWatcher.getQuery("GET_PASSWORD_BY_USERID");
	    }
	 public String updatePasswordQuery() {
		    return QueryFileWatcher.getQuery("GET_UPDATE_PASSWORD");
	    }
	 public String getMasterUserAccount() {
		    return QueryFileWatcher.getQuery("GET_MASTER_USERACCOUNT");
	    }
	 
	@Override
	public MasterUser findMasterUserDetailsByUserName(String username, String password) {
		 String query = getMasterUserByUsernameAndPassword();
		log.info("Entering into findMasterUserDetailsByUserName dao method "+username+" "+password);
		MasterUser user =null;
		log.info("Query to findMasterUserDetailsByUserName "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query, username,password);
		if (rs.next()) {
			user = new MasterUser();
			user.setUserId(rs.getInt("UserId"));
			user.setUserAccount(rs.getString("UserAccount"));
			user.setEmailId(rs.getString("EmailId"));
			user.setFirstName(rs.getString("FirstName"));
			user.setLastName(rs.getString("LastName"));
			user.setContactNumber(rs.getString("ContactNumber"));
			user.setStatus(rs.getString("Status"));
			user.setPassword(rs.getString("Password"));
			log.info("Exiting from findMasterUserDetailsByUserName dao method "+user.toString());
		}
		
		return user;
	}
	@Override
	public MasterUser findMasterUserDetailsByUserName(String ua) {
		String query = getMasterUserByUserAccount();
		MasterUser user =null;
		log.info("Query to findMasterUserDetailsByUserName "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query, ua);
		if (rs.next()) {
			user = new MasterUser();
			user.setUserId(rs.getInt("UserId"));
			user.setUserAccount(rs.getString("UserAccount"));
			user.setEmailId(rs.getString("EmailId"));
			user.setFirstName(rs.getString("FirstName"));
			user.setLastName(rs.getString("LastName"));
			user.setContactNumber(rs.getString("ContactNumber"));
			user.setStatus(rs.getString("Status"));
			user.setPassword(rs.getString("Password"));
			log.info("Exiting from findMasterUserDetailsByUserName dao method "+user.toString());
		}
		
		return user;
	}
	
	@Override
    public String getPasswordByUserId(String userId) {
		 String query = getPasswordByUserIdQuery();
        return jdbcTemplate.queryForObject(query, new Object[]{userId}, String.class);
    }

    @Override
    public void updatePassword(String userId, String newPassword) {
    	 String query = updatePasswordQuery();
        jdbcTemplate.update(query, newPassword, userId);
    }
	@Override
	public boolean existsByUserAccount(String userAccount) {
		 String sql = getMasterUserAccount();
	        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userAccount);
	        return count != null && count > 0;
	}
}

