package com.wfd.dot1.cwfm.dao;

import com.wfd.dot1.cwfm.pojo.RegistrationLink;
import com.wfd.dot1.cwfm.pojo.RegistrationLinkDetails;

public interface RegistrationLinkDao {
	public Long saveRegistrationLink( RegistrationLink link, String tokenHash) ;
	public RegistrationLink findValidLinkByTokenHash(String tokenHash);
	RegistrationLinkDetails getRegistrationLinkDetails(Long linkId);

}
