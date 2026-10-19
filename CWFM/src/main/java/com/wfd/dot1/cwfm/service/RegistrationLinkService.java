package com.wfd.dot1.cwfm.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.LocalDateTime;
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

        if (link == null || supervisorId == null
                || supervisorId.isBlank()) {
            throw new IllegalArgumentException(
                    "Invalid registration request");
        }

        if (link.getExpiryDate() == null
                || !link.getExpiryDate()
                        .isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException(
                    "Expiry date must be in the future");
        }

        validateRequiredFields(link);

        // Must validate supervisor authorization and the
        // selected PE/contractor/workorder/department/area/
        // license relationships before enabling this endpoint.
        // We will connect these checks to your existing
        // Conkraft services in the controller integration step.

        link.setCreatedBy(supervisorId);

        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);

        String token = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);

        String tokenHash = hashToken(token);

        Long linkId = registrationLinkDao
                .saveRegistrationLink(link, tokenHash);

        link.setLinkId(linkId);

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
}