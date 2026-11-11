package com.wfd.dot1.cwfm.service;

import java.util.List;
import java.util.Map;

public interface SelfRegistrationReviewService {

	List<Map<String, Object>> list(String unitId, String contractorId);

	Map<String, Object> detail(long registrationId, List<AuthorizedPair> authorizedPairs);

	Map<String, String> fields(Map<String, Object> registration);

	List<Map<String, Object>> documents(long registrationId, List<AuthorizedPair> authorizedPairs);

	Map<String, Object> documentForReview(long documentId, List<AuthorizedPair> authorizedPairs);

	void reject(long registrationId, String reason, String reviewedBy, List<AuthorizedPair> pairs);

	record AuthorizedPair(String unitId, String contractorId) {
	}
}