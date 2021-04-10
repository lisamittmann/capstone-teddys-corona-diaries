package de.neuefische.teddyscoronadiaries.googleoauth.service;

import de.neuefische.teddyscoronadiaries.googleoauth.config.GoogleOAuthConfig;
import de.neuefische.teddyscoronadiaries.googleoauth.model.VerifyTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class GoogleOAuthService {

    private final RestTemplate restTemplate;
    private static final String GOOGLE_TOKEN_BASE_URL = "https://oauth2.googleapis.com/tokeninfo";

    @Autowired
    public GoogleOAuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<VerifyTokenResponse> verifyToken(String token) {

        String url = GOOGLE_TOKEN_BASE_URL + "?id_token=" + token;

        try {
            ResponseEntity<VerifyTokenResponse> verifyTokenResponse = restTemplate.getForEntity(url, VerifyTokenResponse.class);
            return Optional.of(verifyTokenResponse.getBody());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
