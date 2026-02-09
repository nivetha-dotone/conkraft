package com.wfd.dot1.cwfm.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wfd.dot1.cwfm.dto.FaceLoginResponse;
import com.wfd.dot1.cwfm.dto.FaceRegistrationDTO;
import com.wfd.dot1.cwfm.service.FaceRegistrationRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/faced")
public class FaceLoginController {



    @Autowired
    private FaceRegistrationRepository faceRegistrationRepository;




    @PostMapping(value = "/register",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerFace(@RequestPart("registerFace") String registerFace,@RequestParam(value = "imageFile", required = false) MultipartFile imageFile,  HttpServletRequest request) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        FaceRegistrationDTO FaceRegistrationDTODto = mapper.readValue(registerFace, FaceRegistrationDTO.class);


        String result = faceRegistrationRepository.registerFace(FaceRegistrationDTODto, imageFile);

        if ("SUCCESS".equals(result)) {
            return ResponseEntity.ok("Face Registered Successfully");
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }





    @PostMapping(value="/save-image-auto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FaceLoginResponse> saveImageAuto(
            @RequestParam("userId") String userId,
            @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            // 1️⃣ Validate request
            if (userId == null || imageFile == null) {
                return ResponseEntity.badRequest()
                        .body(new FaceLoginResponse(false, "Invalid request"));
            }

            // 2️⃣ Call Service
            FaceLoginResponse response = faceRegistrationRepository.faceLogin(userId,imageFile);

            // 3️⃣ Map Service Result → HTTP Status
            if (response.isSuccess()) {
                return ResponseEntity.ok(response);  // 200
            }

            if ("Face login not enabled".equals(response.getMessage())) {
                return ResponseEntity.status(403).body(response);
            }

            if ("Face authentication failed".equals(response.getMessage())) {
                return ResponseEntity.status(401).body(response);
            }

            if ("Failed to save captured image".equals(response.getMessage())) {
                return ResponseEntity.status(500).body(response);
            }

            // Default fallback
            return ResponseEntity.internalServerError().body(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(new FaceLoginResponse(false, "Server error"));
        }
    }










}
