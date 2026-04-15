package com.wfd.dot1.cwfm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Repository
public class WfdAuthService {
    private final RestTemplate restTemplate;

    public WfdAuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public String getUserNameSand() {
        return QueryFileWatcher.getQuery("USERNAME");
    }
  public String getPasswordSand() {
        return QueryFileWatcher.getQuery("PASSWORD");
    }
  public String getClientIdSand() {
        return QueryFileWatcher.getQuery("CLIENT_ID");
    }
  public String getClientSSand() {
        return QueryFileWatcher.getQuery("CLIENT_SECRET");
    }
  public String getGrantTypeSand() {
        return QueryFileWatcher.getQuery("GRANT_TYPE");
    }

    public String getAuth_ChainSand() {
        return QueryFileWatcher.getQuery("AUTH_CHAIN");
    }

   public String getAuthUrl() {
        return QueryFileWatcher.getQuery("AUTHLOGINURL");
    }
    public String getHost() {
        return QueryFileWatcher.getQuery("HostName");
    }





    public String getUserNamePoc() {
        return QueryFileWatcher.getQuery("USERNAMEPOC");
    }
    public String getPasswordPoc() {
        return QueryFileWatcher.getQuery("PASSWORDPOC");
    }
    public String getClientIdPoc() {
        return QueryFileWatcher.getQuery("CLIENT_IDPOC");
    }
    public String getClientSPoc() {
        return QueryFileWatcher.getQuery("CLIENT_SECRETPOC");
    }
    public String getGrantTypePoc() {
        return QueryFileWatcher.getQuery("GRANT_TYPEPOC");

    }
    public String getRealmPoc() {
        return QueryFileWatcher.getQuery("REALMPOC");
    }
    public String getAudiencePoc() {
        return QueryFileWatcher.getQuery("AUDIENCEPOC");
    }
    public String getHostAuthPoc() {
        return QueryFileWatcher.getQuery("AUTHURLPOC");
    }

    public String getHostPoc() {
        return QueryFileWatcher.getQuery("HostNamePOC");
    }

    public String getISSANDORPOC() {
        return QueryFileWatcher.getQuery("ISSAND");
    }


    public String getAccessToken() {
        try {
            String issandorpoc = getISSANDORPOC();

            if (issandorpoc != null) {
                issandorpoc = issandorpoc.trim();
            }

            if ("yes".equalsIgnoreCase(issandorpoc)) {
                return getAccessTokenSand();
            } else if ("no".equalsIgnoreCase(issandorpoc)) {
                return getAccessOthPOC();
            } else {
                throw new IllegalArgumentException(
                        "Invalid value for ISSAND in query properties file:  while creating Access token" + issandorpoc
                );
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    public String getAccessCheckup(String username, String password) {
        try {
            String issandorpoc = getISSANDORPOC();

            if (issandorpoc != null) {
                issandorpoc = issandorpoc.trim();
            }



            if ("yes".equalsIgnoreCase(issandorpoc)) {
                String url = getAuthUrl();

                String host = getHost();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

                MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
                form.add("username", username);
                form.add("password", password);
                form.add("client_id",getClientIdSand());
                form.add("client_secret", getClientSSand());
                form.add("grant_type", getGrantTypeSand());
                form.add("auth_chain", getAuth_ChainSand());

                HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);

                ResponseEntity<Map> response = restTemplate.postForEntity(host+url, request, Map.class);

                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                    return "successful";
                }


            } else if ("no".equalsIgnoreCase(issandorpoc)) {


                String host = getHostAuthPoc();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

                MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
                form.add("username", username);
                form.add("password", password);
                form.add("client_id",getClientIdPoc());
                form.add("client_secret", getClientSPoc());
                form.add("grant_type", getGrantTypePoc());
                form.add("realm", getRealmPoc());
                form.add("audience", getAudiencePoc());

                HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);

                ResponseEntity<Map> response = restTemplate.postForEntity(host, request, Map.class);

                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                    return "successful";
                }


            } else {
                throw new IllegalArgumentException(
                        "Invalid value for ISSAND in query properties file: " + issandorpoc
                );
            }


            return "Something went wrong";

        } catch (HttpClientErrorException e) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> errorBody =
                        mapper.readValue(e.getResponseBodyAsString(), Map.class);

                if (errorBody.containsKey("error_description")) {
                    return errorBody.get("error_description").toString();
                }

            } catch (Exception ex) {
            }

            return "Invalid credentials";

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    public String getAccessTokenSand() {
        String url = getAuthUrl();

        String host = getHost();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("username", getUserNameSand());
        form.add("password", getPasswordSand());
        form.add("client_id",getClientIdSand());
        form.add("client_secret", getClientSSand());
        form.add("grant_type", getGrantTypeSand());
        form.add("auth_chain", getAuth_ChainSand());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(host+url, request, Map.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return (String) response.getBody().get("access_token");
        }

        throw new RuntimeException("Failed to fetch access token");
    }

    public String getAccessOthPOC() {

        String host = getHostAuthPoc();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("username", getUserNamePoc());
        form.add("password", getPasswordPoc());
        form.add("client_id",getClientIdPoc());
        form.add("client_secret", getClientSPoc());
        form.add("grant_type", getGrantTypePoc());
        form.add("realm", getRealmPoc());
        form.add("audience", getAudiencePoc());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(host, request, Map.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return (String) response.getBody().get("access_token");
        }

        throw new RuntimeException("Failed to fetch access token");
    }





}

