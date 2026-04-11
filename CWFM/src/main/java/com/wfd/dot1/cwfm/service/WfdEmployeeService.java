

package com.wfd.dot1.cwfm.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wfd.dot1.cwfm.dto.*;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class WfdEmployeeService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final WfdAuthService wfdAuthService;

    public String getCreateSkillsUrl() {
        return QueryFileWatcher.getQuery("getCreateSkillsUrl");
    }

    public String getCreateLaborCatEntryUrl() {
        return QueryFileWatcher.getQuery("getCreateLaborsUrl");
    }

    public String getCreateProfUrl() {
        return QueryFileWatcher.getQuery("getCreateProfUrl");
    }

    public String getAssignCertiUrl() {
        return QueryFileWatcher.getQuery("getPutAssignCertifcUrl");
    }

    public String getfindProfUrl() {
        return QueryFileWatcher.getQuery("getFindProfUrl");
    }

    public String getCreateCertificateUrl() {
        return QueryFileWatcher.getQuery("getCreateCertifcUrl");
    }

    public String getupdateEmpStatusTRACURL() {
        return QueryFileWatcher.getQuery("getupdateempstatusUrl");
    }

    public String getfindCertifUrl() {
        return QueryFileWatcher.getQuery("getFindCertifcUrl");
    }

    public String getfindSkillsUrl() {
        return QueryFileWatcher.getQuery("getFindSkillsUrl");
    }

    public String getfindLaborCatUrl() {
        return QueryFileWatcher.getQuery("getFindLaborCatUrl");
    }

    public WfdEmployeeService(RestTemplate restTemplate, ObjectMapper objectMapper, WfdAuthService wfdAuthService) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.wfdAuthService = wfdAuthService;
    }

    public boolean verifyProfInWFD(String name) {
        try {
            String accessToken = this.wfdAuthService.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            HttpEntity<String> entity = new HttpEntity(headers);
            String var10000 = this.getHostName();
            String url = var10000 + this.getfindProfUrl() + name;
            ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.GET, entity, String.class, new Object[0]);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception var8) {
            return false;
        }
    }

    public boolean verifyCertiInWFD(String name) {
        try {
            String accessToken = this.wfdAuthService.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            HttpEntity<String> entity = new HttpEntity(headers);
            String var10000 = this.getHostName();
            String url = var10000 + this.getfindCertifUrl() + name;
            ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.GET, entity, String.class, new Object[0]);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception var8) {
            return false;
        }
    }

    public boolean verifySkillsInWFD(String name) {
        try {
            String accessToken = this.wfdAuthService.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            HttpEntity<String> entity = new HttpEntity(headers);
            String var10000 = this.getHostName();
            String url = var10000 + this.getfindSkillsUrl() + name;
            ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.GET, entity, String.class, new Object[0]);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception var8) {
            return false;
        }
    }

    public boolean verifyLaborCatEnInWFD(String name) {
        try {
            String accessToken = this.wfdAuthService.getAccessToken();

            String jsonBody = "{\n" +
                    "  \"where\": {\n" +
                    "    \"entries\": {\n" +
                    "      \"key\": \"qualifiers\",\n" +
                    "      \"values\": [\"" + name + "\"]\n" +
                    "    }\n" +
                    "  }\n" +
                    "}";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);

            HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

            String url = this.getHostName() + this.getfindLaborCatUrl();

            ResponseEntity<String> response = this.restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                return true;
            }

            return false;

        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return false;
            }
            return false;

        } catch (Exception ex) {
            return false;
        }
    }

    public String createSkillsInWFD(PostSkillWfd dto) {
        try {
            String jsonBody = this.objectMapper.writeValueAsString(dto);
            String accessToken = this.wfdAuthService.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);
            HttpEntity<String> entity = new HttpEntity(jsonBody, headers);
            String var10000 = this.getHostName();
            String url = var10000 + this.getCreateSkillsUrl();
            ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.POST, entity, String.class, new Object[0]);
            return (String)response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return ((HttpStatusCodeException)e).getResponseBodyAsString();
        } catch (Exception e) {
            return "Error while creating skill: " + e.getMessage();
        }
    }

    public String createLaborCatInWFD(PostLaborCatDTO dto) {
        try {

            ArrayList<PostLaborCatDTO> addList = new ArrayList<>();
            addList.add(dto);

            String jsonBody = this.objectMapper.writeValueAsString(addList);
            String accessToken = this.wfdAuthService.getAccessToken();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);

            HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

            String url = this.getHostName() + this.getCreateLaborCatEntryUrl();

            ResponseEntity<String> response = this.restTemplate.exchange(
                    url, HttpMethod.POST, entity, String.class);


            if (response.getStatusCode() == HttpStatus.OK) {
                return "SUCCESS:" + response.getBody();
            } else {
                return "FAILED:" + response.getBody();
            }

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return "FAILED:" + e.getResponseBodyAsString();
        } catch (Exception e) {
            return "FAILED:" + e.getMessage();
        }
    }
    public String createProfInWFD(ProficiencyDTO dto) {
        try {
            String jsonBody = this.objectMapper.writeValueAsString(dto);
            String accessToken = this.wfdAuthService.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);
            HttpEntity<String> entity = new HttpEntity(jsonBody, headers);
            String var10000 = this.getHostName();
            String url = var10000 + this.getCreateProfUrl();
            ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.POST, entity, String.class, new Object[0]);
            return (String)response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return ((HttpStatusCodeException)e).getResponseBodyAsString();
        } catch (Exception e) {
            return "Error while creating skill: " + e.getMessage();
        }
    }

    public String AssignCertificateInWFD(CertificationAssignmentRequestDTO dto, String gmID) {
        try {
            String jsonBody = this.objectMapper.writeValueAsString(dto);
            String accessToken = this.wfdAuthService.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);
            Integer personKey = this.getPersonKey(accessToken, gmID);
            HttpEntity<String> entity = new HttpEntity(jsonBody, headers);
            String var10000 = this.getHostName();
            String url = var10000 + this.getAssignCertiUrl() + personKey;
            ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.PUT, entity, String.class, new Object[0]);
            return (String)response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return ((HttpStatusCodeException)e).getResponseBodyAsString();
        } catch (Exception e) {
            return "Error while creating skill: " + e.getMessage();
        }
    }

    public String AssignSkillsProInWFD(PersonSkillAssignmentDTO dto, String gmID) {
        try {
            String jsonBody = this.objectMapper.writeValueAsString(dto);
            String accessToken = this.wfdAuthService.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);
            Integer personKey = this.getPersonKey(accessToken, gmID);
            HttpEntity<String> entity = new HttpEntity(jsonBody, headers);
            String var10000 = this.getHostName();
            String url = var10000 + this.getUpdateSkillURL() + personKey;
            ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.PUT, entity, String.class, new Object[0]);
            return (String)response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return ((HttpStatusCodeException)e).getResponseBodyAsString();
        } catch (Exception e) {
            return "Error while creating skill: " + e.getMessage();
        }
    }

    public String createCertiInWFD(PostSkillWfd dto) {
        try {
            String jsonBody = this.objectMapper.writeValueAsString(dto);
            String accessToken = this.wfdAuthService.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);
            HttpEntity<String> entity = new HttpEntity(jsonBody, headers);
            String var10000 = this.getHostName();
            String url = var10000 + this.getCreateCertificateUrl();
            ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.POST, entity, String.class, new Object[0]);
            return (String)response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return ((HttpStatusCodeException)e).getResponseBodyAsString();
        } catch (Exception e) {
            return "Error while creating Certification: " + e.getMessage();
        }
    }

    public WfdResponse updateEmpStatusTarminateSch(ActiveEmpStatusDto dto, String gpId) {
        try {
            String jsonBody = this.objectMapper.writeValueAsString(dto);
            String accessToken = this.wfdAuthService.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);
            Integer personKey = this.getPersonKey(accessToken, gpId);
            String var10000 = this.getHostName();
            String url = var10000 + this.getupdateEmpStatusTRACURL() + personKey;
            HttpEntity<String> entity = new HttpEntity(jsonBody, headers);
            ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.PUT, entity, String.class, new Object[0]);
            return response.getStatusCode().is2xxSuccessful() ? new WfdResponse(true, "Updated Successfully") : new WfdResponse(false, (String)response.getBody());
        } catch (HttpStatusCodeException e) {
            return new WfdResponse(false, e.getResponseBodyAsString());
        } catch (Exception e) {
            return new WfdResponse(false, "Error while updating employment status: " + e.getMessage());
        }
    }

    public String updateEmpStatusTarminate(ActiveEmpStatusDto dto, String gpId) {
        try {
            String jsonBody = this.objectMapper.writeValueAsString(dto);
            String accessToken = this.wfdAuthService.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);
            Integer personKey = this.getPersonKey(accessToken, gpId);
            String var10000 = this.getHostName();
            String url = var10000 + this.getupdateEmpStatusTRACURL() + personKey;
            HttpEntity<String> entity = new HttpEntity(jsonBody, headers);
            ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.PUT, entity, String.class, new Object[0]);
            return (String)response.getBody();
        } catch (HttpStatusCodeException e) {
            return e.getResponseBodyAsString();
        } catch (Exception e) {
            return "Error while updating employment status: " + e.getMessage();
        }
    }

    public String getHostName() {
        return QueryFileWatcher.getQuery("HostName");
    }

    public String getFindPersonKey() {
        return QueryFileWatcher.getQuery("getPersonKeyEmpWFD");
    }


    public String getUrlToCreateBS() {
        return QueryFileWatcher.getQuery("getUrlToCreateBS");
    }

    public String getUpdateSkillURL() {
        return QueryFileWatcher.getQuery("getUpdateSkillURLWFD");
    }

    public String getCheckLocationUrl() {
        return QueryFileWatcher.getQuery("getCkeckLoaction");
    }

    public String getCreateEmpWFD() {
        return QueryFileWatcher.getQuery("CreateEmpWFD");
    }

    public String getUpateEmpWFD() {
        return QueryFileWatcher.getQuery("UpdateEmpWFD");
    }

    public String getUpdatePUNCHEMPWFD() {
        return QueryFileWatcher.getQuery("UpdatePUNCHEMPWFD");
    }

    public String addEmployeePunchFace(PunchRequestDTO dto) {
        try {
            String jsonBody = this.objectMapper.writeValueAsString(dto);
            String accessToken = this.wfdAuthService.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);
            HttpEntity<String> entity = new HttpEntity(jsonBody, headers);
            String var10000 = this.getHostName();
            String url = var10000 + this.getUpdatePUNCHEMPWFD();
            ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.POST, entity, String.class, new Object[0]);
            if (response.getStatusCode().is2xxSuccessful()) {
                return "and also in updated in WFD system";
            } else {
                throw new RuntimeException("WFD API failed with status: " + String.valueOf(response.getStatusCode()));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating punch in WFD API", e);
        }
    }

    public String createEmployee(EmployeeRequestDTO dto) {
        try {
            String jsonBody = this.objectMapper.writeValueAsString(dto);
            String accessToken = this.wfdAuthService.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);
            HttpEntity<String> entity = new HttpEntity(jsonBody, headers);
            String var10 = this.getHostName();
            String url = var10 + this.getCreateEmpWFD();
            ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.POST, entity, String.class, new Object[0]);
            int var11 = response.getStatusCodeValue();
            return "STATUS:" + var11 + "\nBODY:" + (String)response.getBody();
        } catch (HttpServerErrorException | HttpClientErrorException e) {
            int var10000 = ((HttpStatusCodeException)e).getStatusCode().value();
            return "STATUS:" + var10000 + "\nBODY:" + ((HttpStatusCodeException)e).getResponseBodyAsString();
        } catch (Exception e) {
            return "STATUS:500\nBODY:Error creating employee in WFD API: " + e.getMessage();
        }
    }

    public String updateEmployee(UpdateEmployeeRequestDTO dto) {
        try {
            String jsonBody = this.objectMapper.writeValueAsString(dto);
            String accessToken = this.wfdAuthService.getAccessToken();
            Integer personKey = this.getPersonKey(accessToken, dto.getPersonInformation().getPerson().getPersonNumber());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);
            HttpEntity<String> entity = new HttpEntity(jsonBody, headers);
            String var10000 = this.getHostName();
            String url = var10000 + this.getUpateEmpWFD() + personKey;
            ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.PUT, entity, String.class, new Object[0]);
            return (String)response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error updating employee in WFD API", e);
        }
    }

    public Integer getPersonKey(String accessToken, String personNumber) {
        try {
            String jsonBody = "{\n  \"where\": {\n    \"employees\": {\n      \"key\": \"personnumber\",\n      \"values\": [\"" + personNumber + "\"]\n    }\n  }\n}";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);
            HttpEntity<String> entity = new HttpEntity(jsonBody, headers);
            String var10000 = this.getHostName();
            String url = var10000 + this.getFindPersonKey();
            ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.POST, entity, String.class, new Object[0]);
            JsonNode root = this.objectMapper.readTree((String)response.getBody());
            JsonNode idsNode = root.path("ids");
            if (idsNode.isArray() && idsNode.size() > 0) {
                return idsNode.get(0).asInt();
            } else {
                throw new RuntimeException("No ID found in response: " + (String)response.getBody());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching person key from WFD API", e);
        }
    }

    public String addPersonSkill(String personNumber, String skill, String proficiencyLevel, String effectiveDate) {
        try {
            String accessToken = this.wfdAuthService.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);
            Integer personKey = this.getPersonKey(accessToken, personNumber);
            String jsonBody = "{\n  \"assignments\": [\n    {\n      \"skill\": {\n        \"qualifier\": \"" + skill + "\"\n      },\n      \"proficiencyLevel\": {\n        \"qualifier\": \"" + proficiencyLevel + "\"\n      },\n      \"effectiveDate\": \"" + effectiveDate + "\",\n      \"active\": true\n    }\n  ]\n}";
            HttpEntity<String> entity = new HttpEntity(jsonBody, headers);
            String var10000 = this.getHostName();
            String url = var10000 + this.getUpdateSkillURL() + personKey;
            ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.PUT, entity, String.class, new Object[0]);
            return response.getStatusCode().is2xxSuccessful() ? "Skill Assigned Successfully" : (String)response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return ((HttpStatusCodeException)e).getResponseBodyAsString();
        } catch (Exception e) {
            return "Error assigning skill: " + e.getMessage();
        }
    }

    public boolean checkLocationInUKG(String path) {
        try {
            String accessToken = this.wfdAuthService.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);
            String var10000 = this.getHostName();
            String url = var10000 + this.getCheckLocationUrl() + path + "&date=1900-01-01&context=ORG";
            HttpEntity<String> entity = new HttpEntity(headers);
            ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.GET, entity, String.class, new Object[0]);
            return response.getStatusCode().is2xxSuccessful();
        } catch (HttpClientErrorException var7) {
            if (var7.getStatusCode().value() == 400 && var7.getResponseBodyAsString().contains("does not exist")) {
                return false;
            } else {
                throw var7;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error checking location in UKG", e);
        }
    }
    public void createNodeInUKG(String parentPath, String name, String type) {

        String accessToken = wfdAuthService.getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        Map<String, Object> body = new HashMap<>();
        body.put("parentNodeRef", Map.of("qualifier", parentPath));
        body.put("orgNodeTypeRef", Map.of("qualifier", type));
        body.put("name", name);
        body.put("effectiveDate", "1900-01-01");
        body.put("expirationDate", "3000-01-01");

        if ("Job".equals(type)) {
            body.put("genericJobRef", Map.of("qualifier", name));
        }


        HttpEntity<List<Map<String, Object>>> entity =
                new HttpEntity<>(List.of(body), headers);

        restTemplate.exchange(
                getHostName() + getUrlToCreateBS(),
                HttpMethod.POST,
                entity,
                String.class
        );
    }



}
