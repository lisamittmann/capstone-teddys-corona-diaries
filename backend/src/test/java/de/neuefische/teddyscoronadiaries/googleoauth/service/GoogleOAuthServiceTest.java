package de.neuefische.teddyscoronadiaries.googleoauth.service;

import de.neuefische.teddyscoronadiaries.googleoauth.model.VerifyTokenResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GoogleOAuthServiceTest {

    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private final GoogleOAuthService googleOAuthService = new GoogleOAuthService(restTemplate);
    private final String baseUrl = "https://oauth2.googleapis.com/tokeninfo";

    @Test
    @DisplayName("Verify token should return VerifyTokenResponse")
    public void verifyTokenShouldReturnVerifyTokenResponse() {
        // Given
        String token = "awesomeTestToken";
        String url = baseUrl + "?id_token=" + token;

        when(restTemplate.getForEntity(url, VerifyTokenResponse.class)).thenReturn(
                ResponseEntity.ok(VerifyTokenResponse.builder()
                        .clientId("awesomeClientID")
                        .googleId("awesomeGoogleID")
                        .email("awesomeEmail@gmail.com")
                        .name("Awesome Name")
                        .build())
        );

        // when
        Optional<VerifyTokenResponse> result = googleOAuthService.verifyToken(token);

        // Then
        assertThat(result, is(Optional.of(
                VerifyTokenResponse.builder()
                        .clientId("awesomeClientID")
                        .googleId("awesomeGoogleID")
                        .email("awesomeEmail@gmail.com")
                        .name("Awesome Name")
                        .build()
        )));

    }

    @Test
    @DisplayName("Verify token should return empty optional when API is unavailable")
    public void verifyTokenShouldReturnEmptyOptional() {
        // Given
        String token = "awesomeTestToken";
        String url = baseUrl + "?id_token=" + token;

        when(restTemplate.getForEntity(url, VerifyTokenResponse.class)).thenThrow(new RestClientException("Cannot verify"));

        // When
        Optional<VerifyTokenResponse> result = googleOAuthService.verifyToken(token);

        // Then
        assertTrue(result.isEmpty());
    }

}