package com.wfd.dot1.cwfm.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.*;
import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.wfd.dot1.cwfm.pojo.GatePassMain;
import com.wfd.dot1.cwfm.service.SelfRegistrationReviewService.AuthorizedPair;

@Service
public class SelfRegistrationAcceptanceService {
    private static final Path GATEPASS_DOC_ROOT = Paths.get("D:/wfd_cwfm/ep_docs/").toAbsolutePath().normalize();
    private final JdbcTemplate jdbc;
    private final SelfRegistrationReviewService review;
    private final WorkmenService workmenService;

    public SelfRegistrationAcceptanceService(JdbcTemplate jdbc, SelfRegistrationReviewService review,
                                             WorkmenService workmenService) {
        this.jdbc = jdbc;
        this.review = review;
        this.workmenService = workmenService;
    }

    @Transactional(rollbackFor = Exception.class)
    public String accept(long registrationId, String supervisorId, List<AuthorizedPair> pairs) {
        Map<String, Object> row = review.detail(registrationId, pairs);
        // SQL Server UPDLOCK/HOLDLOCK serializes Accept/Reject for this registration.
        List<Map<String,Object>> locked = jdbc.queryForList("""
                SELECT Status, TransactionId FROM dbo.WORKMAN_SELF_REGISTRATION WITH (UPDLOCK, HOLDLOCK)
                WHERE RegistrationId = ?
                """, registrationId);
        if (locked.isEmpty() || !List.of("SUBMITTED", "UNDER_REVIEW").contains(
                String.valueOf(locked.get(0).get("Status"))) || locked.get(0).get("TransactionId") != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Registration already processed.");
        }

        Map<String,String> f = review.fields(row);
        String transactionId = workmenService.generateTransactionId();
        if (transactionId == null || transactionId.isBlank())
            throw new IllegalStateException("Unable to generate transaction ID");

        GatePassMain gp = map(row, f, supervisorId, transactionId);
        Path folder = GATEPASS_DOC_ROOT.resolve(supervisorId).resolve(transactionId).normalize();
        if (!folder.startsWith(GATEPASS_DOC_ROOT)) throw new IllegalStateException("Invalid document path");
        List<Path> copied = new ArrayList<>();
        try {
            // Copy before draft insert so saved document names refer to actual files.
            copyDocuments(registrationId, folder, gp, copied);
            String result = workmenService.draftGatePass(gp);
            if (!transactionId.equals(result))
                throw new IllegalStateException("Gate Pass draft creation failed");

            Integer count = jdbc.queryForObject("""
                    SELECT COUNT(*) FROM dbo.GATEPASSMAIN
                    WHERE TransactionId = ? AND GatePassStatus = 1
                    """, Integer.class, transactionId);
            if (count == null || count != 1)
                throw new IllegalStateException("Gate Pass draft could not be verified");

            int updated = jdbc.update("""
                    UPDATE dbo.WORKMAN_SELF_REGISTRATION
                    SET Status='ACCEPTED', TransactionId=?, ReviewedBy=?, ReviewComments=NULL
                    WHERE RegistrationId=? AND Status IN ('SUBMITTED','UNDER_REVIEW') AND TransactionId IS NULL
                    """, transactionId, supervisorId, registrationId);
            if (updated != 1) throw new IllegalStateException("Registration status update failed");
            return transactionId;
        } catch (Exception ex) {
            for (Path p : copied) {
                try { Files.deleteIfExists(p); } catch (IOException ignored) { }
            }
            try { Files.deleteIfExists(folder); } catch (IOException ignored) { }
            throw ex instanceof RuntimeException ? (RuntimeException) ex
                    : new IllegalStateException("Unable to transfer registration documents", ex);
        }
    }

    private void copyDocuments(long registrationId, Path folder, GatePassMain gp, List<Path> copied)
            throws IOException {
        List<Map<String,Object>> docs = jdbc.queryForList("""
                SELECT DocumentType, StoragePath, StoredFileName
                FROM dbo.WORKMAN_SELF_REG_DOCUMENT WHERE RegistrationId=?
                """, registrationId);
        for (Map<String,Object> doc : docs) {
            String type = String.valueOf(doc.get("DocumentType"));
            String filename = String.valueOf(doc.get("StoredFileName"));
            String extension = filename.toLowerCase(Locale.ROOT);
            int dot = extension.lastIndexOf('.');
            extension = dot >= 0 ? extension.substring(dot) : "";
            if (!List.of(".pdf", ".jpg", ".jpeg", ".png").contains(extension))
                throw new IllegalStateException("Unsupported document extension");
            String targetName = switch (type) {
                case "imageFile" -> "profile" + extension;
                case "aadharFile" -> "aadhar" + extension;
                case "policeFile" -> "police" + extension;
                case "appointmentFile" -> "appointment" + extension;
                default -> throw new IllegalStateException("Unexpected document type: " + type);
            };
            Path source = Paths.get(String.valueOf(doc.get("StoragePath"))).toAbsolutePath().normalize();
            // Do not follow symlinks to arbitrary files.
            if (!Files.isRegularFile(source, LinkOption.NOFOLLOW_LINKS))
                throw new IOException("Registration document missing: " + type);
            Files.createDirectories(folder);
            Path dest = folder.resolve(targetName);
            if (Files.exists(dest)) throw new IOException("Draft document already exists: " + targetName);
            Files.copy(source, dest);
            copied.add(dest);
            switch (type) {
                case "imageFile" -> gp.setPhotoName(targetName);
                case "aadharFile" -> gp.setAadharDocName(targetName);
                case "policeFile" -> gp.setPoliceVerificationDocName(targetName);
                case "appointmentFile" -> gp.setAppointmentDocName(targetName);
            }
        }
    }

    private static GatePassMain map(Map<String,Object> r, Map<String,String> f,
                                    String userId, String transactionId) {
        GatePassMain gp = new GatePassMain();
        gp.setTransactionId(transactionId);
        gp.setUserId(userId);
        gp.setCreatedBy(userId);
        gp.setOnboardingType("regular");
        gp.setUnitId(str(r,"UnitId"));
        gp.setPrincipalEmployer(str(r,"UnitId"));
        gp.setContractor(str(r,"ContractorId"));
        gp.setWorkorder(str(r,"WorkorderId"));
        gp.setDepartment(str(r,"DepartmentId"));
        gp.setSubdepartment(str(r,"AreaId"));
        gp.setEic(str(r,"EicId"));
        gp.setWcEsicNo(str(r,"WcEsicNo"));
        gp.setLlNo(str(r,"LlNo"));
        gp.setAadhaarNumber(val(f,"aadharNumber"));
        gp.setFirstName(val(f,"firstName"));
        gp.setLastName(val(f,"lastName"));
        gp.setRelationName(val(f,"relationName"));
        gp.setDateOfBirth(val(f,"dateOfBirth"));
        gp.setGender(val(f,"gender"));
        gp.setIdMark(val(f,"idMark"));
        gp.setMobileNumber(val(f,"mobileNumber"));
        gp.setMaritalStatus(val(f,"maritalStatus"));
        gp.setDisability(val(f,"disability"));
        gp.setWorkmenType(val(f,"workmenType"));
        gp.setAddress(val(f,"address"));
        gp.setHazardousArea(val(f,"hazardousArea"));
        gp.setAccessArea(val(f,"accessArea"));
        gp.setPfNumber(val(f,"pfNumber"));
        gp.setUanNumber(val(f,"uanNumber"));
        gp.setDoj(val(f,"doj"));
        gp.setHealthCheckDate(val(f,"healthCheckDate"));
        gp.setEsicNumber(val(f,"esicNumber"));
        gp.setPfApplicable(val(f,"pfApplicable"));
        gp.setBloodGroup(val(f,"bloodGroupId"));
        gp.setAccommodation(val(f,"accommodation"));
        gp.setAcademic(val(f,"academicId"));
        gp.setTechnical(val(f,"technical"));
        gp.setIfscCode(val(f,"ifscCode"));
        gp.setAccountNumber(val(f,"accountNumber"));
        gp.setEmergencyName(val(f,"emergencyName"));
        gp.setEmergencyNumber(val(f,"emergencyNumber"));
        gp.setWageCategory(val(f,"wageCategoryId"));
        gp.setBonusPayout(val(f,"bonusPayoutId"));
        gp.setZone(val(f,"zoneId"));
        gp.setPfCap(val(f,"pfCap"));
        gp.setBasic(money(f,"basic"));
        gp.setDa(money(f,"da"));
        gp.setHra(money(f,"hra"));
        gp.setWashingAllowance(money(f,"washingAllowance"));
        gp.setOtherAllowance(money(f,"otherAllowance"));
        gp.setUniformAllowance(money(f,"uniformAllowance"));
        gp.setPoliceVerificationDate(val(f,"policeVerificationDate"));
        gp.setComments(val(f,"comments"));
        // These are not collected by the public form; supervisor can complete them in Draft.
        gp.setIsmw(" ");
        return gp;
    }

    private static String str(Map<String,Object> m, String key) {
        Object value = m.get(key);
        return value == null ? " " : String.valueOf(value);
    }
    private static String val(Map<String,String> m, String key) {
        String value = m.get(key);
        return value == null || value.isBlank() ? " " : value.trim();
    }
    private static BigDecimal money(Map<String,String> m, String key) {
        String value = m.get(key);
        try { return value == null || value.isBlank() ? BigDecimal.ZERO : new BigDecimal(value.trim()); }
        catch (NumberFormatException ex) { throw new IllegalArgumentException("Invalid amount: " + key); }
    }
}
