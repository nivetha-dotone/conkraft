package com.wfd.dot1.cwfm.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.dto.ChangePasswordDTO;
import com.wfd.dot1.cwfm.dto.ResetPasswordDTO;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
  
    public String getuserbyid() {
	    return QueryFileWatcher.getQuery("GET_USER_BY_ID");
    }
    public String getallusers() {
	    return QueryFileWatcher.getQuery("GET_ALL_USERS");
    }
    public String saveuser() {
	    return QueryFileWatcher.getQuery("SAVE_USERS");
    }
    public String updateuser() {
	    return QueryFileWatcher.getQuery("UPDATE_USERS");
    }
    public String deleteuser() {
	    return QueryFileWatcher.getQuery("DELETE_USERS");
    }
    public String saveusers() {
	    return QueryFileWatcher.getQuery("SAVE_USER");
    }
    public String insertrolemapping() {
	    return QueryFileWatcher.getQuery("INSERT_ROLE_MAPPING");
    }
    public String changeuserpasswordselect() {
	    return QueryFileWatcher.getQuery("CHANGE_USERPASSWORD_SELECT");
    }
    public String changeuserpasswordupdate() {
	    return QueryFileWatcher.getQuery("CHANGE_USERPASSWORD_UPDATE");
    }
    public String resetUserPasswordselect() {
	    return QueryFileWatcher.getQuery("RESET_USERPASSWORD_SELECT");
    }
    public String resetUserPasswordupdate() {
	    return QueryFileWatcher.getQuery("RESET_USERPASSWORD_UPDATE");
    }
    public String getUserRoleIds() {
	    return QueryFileWatcher.getQuery("GET_USER_ROLE_IDS");
    }
    public String getUserRole() {
	    return QueryFileWatcher.getQuery("GET_USER_ROLE");
    }
    public String updateuserRole() {
	    return QueryFileWatcher.getQuery("UPDATE_USER_ROLE");
    }
    public String deleteuserRole() {
	    return QueryFileWatcher.getQuery("DELETE_USER_ROLE");
    }
    public String insertuserRole() {
	    return QueryFileWatcher.getQuery("INSERT_USER_ROLE");
    }
    public String deleteUsers() {
	    return QueryFileWatcher.getQuery("DELETE_USER");
    }
    public String getRolesByUserId() {
	    return QueryFileWatcher.getQuery("GET_ROLES_BY_USER");
    }
    public String doesUserExist() {
	    return QueryFileWatcher.getQuery("DOES_USER_EXIST");
    }
    public String getUserWithUserAccount() {
	    return QueryFileWatcher.getQuery("GET_USER_WITH_USERACCOUNT");
    }
    private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";

    @Override
    public MasterUser getUserById(String userId) {
    	String query=getuserbyid();
        return jdbcTemplate.queryForObject(query, new Object[]{userId}, (rs, rowNum) -> {
            MasterUser user = new MasterUser();
            user.setUserId(rs.getInt("userId"));
            user.setUserAccount(rs.getString("userAccount"));
            user.setEmailId(rs.getString("emailId"));
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("lastName"));
            user.setContactNumber(rs.getString("contactNumber"));
            user.setStatus(rs.getString("status"));
            user.setPassword(rs.getString("password"));
            user.setFullName(rs.getString("firstName") + " " + rs.getString("lastName")); // Calculate fullName
            return user;
        });
    }

    @Override
    public List<MasterUser> getAllUsers() {
    	String query=getallusers();
    	
        return jdbcTemplate.query(query, (rs, rowNum) -> {

            MasterUser user = new MasterUser();
            user.setUserId(rs.getInt("userId"));
            user.setUserAccount(rs.getString("userAccount"));
            user.setEmailId(rs.getString("emailId"));
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("lastName"));
            user.setContactNumber(rs.getString("contactNumber"));
            user.setStatus(rs.getString("status"));
            user.setPassword(rs.getString("password"));
            user.setFullName(rs.getString("firstName") + " " + rs.getString("lastName")); // Calculate fullName
            return user;
        });
    }


    @Override
    public void saveUser(MasterUser user) {
    	String query=saveuser();
        jdbcTemplate.update(query, 
            user.getUserId(),
            user.getUserAccount(),
            user.getEmailId(),
            user.getFirstName(),
            user.getLastName(),
            user.getFullName(),
            user.getContactNumber(),
            user.getStatus(),
            user.getPassword()
        );
    }

    @Override
    public void updateUser(MasterUser user) {
    	String query=updateuser();
        jdbcTemplate.update(query, 
            user.getUserAccount(),
            user.getEmailId(),
            user.getFirstName(),
            user.getLastName(),
            user.getFullName(),
            user.getContactNumber(),
            user.getStatus(),
            user.getPassword(),
            user.getUserId()
        );
    }

    @Override
    public void deleteUser(String userId) {
    	String query=deleteuser();
        jdbcTemplate.update(query, userId);
    }
    
    public void saveUser(MasterUser user, List<Long> roleIds) {
        String query=saveusers();
        String query1=insertrolemapping();
        KeyHolder keyHolder = new GeneratedKeyHolder();

        // Encrypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Insert the user and retrieve the generated key
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[] {"UserId"});
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmailId());
            ps.setString(4, user.getContactNumber());
            ps.setString(5, user.getPassword());
            ps.setString(6, user.getUserAccount());
            return ps;
        }, keyHolder);
        // Set the generated UserId in the user object
        int userId = keyHolder.getKey().intValue();
        user.setUserId(userId);
        // Save role mappings if provided
        if (roleIds != null) {
            for (Long roleId : roleIds) {
               // String insertRoleMappingQuery = "INSERT INTO UserRoleMapping (UserId, RoleId) VALUES (?, ?)";
                jdbcTemplate.update(query1, user.getUserId(), roleId);
            }
        }

    }


    @Override
    public boolean changeUserPassword(ChangePasswordDTO changePasswordDTO) {
    	String selectquery=changeuserpasswordselect();
    	String updatequery=changeuserpasswordupdate();
    	 if (!isValidPassword(changePasswordDTO.getNewPassword())) {
             throw new IllegalArgumentException("Password does not meet security requirements!");
         }
        //String selectQuery = "SELECT password FROM MASTERUSER WHERE userAccount = ? AND Status='A' ";

        try {
            @SuppressWarnings("deprecation")
			List<String> passwords = jdbcTemplate.query(selectquery, new Object[]{changePasswordDTO.getUserAccount()},
                (rs, rowNum) -> rs.getString("password"));

            if (passwords.isEmpty()) {
                System.out.println("User not found or inactive.");
                return false;  // No user found
            }

            String storedPassword = passwords.get(0);

            if (passwordEncoder.matches(changePasswordDTO.getOldPassword(), storedPassword)) {
                // If old password matches, update to the new password
               // String updateQuery = "UPDATE MASTERUSER SET password = ? WHERE userAccount = ? AND Status='A' ";
                jdbcTemplate.update(updatequery, passwordEncoder.encode(changePasswordDTO.getNewPassword()), changePasswordDTO.getUserAccount());
                return true;
            } else {
                System.out.println("Old password does not match.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }
    public boolean resetUserPassword(ResetPasswordDTO resetPasswordDTO) {
    	String selectquery=resetUserPasswordselect();
    	String updatequery=resetUserPasswordupdate();
    	if (!isValidPassword(resetPasswordDTO.getNewPassword())) {
            throw new IllegalArgumentException("Password does not meet security requirements!");
        }
        //String selectQuery = "SELECT COUNT(*) FROM MASTERUSER WHERE userAccount = ? AND Status='A'";

        int count = jdbcTemplate.queryForObject(selectquery, new Object[]{resetPasswordDTO.getUserAccount()}, Integer.class);

        if (count > 0) {
            //String updateQuery = "UPDATE MASTERUSER SET password = ? WHERE userAccount = ? AND Status='A'";
            jdbcTemplate.update(updatequery, passwordEncoder.encode(resetPasswordDTO.getNewPassword()), resetPasswordDTO.getUserAccount());
            return true;
        }
        return false;
    }
    private boolean isValidPassword(String password) {
        return Pattern.matches(PASSWORD_REGEX, password);
    }
    public String viewUserDetailsQuery() {
	    return QueryFileWatcher.getQuery("GET_VIEW_USER_DETAILS");
    }
 @Override
    public MasterUser viewUserDetails(String unitId) {
    	String query = viewUserDetailsQuery();
        return jdbcTemplate.queryForObject(query, new Object[]{unitId}, (rs, rowNum) -> {
            MasterUser user = new MasterUser();
            user.setUserId(Integer.parseInt(unitId));
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("lastName"));
            user.setEmailId(rs.getString("emailId"));
            user.setContactNumber(rs.getString("contactNumber"));
            user.setPassword(rs.getString("password"));
            user.setUserAccount(rs.getString("userAccount"));
            
            return user;
        });
    }
 public List<Long> getUserRoleIds(Long userId) {
	 String query = viewUserDetailsQuery();
	    //String query = "SELECT RoleId FROM UserRoleMapping WHERE UserId = ?";
	    return jdbcTemplate.queryForList(query, Long.class, userId);
	}
 


@Override
public List<Long> getUserRoleIds(String userId) {
	String query = getUserRole();
	// String query = "SELECT RoleId FROM UserRoleMapping WHERE UserId = ?";
	    return jdbcTemplate.queryForList(query, Long.class, userId);
}
@Override
public void updateUser(MasterUser user, List<Long> roleIds) {
	String updateRoleSql = updateuserRole();
	String deleteRolesSql = deleteuserRole();
	String insertRoleSql = insertuserRole();
   // String sql = "UPDATE MASTERUSER SET userAccount = ?, EmailId = ?, FirstName = ?, LastName = ?, ContactNumber = ?, Password = ? WHERE UserId = ?";
    jdbcTemplate.update(updateRoleSql, 
        user.getUserAccount(),
        user.getEmailId(),
        user.getFirstName(),
        user.getLastName(),
        user.getContactNumber(),
        user.getPassword(),
        user.getUserId()
    );

   // String deleteRolesSql = "DELETE FROM UserRoleMapping WHERE UserId = ?";
    jdbcTemplate.update(deleteRolesSql, user.getUserId());

    //String insertRoleSql = "INSERT INTO UserRoleMapping (UserId, RoleId) VALUES (?, ?)";
    for (Long roleId : roleIds) {
        jdbcTemplate.update(insertRoleSql, user.getUserId(), roleId);
    }
}
public void deleteUsers(List<Integer> userIds) {
	String query = deleteUsers();
    //String sql = "UPDATE MASTERUSER SET status = 'NA' WHERE UserId = ?";
    for (Integer userId : userIds) {
        jdbcTemplate.update(query, userId);
    }
}

@Override
public List<String> getRolesByUserId(Long userId) {
	String query = getRolesByUserId();
	/*
	 * String sql = """ SELECT STRING_AGG(gm.GMNAME, '; ') AS RoleNames FROM
	 * UserRoleMapping urm JOIN CMSGENERALMASTER gm ON urm.RoleId = gm.GMID WHERE
	 * urm.UserId = ? """;
	 */

    return jdbcTemplate.query(query, (rs, rowNum) -> rs.getString("RoleNames"), userId);
}

public boolean doesUserExist(String userAccount) {
	String query = doesUserExist();
    //String sql = "SELECT COUNT(*) FROM MASTERUSER WHERE userAccount = ? AND Status='A'";
    Integer count = jdbcTemplate.queryForObject(query, Integer.class, userAccount);
    return count != null && count > 0;
}
@Override
public List<MasterUser> getUserWithUserAccount(String userAccount) {
	String query = getUserWithUserAccount();
    //String query = "SELECT userid,userAccount, password,EmailId, FirstName, LastName, ContactNumber, Status FROM MASTERUSER WHERE userAccount = ?";

    return jdbcTemplate.query(query, (rs, rowNum) -> {
        MasterUser user = new MasterUser();
        // Assuming you have userId and password if needed later
         user.setUserId(rs.getInt("userId"));
        user.setUserAccount(rs.getString("userAccount"));
        user.setPassword(rs.getString("password"));
        user.setEmailId(rs.getString("EmailId"));
        user.setFirstName(rs.getString("FirstName"));
        user.setLastName(rs.getString("LastName"));
        user.setContactNumber(rs.getString("ContactNumber"));
        user.setStatus(rs.getString("Status"));
        return user;
    }, userAccount); // Pass userAccount as query parameter
}

}
