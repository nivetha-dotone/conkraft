package com.wfd.dot1.cwfm.dao;

import com.wfd.dot1.cwfm.pojo.MasterUser;

public interface MasterUserDao {

	public MasterUser findMasterUserDetailsByUserName(String username,String password);

	public void updatePassword(String userId, String newPassword);

	public String getPasswordByUserId(String userId);
	
	public MasterUser findMasterUserDetailsByUserName(String ua);

	public boolean existsByUserAccount(String name);

	
}
