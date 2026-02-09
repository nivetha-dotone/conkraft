package com.wfd.dot1.cwfm.service;

import com.wfd.dot1.cwfm.pojo.MasterUser;

public interface MasterUserService {

	public MasterUser findMasterUserDetailsByUserName(String username,String password);

	public boolean validateOldPassword(String UserId, String password);

	public void updatePassword(String UserId, String newPassword);

	MasterUser findMasterUserDetailsByUserName(String username);

	public boolean existsByUserAccount(String name);

	
}
