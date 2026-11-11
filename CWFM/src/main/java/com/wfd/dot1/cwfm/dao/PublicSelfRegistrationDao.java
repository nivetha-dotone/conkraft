package com.wfd.dot1.cwfm.dao;

public interface PublicSelfRegistrationDao {

	long insertRegistration(long linkId, String aadhaar, String firstName, String lastName, String mobileNumber,
			String registrationData);

	void insertRegistrationDocument(long registrationId, String documentType, String storedFileName,
			String storagePath);
}