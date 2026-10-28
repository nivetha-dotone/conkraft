package com.wfd.dot1.cwfm.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Base64;
import java.util.HexFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.RegistrationLinkDao;
import com.wfd.dot1.cwfm.pojo.RegistrationLink;

@Service
public class RegistrationLinkService {

    @Autowired
    private RegistrationLinkDao registrationLinkDao;

    private final SecureRandom secureRandom = new SecureRandom();


public String generateRegistrationLink(
        RegistrationLink link,
        String supervisorId,
        String baseUrl) {

    // 1. Validate request
    if (link == null || supervisorId == null
            || supervisorId.isBlank()) {
        throw new IllegalArgumentException(
                "Invalid registration request");
    }

    if (baseUrl == null || baseUrl.isBlank()) {
        throw new IllegalArgumentException(
                "Registration base URL is not configured");
    }
    // 2. Validate expiry date
    LocalDateTime expiryDate;

    try {
        expiryDate = LocalDateTime.parse(link.getExpiryDate());
    } catch (DateTimeParseException e) {
        throw new IllegalArgumentException("Invalid expiry date format");
    }

    if (!expiryDate.isAfter(LocalDateTime.now())) {
        throw new IllegalArgumentException("Expiry date must be in the future");
    }

    if (expiryDate.isAfter(LocalDateTime.now().plusDays(30))) {
        throw new IllegalArgumentException("Expiry date cannot exceed 30 days");
    }
  
    

   

    

    // 3. Validate mandatory fields
    validateRequiredFields(link);

    // 4. Set logged-in supervisor
    link.setCreatedBy(supervisorId);

    // 5. Generate secure random token
    byte[] randomBytes = new byte[32];
    secureRandom.nextBytes(randomBytes);

    String token = Base64.getUrlEncoder()
            .withoutPadding()
            .encodeToString(randomBytes);

    // 6. Store token hash, not raw token
    String tokenHash = hashToken(token);

    Long linkId = registrationLinkDao
            .saveRegistrationLink(link, tokenHash);

    link.setLinkId(linkId);

    // 7. Return registration URL
    return baseUrl.replaceAll("/+$", "")
            + "/register/" + token;
}


    public String hashToken(String token) {
        try {
            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(
                    token.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(hash);

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Unable to generate token hash", e);
        }
    }

    private void validateRequiredFields(RegistrationLink link) {

        if (isBlank(link.getUnitId())
                || isBlank(link.getContractorId())
                || isBlank(link.getWorkorderId())
                || isBlank(link.getDepartmentId())
                || isBlank(link.getAreaId())
                || isBlank(link.getWcEsicNo())
                || isBlank(link.getLlNo())) {

            throw new IllegalArgumentException(
                    "Please select all mandatory fields");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
    
    public RegistrationLink validateAndGetLink(String token) {

        if (token == null ||
            !token.matches("^[A-Za-z0-9_-]{43}$")) {
            throw new IllegalArgumentException("Invalid registration link");
        }

        String tokenHash = hashToken(token);

        RegistrationLink link =
                registrationLinkDao.findValidLinkByTokenHash(tokenHash);

        if (link == null) {
            throw new IllegalArgumentException(
                    "Registration link is invalid, expired or deactivated");
        }

        return link;
    }
}