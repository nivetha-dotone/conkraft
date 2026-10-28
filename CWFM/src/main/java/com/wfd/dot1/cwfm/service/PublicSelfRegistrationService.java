package com.wfd.dot1.cwfm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wfd.dot1.cwfm.pojo.RegistrationLink;
import com.wfd.dot1.cwfm.util.ApplicationConfig;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.*;

@Service
public class PublicSelfRegistrationService {
    private final RegistrationLinkService linkService;
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper mapper = new ObjectMapper();
    private final SecureRandom random = new SecureRandom();

    public PublicSelfRegistrationService(RegistrationLinkService linkService, JdbcTemplate jdbcTemplate) {
        this.linkService = linkService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(rollbackFor = Exception.class)
    public long submit(String token, Map<String, String> posted,
                       Map<String, MultipartFile> uploads) throws IOException {
        // The browser's hidden IDs are never trusted; the validated token supplies the LinkId.
        RegistrationLink link = linkService.validateAndGetLink(token);
        String aadhaar = required(posted, "aadharNumber", 12);
        if (!aadhaar.matches("[0-9]{12}")) throw new IllegalArgumentException("Enter a valid 12-digit Aadhaar number");
        String first = required(posted, "firstName", 100);
        String last = required(posted, "lastName", 100);
        String mobile = required(posted, "mobileNumber", 10);
        if (!mobile.matches("[6-9][0-9]{9}")) throw new IllegalArgumentException("Enter a valid mobile number");
        required(posted, "address", 500);
        required(posted, "gender", 30);
        required(posted, "doj", 10);
        required(posted, "bloodGroupId", 50);
        required(posted, "accommodation", 20);
        required(posted, "wageCategoryId", 50);
        required(posted, "bonusPayoutId", 50);
        required(posted, "zoneId", 50);
        required(posted, "hazardousArea", 20);
        if (!"true".equals(posted.get("acceptCheck"))) throw new IllegalArgumentException("Accept the declaration");
        LocalDate dob = parseDate(required(posted, "dateOfBirth", 10), "Date of Birth");
        LocalDate now = LocalDate.now();
        if (dob.isAfter(now.minusYears(18)) || dob.isBefore(now.minusYears(70)))
            throw new IllegalArgumentException("Workman age must be between 18 and 70 years");
        LocalDate doj = parseDate(posted.get("doj"), "Date of Joining");
        if (doj.isAfter(now.plusDays(15))) throw new IllegalArgumentException("Date of Joining cannot exceed today + 15 days");
        String health = posted.get("healthCheckDate");
        if (health != null && !health.isBlank()) {
            LocalDate h = parseDate(health, "Health Check Date");
            if (h.isAfter(now) || h.isBefore(now.minusMonths(6)))
                throw new IllegalArgumentException("Health Check Date must be within the last 6 months");
        }
        // Only known form keys are persisted. No browser-supplied employment mappings.
        Set<String> allowed = Set.of("aadharNumber", "firstName", "lastName", "relationName", "dateOfBirth",
                "gender", "idMark", "mobileNumber", "maritalStatus", "disability", "workmenType", "address",
                "hazardousArea", "accessArea", "pfNumber", "uanNumber", "doj", "healthCheckDate", "esicNumber",
                "pfApplicable", "bloodGroupId", "accommodation", "academicId", "technical", "ifscCode",
                "accountNumber", "emergencyName", "emergencyNumber", "wageCategoryId", "bonusPayoutId",
                "zoneId", "basic", "da", "hra", "washingAllowance", "otherAllowance", "uniformAllowance",
                "pfCap", "policeVerificationDate", "comments", "acceptCheck");
        Map<String, String> data = new LinkedHashMap<>();
        for (String key : allowed) {
            String value = posted.get(key);
            if (value != null) {
                if (value.length() > 1000) throw new IllegalArgumentException("Value too long: " + key);
                data.put(key, value);
            }
        }
        // Do not include Aadhaar in application logs or error messages.
        String json = mapper.writeValueAsString(data);
        validateUpload(uploads.get("imageFile"), true, "Workman Photo");
        validateUpload(uploads.get("aadharFile"), true, "Aadhaar Document");
        validateUpload(uploads.get("appointmentFile"), false, "Appointment Letter");
        validateUpload(uploads.get("policeFile"), false, "Police Verification Document");

        String insert = "INSERT INTO dbo.WORKMAN_SELF_REGISTRATION " +
                "(LinkId, AadharNumber, FirstName, LastName, MobileNumber, RegistrationData, Status) " +
                "VALUES (?, ?, ?, ?, ?, ?, 'SUBMITTED')";
        KeyHolder holder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, link.getLinkId()); ps.setString(2, aadhaar);
                ps.setString(3, first); ps.setString(4, last);
                ps.setString(5, mobile); ps.setString(6, json);
                return ps;
            }, holder);
        } catch (DataIntegrityViolationException ex) {
            // Unique Aadhaar constraint must also exist in the database.
            throw new IllegalArgumentException("A registration already exists for this Aadhaar number");
        }
        if (holder.getKey() == null) throw new IllegalStateException("Registration ID was not generated");
        long registrationId = holder.getKey().longValue();

        String configured = ApplicationConfig.getProperty("registration.upload.dir");
        if (configured == null || configured.isBlank())
            throw new IllegalStateException("Configure registration.upload.dir before accepting documents");
        Path root = Paths.get(configured).toAbsolutePath().normalize();
        Path folder = root.resolve(Long.toString(registrationId)).normalize();
        if (!folder.startsWith(root)) throw new IllegalStateException("Invalid upload location");
        List<Path> created = new ArrayList<>();
        try {
            Files.createDirectories(folder);
            for (String key : List.of("imageFile", "aadharFile", "appointmentFile", "policeFile")) {
                MultipartFile file = uploads.get(key);
                if (file == null || file.isEmpty()) continue;
                String ext = safeExtension(file.getOriginalFilename());
                String stored = UUID.randomUUID().toString() + ext;
                Path destination = folder.resolve(stored);
                try (var in = file.getInputStream()) {
                    Files.copy(in, destination, StandardCopyOption.REPLACE_EXISTING);
                }
                created.add(destination);
                jdbcTemplate.update("INSERT INTO dbo.WORKMAN_SELF_REG_DOCUMENT " +
                                "(RegistrationId, DocumentType, StoredFileName, StoragePath) VALUES (?, ?, ?, ?)",
                        registrationId, key, stored, destination.toString());
            }
        } catch (Exception ex) {
            for (Path path : created) { try { Files.deleteIfExists(path); } catch (IOException ignored) {} }
            if (ex instanceof IOException) throw (IOException) ex;
            throw new IllegalStateException("Unable to save registration documents", ex);
        }
        return registrationId;
    }

    private static String required(Map<String, String> values, String name, int max) {
        String v = values.get(name);
        if (v == null || v.isBlank() || v.length() > max) throw new IllegalArgumentException("Invalid or missing: " + name);
        return v.trim();
    }
    private static LocalDate parseDate(String value, String label) {
        try { return LocalDate.parse(value); }
        catch (Exception ex) { throw new IllegalArgumentException("Invalid " + label); }
    }
    private static String safeExtension(String name) {
        if (name == null) throw new IllegalArgumentException("Invalid file name");
        String n = name.toLowerCase(Locale.ROOT);
        if (n.endsWith(".jpg")) return ".jpg";
        if (n.endsWith(".jpeg")) return ".jpeg";
        if (n.endsWith(".png")) return ".png";
        if (n.endsWith(".pdf")) return ".pdf";
        throw new IllegalArgumentException("Only JPG, PNG and PDF files are allowed");
    }
    private static void validateUpload(MultipartFile file, boolean required, String label) throws IOException {
        if (file == null || file.isEmpty()) {
            if (required) throw new IllegalArgumentException(label + " is required");
            return;
        }
        if (file.getSize() > 5L * 1024 * 1024) throw new IllegalArgumentException(label + " exceeds 5 MB");
        String ext = safeExtension(file.getOriginalFilename());
        byte[] head = new byte[8];
        int n;
        try (var in = file.getInputStream()) { n = in.read(head); }
        boolean pdf = n >= 5 && head[0] == '%' && head[1] == 'P' && head[2] == 'D' && head[3] == 'F' && head[4] == '-';
        boolean png = n >= 8 && (head[0] & 0xff) == 137 && head[1] == 'P' && head[2] == 'N' && head[3] == 'G';
        boolean jpg = n >= 3 && (head[0] & 0xff) == 0xff && (head[1] & 0xff) == 0xd8 && (head[2] & 0xff) == 0xff;
        if (!(ext.equals(".pdf") && pdf || ext.equals(".png") && png ||
                (ext.equals(".jpg") || ext.equals(".jpeg")) && jpg))
            throw new IllegalArgumentException(label + " has an invalid file format");
        if ("imageFile".equals(file.getName()) && pdf)
            throw new IllegalArgumentException("Workman Photo must be JPG or PNG");
    }
}
