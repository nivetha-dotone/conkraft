
package com.wfd.dot1.cwfm.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wfd.dot1.cwfm.dto.CertificationAssignmentRequestDTO;
import com.wfd.dot1.cwfm.dto.EmployeeRequestDTO;
import com.wfd.dot1.cwfm.dto.PersonSkillAssignmentDTO;
import com.wfd.dot1.cwfm.dto.PostSkillWfd;
import com.wfd.dot1.cwfm.dto.ProficiencyDTO;
import com.wfd.dot1.cwfm.dto.PunchRequestDTO;
import com.wfd.dot1.cwfm.dto.UpdateEmployeeRequestDTO;
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

@Repository
public class WfdEmployeeService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final WfdAuthService wfdAuthService;

    public String getCreateSkillsUrl() {
        return QueryFileWatcher.getQuery("getCreateSkillsUrl");
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

    public String getfindCertifUrl() {
        return QueryFileWatcher.getQuery("getFindCertifcUrl");
    }

    public String getfindSkillsUrl() {
        return QueryFileWatcher.getQuery("getFindSkillsUrl");
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
        } catch (Exception var7) {
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
        } catch (Exception var7) {
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
        } catch (Exception var7) {
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
        } catch (HttpServerErrorException | HttpClientErrorException e) {
            return ((HttpStatusCodeException)e).getResponseBodyAsString();
        } catch (Exception e) {
            return "Error while creating skill: " + e.getMessage();
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
        } catch (HttpServerErrorException | HttpClientErrorException e) {
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
        } catch (HttpServerErrorException | HttpClientErrorException e) {
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
        } catch (HttpServerErrorException | HttpClientErrorException e) {
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
        } catch (HttpServerErrorException | HttpClientErrorException e) {
            return ((HttpStatusCodeException)e).getResponseBodyAsString();
        } catch (Exception e) {
            return "Error while creating Certification: " + e.getMessage();
        }
    }

    public String getHostName() {
        return QueryFileWatcher.getQuery("HostName");
    }

    public String getFindPersonKey() {
        return QueryFileWatcher.getQuery("getPersonKeyEmpWFD");
    }

    public String getUpdateSkillURL() {
        return QueryFileWatcher.getQuery("getUpdateSkillURLWFD");
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
            String var10000 = this.getHostName();
            String url = var10000 + this.getCreateEmpWFD();
            ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.POST, entity, String.class, new Object[0]);
            return response.getStatusCode().is2xxSuccessful() ? "Employee successfully Inserted in WFD" : "Unexpected response from WFD";
        } catch (HttpClientErrorException e) {
            String errorBody = e.getResponseBodyAsString();
            return e.getStatusCode() == HttpStatus.BAD_REQUEST && errorBody.contains("The ID already exists within the system") ? "The ID already exists within the system" : errorBody;
        } catch (Exception e) {
            return "Error creating employee in WFD API: " + e.getMessage();
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
        } catch (HttpServerErrorException | HttpClientErrorException e) {
            return ((HttpStatusCodeException)e).getResponseBodyAsString();
        } catch (Exception e) {
            return "Error assigning skill: " + e.getMessage();
        }
    }
}
