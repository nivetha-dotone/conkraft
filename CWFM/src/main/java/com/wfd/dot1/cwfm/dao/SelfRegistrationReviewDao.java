package com.wfd.dot1.cwfm.dao;

import java.util.List;
import java.util.Map;

public interface SelfRegistrationReviewDao {

	List<Map<String, Object>> list(String unitId, String contractorId);

	Map<String, Object> fetchDetail(long registrationId);

	List<Map<String, Object>> documents(long registrationId);

	Map<String, Object> documentForReview(long documentId);

	int reject(long registrationId, String reason, String reviewedBy);
}