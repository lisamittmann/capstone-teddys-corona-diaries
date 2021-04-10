package de.neuefische.teddyscoronadiaries.controller;

import de.neuefische.teddyscoronadiaries.googleoauth.model.VerifyTokenResponse;
import de.neuefische.teddyscoronadiaries.model.user.GoogleOAuthUserDTO;
import de.neuefische.teddyscoronadiaries.model.user.GoogleProfileObject;
import de.neuefische.teddyscoronadiaries.security.AppUserMongoDb;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"security.jwt.secret=supertestsecret", "google.auth.client-id=awesomeClientId"})
class UserControllerTest {

    @LocalServerPort
    private int serverPort;

    private String getUrl() {
        return "http://localhost:" + serverPort + "auth/login/google";
    }

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private AppUserMongoDb appUserMongoDb;

    @Test
    @DisplayName("Login with Google should return JWT token")
    public void loginWithGoogleShouldReturnJwtToken() {
        //Given
        GoogleOAuthUserDTO userDTO = getUserDTO();
        String verifyTokenUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=awesomeTokenId";

        when(restTemplate.getForEntity(verifyTokenUrl, VerifyTokenResponse.class)).thenReturn(
                ResponseEntity.ok(VerifyTokenResponse.builder()
                        .clientId("awesomeClientId")
                        .googleId("awesomeGoogleId")
                        .email("teddysAwesomeEmail")
                        .name("Teddy Teddyson")
                        .build())
        );

        // When
        ResponseEntity<String> response = testRestTemplate.postForEntity(getUrl(), userDTO, String.class);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        Claims claims = Jwts.parser().setSigningKey("supertestsecret").parseClaimsJws(response.getBody()).getBody();
        assertThat(claims.getSubject(), Matchers.is("awesomeGoogleId"));

    }


    private static GoogleOAuthUserDTO getUserDTO() {
        return GoogleOAuthUserDTO.builder()
                .profile(GoogleProfileObject.builder()
                        .email("teddysAwesomeEmail")
                        .familyName("Teddyson")
                        .givenName("Teddy")
                        .googleId("awesomeGoogleId")
                        .imageUrl("awesomeImageUrl")
                        .name("Teddy Teddyson")
                        .build())
                .token("awesomeTokenId")
                .build();
    }
}