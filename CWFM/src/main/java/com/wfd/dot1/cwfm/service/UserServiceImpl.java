package com.wfd.dot1.cwfm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.UserDAO;
import com.wfd.dot1.cwfm.dto.ChangePasswordDTO;
import com.wfd.dot1.cwfm.dto.ResetPasswordDTO;
import com.wfd.dot1.cwfm.pojo.MasterUser;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public MasterUser getUserById(String userId) {
        return userDAO.getUserById(userId);
    }

    @Override
    public List<MasterUser> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public void saveUser(MasterUser user) {
        userDAO.saveUser(user);
    }

    @Override
    public void updateUser(MasterUser user) {
        userDAO.updateUser(user);
    }

    @Override
    public void deleteUser(String userId) {
        userDAO.deleteUser(userId);
    }

	@Override
	public void saveUser(MasterUser user, List<Long> roleIds) {
		userDAO.saveUser(user,roleIds);	
	}

	@Override
	public boolean changeUserPassword(ChangePasswordDTO changePasswordDTO) {
		return userDAO.changeUserPassword(changePasswordDTO);
	}

	@Override
	public boolean resetUserPassword(ResetPasswordDTO resetPasswordDTO) {
		return userDAO.resetUserPassword(resetPasswordDTO);
	}
	@Override
	public MasterUser viewUserDetails(String userId) {
		return userDAO.viewUserDetails(userId);	
	}

	@Override
	public List<Long> getUserRoleIds(String userId) {
		return userDAO.getUserRoleIds(userId);
	}

	@Override
	public void updateUser(MasterUser user, List<Long> roleIds) {
		userDAO.updateUser(user,roleIds);		
	}

	@Override
	public void deleteUsers(List<Integer> userIds) {
		userDAO.deleteUsers(userIds);
	}

	@Override
	public List<String> getRolesByUserId(Long userId) {
		return userDAO.getRolesByUserId(userId);
	}

	@Override
	public boolean doesUserExist(String userAccount) {
		return userDAO.doesUserExist(userAccount);
	}
	 @Override
	    public List<MasterUser> getUserWithUserAccount(String userAccount) {
	        return userDAO.getUserWithUserAccount(userAccount);
	    }
}
