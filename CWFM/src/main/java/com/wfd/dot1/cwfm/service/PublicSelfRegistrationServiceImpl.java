package com.wfd.dot1.cwfm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wfd.dot1.cwfm.pojo.RegistrationLink;
import com.wfd.dot1.cwfm.service.PublicSelfRegistrationService;
import com.wfd.dot1.cwfm.service.RegistrationLinkService;
import com.wfd.dot1.cwfm.dao.PublicSelfRegistrationDao;
import com.wfd.dot1.cwfm.util.ApplicationConfig;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

@Service
public class PublicSelfRegistrationServiceImpl implements PublicSelfRegistrationService {

    private final RegistrationLinkService linkService;
    private final PublicSelfRegistrationDao registrationDao;

    private final ObjectMapper mapper = new ObjectMapper();

    public PublicSelfRegistrationServiceImpl(
            RegistrationLinkService linkService,
            PublicSelfRegistrationDao registrationDao) {

        this.linkService = linkService;
        this.registrationDao = registrationDao;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long submit(String token,
                       Map<String, String> posted,
                       Map<String, MultipartFile> uploads) throws IOException {

        // Validate registration token
        RegistrationLink link = linkService.validateAndGetLink(token);

        // Aadhaar validation
        String aadhaar = required(posted, "aadharNumber", 12);

        if (!aadhaar.matches("[0-9]{12}")) {
            throw new IllegalArgumentException(
                    "Enter a valid 12-digit Aadhaar number");
        }

        // Basic details validation
        String first = required(posted, "firstName", 100);
        String last = required(posted, "lastName", 100);
        String mobile = required(posted, "mobileNumber", 10);

        if (!mobile.matches("[6-9][0-9]{9}")) {
            throw new IllegalArgumentException("Enter a valid mobile number");
        }

        required(posted, "address", 500);
        required(posted, "gender", 30);
        required(posted, "doj", 10);
        required(posted, "bloodGroupId", 50);
        required(posted, "accommodation", 20);
        required(posted, "wageCategoryId", 50);
        required(posted, "bonusPayoutId", 50);
        required(posted, "zoneId", 50);
        required(posted, "hazardousArea", 20);

        if (!"true".equals(posted.get("acceptCheck"))) {
            throw new IllegalArgumentException("Accept the declaration");
        }

        // Date of birth validation
        LocalDate dob = parseDate(
                required(posted, "dateOfBirth", 10),
                "Date of Birth");

        LocalDate now = LocalDate.now();

        if (dob.isAfter(now.minusYears(18))
                || dob.isBefore(now.minusYears(70))) {

            throw new IllegalArgumentException(
                    "Workman age must be between 18 and 70 years");
        }

        // Date of joining validation
        LocalDate doj = parseDate(posted.get("doj"), "Date of Joining");

        if (doj.isAfter(now.plusDays(15))) {
            throw new IllegalArgumentException(
                    "Date of Joining cannot exceed today + 15 days");
        }

        // Health check date validation
        String health = posted.get("healthCheckDate");

        if (health != null && !health.isBlank()) {

            LocalDate h = parseDate(health, "Health Check Date");

            if (h.isAfter(now) || h.isBefore(now.minusMonths(6))) {
                throw new IllegalArgumentException(
                        "Health Check Date must be within the last 6 months");
            }
        }

        // Only allowed form fields are persisted
        Set<String> allowed = Set.of(
                "aadharNumber", "firstName", "lastName", "relationName",
                "dateOfBirth", "gender", "idMark", "mobileNumber",
                "maritalStatus", "disability", "workmenType", "address",
                "hazardousArea", "accessArea", "pfNumber", "uanNumber",
                "doj", "healthCheckDate", "esicNumber", "pfApplicable",
                "bloodGroupId", "accommodation", "academicId", "technical",
                "ifscCode", "accountNumber", "emergencyName",
                "emergencyNumber", "wageCategoryId", "bonusPayoutId",
                "zoneId", "basic", "da", "hra", "washingAllowance",
                "otherAllowance", "uniformAllowance", "pfCap",
                "policeVerificationDate", "comments", "acceptCheck"
        );

        Map<String, String> data = new LinkedHashMap<>();

        for (String key : allowed) {

            String value = posted.get(key);

            if (value != null) {

                if (value.length() > 1000) {
                    throw new IllegalArgumentException("Value too long: " + key);
                }

                data.put(key, value);
            }
        }

        // Convert registration details into JSON
        String json = mapper.writeValueAsString(data);

        // Validate uploaded documents
        validateUpload(uploads.get("imageFile"), true, "Workman Photo");
        validateUpload(uploads.get("aadharFile"), true, "Aadhaar Document");
        validateUpload(uploads.get("appointmentFile"), false, "Appointment Letter");
        validateUpload(uploads.get("policeFile"), false, "Police Verification Document");

        // Insert registration through DAO
        long registrationId;

        try {

            registrationId = registrationDao.insertRegistration(
                    link.getLinkId(),
                    aadhaar,
                    first,
                    last,
                    mobile,
                    json
            );

        } catch (DataIntegrityViolationException ex) {

            throw new IllegalArgumentException(
                    "A registration already exists for this Aadhaar number");
        }

        // Validate generated registration ID
        if (registrationId <= 0) {
            throw new IllegalStateException(
                    "Registration ID was not generated");
        }

        // Get upload directory from configuration
        String configured = ApplicationConfig.getProperty("registration.upload.dir");

        if (configured == null || configured.isBlank()) {
            throw new IllegalStateException(
                    "Configure registration.upload.dir before accepting documents");
        }

        Path root = Paths.get(configured).toAbsolutePath().normalize();

        Path folder = root.resolve(
                Long.toString(registrationId)).normalize();

        if (!folder.startsWith(root)) {
            throw new IllegalStateException("Invalid upload location");
        }

        List<Path> created = new ArrayList<>();

        try {

            Files.createDirectories(folder);

            for (String key : List.of(
                    "imageFile",
                    "aadharFile",
                    "appointmentFile",
                    "policeFile")) {

                MultipartFile file = uploads.get(key);

                if (file == null || file.isEmpty()) {
                    continue;
                }

                String ext = safeExtension(file.getOriginalFilename());

                String stored = UUID.randomUUID().toString() + ext;

                Path destination = folder.resolve(stored);

                try (var in = file.getInputStream()) {

                    Files.copy(
                            in,
                            destination,
                            StandardCopyOption.REPLACE_EXISTING);
                }

                created.add(destination);

                // Insert document details through DAO
                registrationDao.insertRegistrationDocument(
                        registrationId,
                        key,
                        stored,
                        destination.toString()
                );
            }

        } catch (Exception ex) {

            // Delete files created during failed document processing
            for (Path path : created) {

                try {
                    Files.deleteIfExists(path);
                } catch (IOException ignored) {
                }
            }

            if (ex instanceof IOException) {
                throw (IOException) ex;
            }

            throw new IllegalStateException(
                    "Unable to save registration documents", ex);
        }

        return registrationId;
    }

    // Required field validation
    private static String required(
            Map<String, String> values,
            String name,
            int max) {

        String v = values.get(name);

        if (v == null || v.isBlank() || v.length() > max) {
            throw new IllegalArgumentException(
                    "Invalid or missing: " + name);
        }

        return v.trim();
    }

    // Date validation
    private static LocalDate parseDate(String value, String label) {

        try {
            return LocalDate.parse(value);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Invalid " + label);
        }
    }

    // File extension validation
    private static String safeExtension(String name) {

        if (name == null) {
            throw new IllegalArgumentException("Invalid file name");
        }

        String n = name.toLowerCase(Locale.ROOT);

        if (n.endsWith(".jpg")) return ".jpg";
        if (n.endsWith(".jpeg")) return ".jpeg";
        if (n.endsWith(".png")) return ".png";
        if (n.endsWith(".pdf")) return ".pdf";

        throw new IllegalArgumentException(
                "Only JPG, PNG and PDF files are allowed");
    }

    // Upload validation
    private static void validateUpload(
            MultipartFile file,
            boolean required,
            String label) throws IOException {

        if (file == null || file.isEmpty()) {

            if (required) {
                throw new IllegalArgumentException(label + " is required");
            }

            return;
        }

        if (file.getSize() > 5L * 1024 * 1024) {
            throw new IllegalArgumentException(label + " exceeds 5 MB");
        }

        String ext = safeExtension(file.getOriginalFilename());

        byte[] head = new byte[8];

        int n;

        try (var in = file.getInputStream()) {
            n = in.read(head);
        }

        boolean pdf = n >= 5
                && head[0] == '%'
                && head[1] == 'P'
                && head[2] == 'D'
                && head[3] == 'F'
                && head[4] == '-';

        boolean png = n >= 8
                && (head[0] & 0xff) == 137
                && head[1] == 'P'
                && head[2] == 'N'
                && head[3] == 'G';

        boolean jpg = n >= 3
                && (head[0] & 0xff) == 0xff
                && (head[1] & 0xff) == 0xd8
                && (head[2] & 0xff) == 0xff;

        if (!(ext.equals(".pdf") && pdf
                || ext.equals(".png") && png
                || (ext.equals(".jpg") || ext.equals(".jpeg")) && jpg)) {

            throw new IllegalArgumentException(
                    label + " has an invalid file format");
        }

        if ("imageFile".equals(file.getName()) && pdf) {
            throw new IllegalArgumentException(
                    "Workman Photo must be JPG or PNG");
        }
    }
}