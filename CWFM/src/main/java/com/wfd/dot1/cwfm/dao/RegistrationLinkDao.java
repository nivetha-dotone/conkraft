package com.wfd.dot1.cwfm.dao;

import com.wfd.dot1.cwfm.pojo.RegistrationLink;

public interface RegistrationLinkDao {
	public Long saveRegistrationLink( RegistrationLink link, String tokenHash) ;

}
