package com.wfd.dot1.cwfm.service;

import com.wfd.dot1.cwfm.util.QueryFileWatcher;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Repository
public class WfdAuthService {
    private final RestTemplate restTemplate;

    public WfdAuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public String getUserName() {
        return QueryFileWatcher.getQuery("USERNAME");
//        return "DTSV0001";
    }
  public String getPassword() {
        return QueryFileWatcher.getQuery("PASSWORD");
//    return "Kronos@890";
    }
  public String getClientId() {
        return QueryFileWatcher.getQuery("CLIENT_ID");
//    return "yWnPaMo9nyN6rKZ2ud95aSol71Y3Gn9t";
    }
  public String getClientS() {
        return QueryFileWatcher.getQuery("CLIENT_SECRET");
//      return "VuOdka9SZJ3jUOHB";
    }
  public String getGrantType() {
        return QueryFileWatcher.getQuery("GRANT_TYPE");
//            return "password";

    }

    public String getAuth_Chain() {
        return QueryFileWatcher.getQuery("AUTH_CHAIN");
//        return "OAuthLdapService";
    }


   public String getAuthUrl() {
        return QueryFileWatcher.getQuery("AUTHLOGINURL");
//       return "https://partnersand-041.cfn.mykronos.com/api/authentication/access_token";
    }
    public String getHost() {
        return QueryFileWatcher.getQuery("HostName");
//       return "https://partnersand-041.cfn.mykronos.com/api/authentication/access_token";
    }


    public String getAccessToken() {
        String url = getAuthUrl();

        String host = getHost();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("username", getUserName());
        form.add("password", getPassword());
        form.add("client_id",getClientId());
        form.add("client_secret", getClientS());
        form.add("grant_type", getGrantType());
        form.add("auth_chain", getAuth_Chain());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(host+url, request, Map.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return (String) response.getBody().get("access_token");
        }

        throw new RuntimeException("Failed to fetch access token");
    }
}

