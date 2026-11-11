package com.wfd.dot1.cwfm.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wfd.dot1.cwfm.dao.SelfRegistrationReviewDao;
import com.wfd.dot1.cwfm.service.SelfRegistrationReviewService;
import com.wfd.dot1.cwfm.service.SelfRegistrationReviewService.AuthorizedPair;

@Service
public class SelfRegistrationReviewServiceImpl implements SelfRegistrationReviewService {

	private final SelfRegistrationReviewDao reviewDao;

	private final ObjectMapper json = new ObjectMapper();

	public SelfRegistrationReviewServiceImpl(SelfRegistrationReviewDao reviewDao) {

		this.reviewDao = reviewDao;
	}

	// 1. LIST REGISTRATIONS
	@Override
	public List<Map<String, Object>> list(String unitId, String contractorId) {

		return reviewDao.list(unitId, contractorId);
	}

	// 2. FETCH REGISTRATION DETAILS INTERNALLY
	private Map<String, Object> fetchDetail(long registrationId) {

		Map<String, Object> registration = reviewDao.fetchDetail(registrationId);

		if (registration == null || registration.isEmpty()) {

			throw new NoSuchElementException("Registration not found: " + registrationId);
		}

		return registration;
	}

	// 3. FETCH REGISTRATION WITH AUTHORIZATION
	@Override
	public Map<String, Object> detail(long registrationId, List<AuthorizedPair> authorizedPairs) {

		Map<String, Object> registration = fetchDetail(registrationId);

		String unitId = String.valueOf(registration.get("UnitId"));

		String contractorId = String.valueOf(registration.get("ContractorId"));

		boolean allowed = authorizedPairs != null && authorizedPairs.stream().anyMatch(
				pair -> pair != null && unitId.equals(pair.unitId()) && contractorId.equals(pair.contractorId()));

		if (!allowed) {

			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to view this registration");
		}

		return registration;
	}

	// 4. PARSE REGISTRATION DATA JSON
	@Override
	public Map<String, String> fields(Map<String, Object> registration) {

		try {

			Object raw = registration.get("RegistrationData");

			if (raw == null) {
				return Collections.emptyMap();
			}

			return json.readValue(raw.toString(), new TypeReference<Map<String, String>>() {
			});

		} catch (Exception ex) {

			throw new IllegalStateException("Invalid registration JSON", ex);
		}
	}

	// 5. FETCH DOCUMENT METADATA WITH AUTHORIZATION
	@Override
	public List<Map<String, Object>> documents(long registrationId, List<AuthorizedPair> authorizedPairs) {

		// Ensure supervisor is authorized for this registration.
		detail(registrationId, authorizedPairs);

		return reviewDao.documents(registrationId);
	}

	// 6. FETCH DOCUMENT FOR REVIEW WITH AUTHORIZATION
	@Override
	public Map<String, Object> documentForReview(long documentId, List<AuthorizedPair> authorizedPairs) {

		Map<String, Object> doc = reviewDao.documentForReview(documentId);

		if (doc == null || doc.isEmpty()) {

			throw new NoSuchElementException("Document not found");
		}

		long registrationId = ((Number) doc.get("RegistrationId")).longValue();

		// Checks registration's PE + contractor authorization.
		detail(registrationId, authorizedPairs);

		return doc;
	}

	// 7. REJECT REGISTRATION
	@Override
	@Transactional
	public void reject(long registrationId, String reason, String reviewedBy, List<AuthorizedPair> pairs) {

		if (reason == null || reason.isBlank() || reason.trim().length() > 1000) {

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Rejection reason is required (maximum 1000 characters).");
		}

		// Detail checks registration-level PE + Contractor authorization.
		detail(registrationId, pairs);

		int updated = reviewDao.reject(registrationId, reason.trim(), reviewedBy);

		if (updated != 1) {

			throw new ResponseStatusException(HttpStatus.CONFLICT,
					"Registration has already been processed or cannot be rejected.");
		}
	}
}