package com.wfd.dot1.cwfm.queries;

public interface MasterUserQueryBank {

	String GET_MASTERUSER_BY_USERNAME_AND_PASSWROD ="select mu.UserId,mu.EmailId,mu.FirstName,mu.LastName,mu.UserAccount,mu.RoleName,mu.ContactNumber,mu.Status,mu.BusinessType "
			+ "	 from MasterUser mu where mu.UserAccount=? and mu.Password=?";
	

}
