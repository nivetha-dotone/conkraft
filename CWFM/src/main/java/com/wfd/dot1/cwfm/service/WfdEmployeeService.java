package com.wfd.dot1.cwfm.service;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wfd.dot1.cwfm.dto.EmployeeRequestDTO;
import com.wfd.dot1.cwfm.dto.PostSkillWfd;
import com.wfd.dot1.cwfm.dto.UpdateEmployeeRequestDTO;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;

@Repository
public class WfdEmployeeService {


	 public String getCreateSkillsUrl() {
	        return QueryFileWatcher.getQuery("getCreateSkillsUrl");
	    }
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final WfdAuthService wfdAuthService;

    public WfdEmployeeService(RestTemplate restTemplate, ObjectMapper objectMapper, WfdAuthService wfdAuthService) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.wfdAuthService = wfdAuthService;
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

    public String createEmployee(EmployeeRequestDTO dto){
        try {
            // Convert DTO → JSON
            String jsonBody = objectMapper.writeValueAsString(dto);

            // Get access token dynamically
            String accessToken = wfdAuthService.getAccessToken();



            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);


            HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

//            String url = "https://partnersand-041.cfn.mykronos.com/api/v1/commons/persons";
            String url = getHostName()+getCreateEmpWFD();

            ResponseEntity<String> response = restTemplate.exchange(
                    url, HttpMethod.POST, entity, String.class
            );
            return response.getBody();

        } catch (Exception e) {
            throw new RuntimeException("Error creating employee in WFD API", e);
        }
    }

    public String updateEmployee(UpdateEmployeeRequestDTO dto)  {
        try {
            // Convert DTO → JSON
            String jsonBody = objectMapper.writeValueAsString(dto);

            // Get access token dynamically
            String accessToken = wfdAuthService.getAccessToken();

            // Get the internal PersonKey (numeric id)
            Integer personKey = getPersonKey(accessToken, dto.getPersonInformation().getPerson().getPersonNumber());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);

            HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

            // Use the resolved numeric key (17903) in the update URL
            String url = getHostName()+getUpateEmpWFD()+ personKey;

            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    entity,
                    String.class
            );

            return response.getBody();

        } catch (Exception e) {
            throw new RuntimeException("Error updating employee in WFD API", e);
        }
    }

    public Integer getPersonKey(String accessToken, String personNumber) {
        try {
            // Build request body dynamically
            String jsonBody = "{\n" +
                    "  \"where\": {\n" +
                    "    \"employees\": {\n" +
                    "      \"key\": \"personnumber\",\n" +
                    "      \"values\": [\"" + personNumber + "\"]\n" +
                    "    }\n" +
                    "  }\n" +
                    "}";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);

            HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
            String url = getHostName()+getFindPersonKey();

            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    String.class
            );
            // Parse JSON response and extract the first ID
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode idsNode = root.path("ids");
            if (idsNode.isArray() && idsNode.size() > 0) {
                return idsNode.get(0).asInt(); // return Integer 17903
            }

            throw new RuntimeException("No ID found in response: " + response.getBody());

        } catch (Exception e) {
            throw new RuntimeException("Error fetching person key from WFD API", e);
        }
    }

    public String addPersonSkill(String  personNumber, String skill, String proficiencyLevel, String effectiveDate) {
        try {
            // Build request body dynamically

            // Get access token dynamically
            String accessToken = wfdAuthService.getAccessToken();


            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);

            Integer personKey = getPersonKey(accessToken, personNumber);
            String jsonBody = "{\n" +
                    "  \"assignments\": [\n" +
                    "    {\n" +
                    "      \"skill\": {\n" +
                    "        \"qualifier\": \"" + skill + "\"\n" +
                    "      },\n" +
                    "      \"proficiencyLevel\": {\n" +
                    "        \"qualifier\": \"" + proficiencyLevel + "\"\n" +
                    "      },\n" +
                    "      \"effectiveDate\": \"" + effectiveDate + "\",\n" +
                    "      \"active\": true\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";

            // Get access token dynamically



            HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

//            String url = "https://partnersand-041.cfn.mykronos.com/api/v1/commons/persons/skills/"+personKey;
            String url = getHostName()+getUpdateSkillURL()+personKey;

            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    entity,
                    String.class
            );

            // Parse JSON response and extract the first ID
            return response.getBody();

        } catch (Exception e) {
            throw new RuntimeException("Error fetching person key from WFD API", e);
        }
    }

    public String createSkillsInWFD(PostSkillWfd dto){
        try {
            String jsonBody = objectMapper.writeValueAsString(dto);

            String accessToken = wfdAuthService.getAccessToken();
           
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);


            HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

//          String url = "https://partnersand-041.cfn.mykronos.com/api/v1/scheduling/skills";
            String url = getHostName()+getCreateSkillsUrl();

            ResponseEntity<String> response = restTemplate.exchange(
                    url, HttpMethod.POST, entity, String.class
            );
            return dto.getName()+ " Saved Successfully";

        }
        catch (HttpClientErrorException.BadRequest e) {
            return "already in the database";
        }
        catch (HttpClientErrorException e) {
            return "Client error: " + e.getStatusCode();
        }
        catch (Exception e) {
            throw new RuntimeException("Error while creating skill", e);
        }
    }



}

