package com.wfd.dot1.cwfm.service;



import com.wfd.dot1.cwfm.dto.*;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
public class FaceRegistrationRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EmployeeMapper employeeMapper;


    private static final Logger log = LoggerFactory.getLogger(FaceRegistrationRepository.class.getName());


    public String getFACE_REGISTRAION() {
        return QueryFileWatcher.getQuery("GET_FACE_REGISTRAION");
    }

    public String GETUNITBYGATEPASSID() {
        return QueryFileWatcher.getQuery("GET_UNIT_BY_GATEPASSID");
    }

    public String GET_SITE_LOCATION() {
        return QueryFileWatcher.getQuery("GET_SITE_LOCATION");
    }
    public String getINSERTFACERE() {
        return QueryFileWatcher.getQuery("INSERTFACERE");
    }
    public String getFACEEMPLOYEEBYUSERID() {
        return QueryFileWatcher.getQuery("GET_FACEEMPLOYEEBYUSERID");
    }

    public String getGETPYPATH() {
        return QueryFileWatcher.getQuery("GETPYPATH");
    }
    public String getINSERTEMPPUNCH() {
        return QueryFileWatcher.getQuery("INSERTEMPPUNCH");
    }

    public FaceLogFetchDto getFaceLogFetchDto(String personNum) {

        FaceLogFetchDto dto = new FaceLogFetchDto();
        dto.setPersonNum(personNum);
        dto.setUsername(personNum);
        dto.setLocation("Trials");
        dto.setFaceScore(0.0);
        dto.setDeviceId("FACE_TERMINAL");
        dto.setIpAddress("SYSTEM");
        dto.setPunchDtm(java.time.LocalDateTime.now().toString());
        return dto;

    }

    public String getROOT_DIRECTORY() {
        return QueryFileWatcher.getQuery("GETROOT-USERIMAGE");
    }

    public String GETROOT_DIRECTORYUserLogin() {
        return QueryFileWatcher.getQuery("GETROOT_DIRECTORYUserLogin");
    }  public String getROOT_DIRECTORYFaceTemp() {
        return QueryFileWatcher.getQuery("GETROOT_DIRECTORYFaceTemp");
    }

    public String getROOT_DIRECTORYPythonExec() {
        return QueryFileWatcher.getQuery("ROOT_DIRECTORYPythonExec");
    }

    public String getROOT_DIRECTORYPythonScript() {
        return QueryFileWatcher.getQuery("ROOT_DIRECTORYPythonScript");
    }


    private static final String ROOT_DIRECTORYUserLogin = "D:/wfd_cwfm/USERIMAGE_LOGIN/";
    private static final String ROOT_DIRECTORYFaceTemp = "D:/wfd_cwfm/FACE_TEMP/";

    private static final String ROOT_DIRECTORYPythonExec = "D:/Python312/python.exe";
    private static final String ROOT_DIRECTORYPythonScript = "D:/Python312/facerec.py";




    public boolean isUserFaceRegistered(String userId) {

        String sql = getFACE_REGISTRAION();

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId);

//        return count != null && count > 0;
        return false;
    }


    public String registerFace(FaceRegistrationDTO dto, MultipartFile file) {

        try {

            if (file == null || file.isEmpty()) {
                return "NO_FILE";
            }

            if (isUserFaceRegistered(dto.getUserId())) {
                return "USER_ALREADY_REGISTERED";
            }

            /* ---------- STEP 1 : FETCH UNITID ---------- */

            String unitQuery = GETUNITBYGATEPASSID();

            List<String> units = jdbcTemplate.query(
                    unitQuery,
                    new Object[]{dto.getUserId()},
                    (rs, rowNum) -> rs.getString("UNITID")
            );

            if (units.isEmpty()) {
                return "NOT_FOUND_UNITID";
            }

            String unitId = units.get(0);
            dto.setUnitID(unitId);

            /* ---------- STEP 2 : FETCH SITE LOCATION ---------- */

            String siteQuery = GET_SITE_LOCATION();

            List<Map<String, Object>> siteList = jdbcTemplate.queryForList(siteQuery, unitId);

            if (siteList.isEmpty()) {
                return "THIS_UNIT_IS_NOT_MAPPED_WITH_LOCATION";
            }

            Map<String, Object> site = siteList.get(0);

            double siteLat = ((Number) site.get("LATITUDE")).doubleValue();
            double siteLon = ((Number) site.get("LONGITUDE")).doubleValue();
            int radius = ((Number) site.get("ALLOWED_RADIUS_METERS")).intValue();
            /* ---------- STEP 3 : VALIDATE MOBILE LOCATION ---------- */

            if (dto.getLatitude() == null || dto.getLongitude() == null) {
                return "LOCATION_NOT_AVAILABLE";
            }

            double distance = calculateDistance(
                    dto.getLatitude(),
                    dto.getLongitude(),
                    siteLat,
                    siteLon
            );

            if (distance > radius) {
                return "OUTSIDE_GEOFENCE";
            }

            /* ---------- STEP 4 : SAVE FACE ---------- */

            String fileName = generateFileName();

            String safeFileName = (fileName + "_" + file.getOriginalFilename())
                    .replaceAll("[^a-zA-Z0-9._-]", "_");

            Path folder = Paths.get(getROOT_DIRECTORY());
            Files.createDirectories(folder);

            Path fullPath = folder.resolve(safeFileName);

            saveFile(file, fullPath.toString());

            String query = getINSERTFACERE();

            int rows = jdbcTemplate.update(
                    query,
                    dto.getUserId(),
                    dto.getUserId(),
                    safeFileName,
                    fullPath.toString()
            );

            return rows > 0 ? "SUCCESS" : "DB_INSERT_FAILED";

        } catch (Exception e) {

            e.printStackTrace();
            return "ERROR";

        }
    }
    private void saveFile(MultipartFile file, String path) throws IOException {
        byte[] bytes = file.getBytes();
        Path filePath = Paths.get(path);
        Files.write(filePath, bytes);
    }
    private String generateFileName() {

        String timestamp = DateTimeFormatter
                .ofPattern("yyyyMMdd_HHmmss")
                .format(LocalDateTime.now());

        String random = UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 6)
                .toUpperCase();

        return "FL_" + timestamp + "_" + random + ".jpg";
    }


    public FaceLoginResponse faceLogin(
            String userId,
            Double latitude,
            Double longitude,
            MultipartFile imageFile
    ){
        Path tempLoginImage = null;

        try {

            if (imageFile == null || imageFile.isEmpty()) {
                return new FaceLoginResponse(false, "No image received");
            }

            /* ---------- STEP 1 : FETCH UNITID ---------- */

            String unitQuery = GETUNITBYGATEPASSID();

            List<String> units = jdbcTemplate.query(
                    unitQuery,
                    new Object[]{userId},
                    (rs, rowNum) -> rs.getString("UNITID")
            );

            if (units.isEmpty()) {
                return new FaceLoginResponse(false, "UNITID_NOT_FOUND FOR THAT GATEPASS");
            }

            String unitId = units.get(0);


            /* ---------- STEP 2 : FETCH SITE LOCATION ---------- */

            String siteQuery = GET_SITE_LOCATION();

            List<Map<String, Object>> siteList = jdbcTemplate.queryForList(siteQuery, unitId);

            if (siteList.isEmpty()) {
                return new FaceLoginResponse(false, "THIS_UNIT_IS_NOT_MAPPED_WITH_LOCATION");
            }

            Map<String, Object> site = siteList.get(0);

            double siteLat = ((Number) site.get("LATITUDE")).doubleValue();
            double siteLon = ((Number) site.get("LONGITUDE")).doubleValue();
            int radius = ((Number) site.get("ALLOWED_RADIUS_METERS")).intValue();

            /* ---------- STEP 3 : VALIDATE LOCATION ---------- */

            if (latitude == null || longitude == null) {
                return new FaceLoginResponse(false, "LOCATION_NOT_AVAILABLE FROM WEB PAGE");
            }

            double distance = calculateDistance(latitude, longitude, siteLat, siteLon);

            if (distance > radius) {
                return new FaceLoginResponse(false, "OUTSIDE_GEOFENCE");
            }

            // ✅ Ensure FACE_TEMP directory exists
            Files.createDirectories(Paths.get(getROOT_DIRECTORYFaceTemp()));

            // ✅ Create temp image inside FACE_TEMP
            String tempFileName = "login_" + System.currentTimeMillis() + ".jpg";
            tempLoginImage = Paths.get(getROOT_DIRECTORYFaceTemp(), tempFileName);

            // ✅ Save uploaded image
            imageFile.transferTo(tempLoginImage.toFile());

            // 🔍 Fetch registered face images
            List<Map<String, Object>> users = jdbcTemplate.queryForList(
                    getFACEEMPLOYEEBYUSERID(),
                    userId
            );

            if (users.isEmpty()) {
                return new FaceLoginResponse(false, "No face registered for this user");
            }

            boolean atLeastOneImageChecked = false;

            for (Map<String, Object> row : users) {

                String storedImagePath = row.get("IMAGE_PATH").toString();

                if (!Files.exists(Paths.get(storedImagePath))) {
                    System.out.println("Missing DB image file: " + storedImagePath);
                    continue;
                }

                atLeastOneImageChecked = true;

                System.out.println("Comparing with DB image: " + storedImagePath);

                boolean match = runPythonFaceCompare(
                        tempLoginImage.toString(),
                        storedImagePath
                );

                if (match) {

                    String newFileName = generateFileName();
                    Path finalPath = Paths.get(GETROOT_DIRECTORYUserLogin(), newFileName);

                    Files.copy(
                            tempLoginImage,
                            finalPath,
                            StandardCopyOption.REPLACE_EXISTING
                    );

                    FaceLogFetchDto dto = getFaceLogFetchDto(userId);
                    if (dto == null) {
                        return new FaceLoginResponse(false,
                                "User data missing for punch logging");
                    }

                    dto.setFileName(newFileName);
                    dto.setImagePath(finalPath.toString());

                    boolean punchSaved = savePunchLog(dto);

                    if (!punchSaved) {
                        return new FaceLoginResponse(false,
                                "Face verified but punch entry failed. Contact admin.");
                    }
                    String s= " ";
                    if(punchSaved){
                        s = employeeMapper.punchMatched(dto);
                    }

                    return buildLoginResponse(
                            row,
                            "Face verification & punch inserted successfully  "+ s
                    );
                }
            }

            if (!atLeastOneImageChecked) {
                return new FaceLoginResponse(false,
                        "All registered face images are missing. Contact admin.");
            }

            return new FaceLoginResponse(false, "Face authentication failed");

        } catch (Exception e) {
            e.printStackTrace();
            return new FaceLoginResponse(false, "Server error during face login");

        } finally {
            try {
                if (tempLoginImage != null) {
                    Files.deleteIfExists(tempLoginImage);
                }
            } catch (Exception ignored) {
            }
        }
    }



    private FaceLoginResponse buildLoginResponse(Map<String, Object> row, String message) {

        FaceLoginResponse res = new FaceLoginResponse();

        res.setSuccess(true);
        res.setMessage(message);
        res.setUserId(row.get("USER_ID").toString());
        res.setUsername(row.get("USERNAME").toString());

        return res;
    }


    private boolean runPythonFaceCompare(String loginImage, String dbImage) {
        try {

            ProcessBuilder pb = new ProcessBuilder(
                    getROOT_DIRECTORYPythonExec(),
                    "-OO",                         // 🚀 disable debug
                    getROOT_DIRECTORYPythonScript(),
                    loginImage,
                    dbImage
            );
            String getpypath = getGETPYPATH();
            pb.directory(new File(getpypath));
            pb.redirectErrorStream(true);

            Process process = pb.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );

            StringBuilder output = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println("PYTHON: " + line); // debug log
                output.append(line);
            }

            boolean finished = process.waitFor(10, java.util.concurrent.TimeUnit.SECONDS);
            if (!finished) {
                process.destroyForcibly();
                System.out.println("Python process timeout");
                return false;
            }

            System.out.println("PYTHON RESULT = " + output);

            return output.toString().contains("True");

        } catch (Exception e) {
            System.out.println("Python execution error: " + e.getMessage());
            return false;
        }
    }

    public boolean savePunchLog(FaceLogFetchDto dto) {

        try {

            String sql = getINSERTEMPPUNCH();

            int rows = jdbcTemplate.update(sql,
                    dto.getPersonNum(),
                    dto.getUsername(),
                    dto.getPunchDtm(),
                    dto.getPersonNum(),
                    dto.getPunchDtm(),
                    dto.getImagePath(),
                    dto.getFileName(),
                    dto.getFaceScore(),
                    dto.getDeviceId(),
                    dto.getLocation(),
                    dto.getIpAddress()
            );
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public String getWorkmenByContrId() {
        return QueryFileWatcher.getQuery("GET_ALLWORKMEN_BYCON");
    }


    public List<workMenFaceDropDto> getListWorkmen(Integer userId) {
        try {
            log.info("Fetching Requestor list");
            String fetchListRequestor =  getWorkmenByContrId();
            if (userId == null) {
                throw new RuntimeException("User account is missing (null). Cannot fetch requestors.");
            }
            SqlRowSet rs = jdbcTemplate.queryForRowSet(fetchListRequestor,userId);
            List<workMenFaceDropDto> peList = new ArrayList<>();

            while (rs.next()) {
                workMenFaceDropDto dto = new workMenFaceDropDto();

                dto.setGatePassId(rs.getString("GatePassId"));
                dto.setFirstName(rs.getString("FirstName"));
                dto.setLastName(rs.getString("LastName"));
                dto.setEMPLOYEEID(rs.getString("EMPLOYEEID"));
                peList.add(dto);
            }
            log.info("Fetched {} requestors", peList.size());
            return peList;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching requestor list", e);
        }

    }




















//    public FaceLoginResponse saveImageAutoService(
//            String userId, MultipartFile imageFile) {
//
//        try {
//
//            String faceLoginFlag = faceLogin();
//            if (!"Y".equalsIgnoreCase(faceLoginFlag)) {
//                return new FaceLoginResponse(false, "Face login not enabled");
//            }
//
//
//            String pathImage = userImageLogin(userId, imageFile);
//            if (pathImage.isEmpty()) {
//                return new FaceLoginResponse(false, "Failed to save captured image");
//            }
//
//            Map<String, Object> userRow   = callLoginSPAndRunPythonDTO(userId, imageFile);
//
//            String s = callLoginSPAndRunPython(request.getUserId(), pathImage);
//
//            if (userRow  == null) {
//                return new FaceLoginResponse(false, "Face authentication failed");
//            }
//
//            // 4️⃣ Decrypt password
//            String encPwd = userRow.get("PASSWORD").toString();
//            String decPwd = decryptTripleDES(encPwd);
//
//            // 🎯 Build response DTO
//            FaceLoginResponse response = new FaceLoginResponse();
//            response.setSuccess(true);
//            response.setMessage("Face authentication success");
//            response.setUserId(userRow.get("USERID").toString());
//            response.setUsername(userRow.get("USERNAME").toString());
//            response.setRetainer(userRow.get("RETAINER_CODE").toString());
//            response.setPubCode(userRow.get("PUB_CODE").toString());
//            response.setDecryptedPassword(decPwd);
//
//            return response;
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new FaceLoginResponse(false, "Server error");
//        }
//    }
//




//    private String decryptTripleDES(String encPwd) throws Exception {
//
//        byte[] toDecrypt = Base64.getDecoder().decode(encPwd);
//
//        MessageDigest md5 = MessageDigest.getInstance("MD5");
//        byte[] keyBytes = md5.digest(passphrase.getBytes("UTF-8"));
//
//        // 🔥 Expand 16-byte MD5 key to 24 bytes for DESede
//        byte[] key24 = new byte[24];
//        System.arraycopy(keyBytes, 0, key24, 0, 16);
//        System.arraycopy(keyBytes, 0, key24, 16, 8);
//
//        SecretKeySpec keySpec = new SecretKeySpec(key24, "DESede");
//
//        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
//        cipher.init(Cipher.DECRYPT_MODE, keySpec);
//
//        byte[] result = cipher.doFinal(toDecrypt);
//        return new String(result, "UTF-8");
//    }
//


//    private Map<String, Object> callLoginSPAndRunPythonDTO(String userId, MultipartFile capturedImagePath) {
//        try {
//            String pextra1 = "E";
//
//            List<Map<String, Object>> users = jdbcTemplate.queryForList(
//                    "SELECT TOP (1000) [ID],[USER_ID],[USERNAME],[IMAGE_FILE_NAME],[IMAGE_PATH],[CREATED_ON],[IS_ACTIVE]FROM [WFD_CWFM].[dbo].[EMPLOYEE_FACE_MASTER] where USER_ID=?",
//                    userId
//            );
//
//            if (users.isEmpty()) return null;
//
//            for (Map<String, Object> row : users) {
//                String storedImagePath = (String) row.get("IMAGE_PATH");
//                String storedImagePath = ROOT_DIRECTORY + File.separator + storedImageFile;
//
//                boolean match = runPythonFaceCompare(capturedImagePath, storedImagePath);
//
    //                if (match) {
    //                    return row; // return the matched user row
    //                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }



//    public String userImageLogin(String userId, String base64Image) {
//
//        try {
//
//            // === 1. Generate unique filename (C# style)
//            String fileName = generateFileNameLikeCSharp();
//            Path imagePath = Paths.get(ROOT_DIRECTORYUserLogin, fileName);
//
//            // === 2. Remove Base64 header if present
//            if (base64Image.contains(",")) {
//                base64Image = base64Image.split(",")[1];
//            }
//
//            // === 3. Decode Base64
//            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
//
//            // === 4. Convert to BufferedImage (C# Image.FromStream equivalent)
//            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
//            BufferedImage bufferedImage = ImageIO.read(bis);
//
//            if (bufferedImage == null) {
//                return "";
//            }
//
//            // === 5. Create folder if not exists
//            Files.createDirectories(imagePath.getParent());
//
//            // === 6. Save as JPEG (same as C# img.Save(..., Jpeg))
//            ImageIO.write(bufferedImage, "jpg", imagePath.toFile());
//
//
//
//
//            // RETURN FILE PATH for next step
//            return imagePath.toString();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//    }

//    private String generateFileNameLikeCSharp() {
//
//        LocalDateTime now = LocalDateTime.now();
//
//        // === Same variables as C#
//        String hour = String.valueOf(now.getHour());
//        String minute = String.valueOf(now.getMinute());
//        String millisecond = String.valueOf(now.getNano() / 1_000_000);
//
//        int yyyy = now.getYear();
//        int mon = now.getMonthValue();
//        int ddd = now.getDayOfMonth();
//
//        String fileExt = ".Jpeg";
//
//        String fnamev = ddd + "" + mon + "" + yyyy + hour + minute + millisecond;
//
//        // === SAME Replace chain as C#
//        fnamev = fnamev.replace("'", "_")
//                .replace("\"", "")
//                .replace("&", "")
//                .replace("-", "_")
//                .replace("%", "")
//                .replace(",", "_")
//                .replace("�", "_")
//                .replace("?", "_")
//                .replace("–", "_")
//                .replace(".", "_")
//                .replace(" ", "_");
//
//        fnamev = fnamev + fileExt;
//
//        return fnamev;
//    }
//





//    public String callLoginSPAndRunPython(String userId, String capturedImagePath) {
//
//        try {
//            String pextra1 = "E";
//
//            List<Map<String, Object>> users = jdbcTemplate.queryForList(
//                    "EXEC LOGIN_IMAGE_COMPARE ?, ?, ?, ?, ?, ?, ?",
//                    userId,
//                    capturedImagePath,
//                    pextra1, "", "", "", ""
//            );
//
//            if (users.isEmpty()) {
//                return "";
//            }
//
//            // 🔥 LOOP LIKE C#
//            for (Map<String, Object> row : users) {
//
//                String storedImageFile = (String) row.get("USER_IMAGE");
//                String storedImagePath = userImagePath() + File.separator + storedImageFile;
//
//                boolean match = runPythonFaceCompare(capturedImagePath, storedImagePath);
//
//                if (match) {
//                    return buildLoginResponse(row);
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return "";
//    }



//    private String buildLoginResponse(Map<String, Object> row) {
//
//        String userId = row.get("USERID").toString();
//        String encPwd = row.get("PASSWORD").toString();
//        String username = row.get("USERNAME").toString();
//        String retainer = row.get("RETAINER_CODE").toString();
//        String pub = row.get("PUB_CODE").toString();
//
//
//        return userId + "~" + encPwd + "~" + encPwd + "~" + username + "~" + retainer + "~" + pub;
//    }




    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {

        final int R = 6371000;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }
}
