package com.wfd.dot1.cwfm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.MasterUserDao;
import com.wfd.dot1.cwfm.pojo.MasterUser;
@Service
public class MasterUserServiceImpl implements MasterUserService{

	private static final Logger log = LoggerFactory.getLogger(MasterUserServiceImpl.class.getName());
	private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	@Autowired
	MasterUserDao masterUserDao;
	
	
	@Override
	public MasterUser findMasterUserDetailsByUserName(String username, String password) {
		log.info("Entering into findMasterUserDetailsByUserName service method "+username+" "+password);
		return masterUserDao.findMasterUserDetailsByUserName(username, password);
	}
	@Override
    public boolean validateOldPassword(String userId, String password) {
        String storedPassword = masterUserDao.getPasswordByUserId(userId);
        return storedPassword != null && storedPassword.equals(password); // Use hashed password in real applications
    }

    @Override
    public void updatePassword(String userId, String newPassword) {
    	masterUserDao.updatePassword(userId, newPassword); // Hash password before saving in real applications
    }
	
	@Override
	public MasterUser findMasterUserDetailsByUserName(String username) {
		return masterUserDao.findMasterUserDetailsByUserName(username);
	}
	@Override
	public boolean existsByUserAccount(String name) {
		return masterUserDao.existsByUserAccount(name);
	}
}
