package com.wfd.dot1.cwfm.dao;

import java.util.List;

import org.springframework.security.core.userdetails.User;

import com.wfd.dot1.cwfm.dto.ChangePasswordDTO;
import com.wfd.dot1.cwfm.dto.ResetPasswordDTO;
import com.wfd.dot1.cwfm.pojo.MasterUser;

public interface UserDAO {
    MasterUser getUserById(String userId);
    List<MasterUser> getAllUsers();
    void saveUser(MasterUser user);
    void updateUser(MasterUser user);
    void deleteUser(String userId);
	void saveUser(MasterUser user, List<Long> roleIds);
	boolean changeUserPassword(ChangePasswordDTO changePasswordDTO);
	boolean resetUserPassword(ResetPasswordDTO resetPasswordDTO);
	MasterUser viewUserDetails(String userId);
	List<Long> getUserRoleIds(String userId);
	void updateUser(MasterUser user, List<Long> roleIds);
	void deleteUsers(List<Integer> userIds);
	List<String> getRolesByUserId(Long userId);
	boolean doesUserExist(String userAccount);
	List<MasterUser> getUserWithUserAccount(String userAccount);
}
