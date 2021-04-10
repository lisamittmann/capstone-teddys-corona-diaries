package de.neuefische.teddyscoronadiaries.service;

import de.neuefische.teddyscoronadiaries.googleoauth.config.GoogleOAuthConfig;
import de.neuefische.teddyscoronadiaries.googleoauth.model.VerifyTokenResponse;
import de.neuefische.teddyscoronadiaries.googleoauth.service.GoogleOAuthService;
import de.neuefische.teddyscoronadiaries.model.user.GoogleOAuthUserDTO;
import de.neuefische.teddyscoronadiaries.model.user.GoogleProfileObject;
import de.neuefische.teddyscoronadiaries.security.AppUser;
import de.neuefische.teddyscoronadiaries.security.AppUserMongoDb;
import de.neuefische.teddyscoronadiaries.security.JwtUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginServiceTest {

    private final GoogleOAuthConfig googleOAuthConfig = mock(GoogleOAuthConfig.class);
    private final GoogleOAuthService googleOAuthService = mock(GoogleOAuthService.class);
    private final AppUserMongoDb appUserMongoDb = mock(AppUserMongoDb.class);
    private final JwtUtils jwtUtils = mock(JwtUtils.class);

    private final LoginService loginService = new LoginService(googleOAuthConfig, googleOAuthService, appUserMongoDb, jwtUtils);

    @Test
    @DisplayName("Login with Google should return valid JWT token")
    public void loginWithGoogleShouldReturnValidJwtToken() {
        // Given
        GoogleOAuthUserDTO userDTO = getUserDTO();

        when(googleOAuthService.verifyToken(userDTO.getToken())).thenReturn(Optional.of(VerifyTokenResponse.builder()
                .clientId("awesomeClientId")
                .googleId("awesomeGoogleId")
                .email("teddysAwesomeEmail")
                .name("Teddy Teddyson")
                .build()));

        when(googleOAuthConfig.getClientId()).thenReturn("awesomeClientId");

        when(appUserMongoDb.findById("awesomeGoogleId")).thenReturn(Optional.of(getAppUser()));

        when(jwtUtils.createToken("awesomeGoogleId", new HashMap<>())).thenReturn("awesomeJwtToken");

        // When
        String result = loginService.loginWithGoogleUser(userDTO);

        // Then
        assertThat(result, is("awesomeJwtToken"));
        verify(appUserMongoDb).findById(getAppUser().getId());
    }

    @Test
    @DisplayName("Login with Google should throw error when token cannot be verified")
    public void loginWithGoogleShouldThrowErrorWhenTokenIsNotVerified() {
        // Given
        GoogleOAuthUserDTO userDTO = getUserDTO();

        when(googleOAuthService.verifyToken(userDTO.getToken())).thenReturn(Optional.empty());

        // Then
        assertThrows(ResponseStatusException.class, () -> {
            loginService.loginWithGoogleUser(userDTO);
        });
    }

    @Test
    @DisplayName("Login with Google should throw error token mismatchs verified token")
    public void loginWithGoogleShouldThrowErrorWhenTokensMismatch() {
        // Given
        GoogleOAuthUserDTO userDTO = getUserDTO();

        when(googleOAuthService.verifyToken(userDTO.getToken())).thenReturn(Optional.of(VerifyTokenResponse.builder()
                .clientId("awesomeClientId")
                .googleId("notAnAwesomeGoogleId")
                .email("teddysAwesomeEmail")
                .name("Teddy Teddyson")
                .build()));

        // Then
        assertThrows(ResponseStatusException.class, () -> {
            loginService.loginWithGoogleUser(userDTO);
        });

    }

    @Test
    @DisplayName("Get eMail SHA should return SHA value")
    public void getEmailShaShouldReturnEmailSha() {
        // Given
        String email = "teddysAwesomeEmail";

        // When
        String result = loginService.getEmailSha(email);

        // Then
        assertThat(result, is("c3983ee971f7159041eb1348bea18ec514529713b5b63a118ba6a0fc4ac265bb"));
    }

    @ParameterizedTest
    @MethodSource("provideUserAndVerifiedToken")
    @DisplayName("Compare user and verified token response")
    public void compareUserDtoAndVerifiedToken(GoogleOAuthUserDTO userDto, VerifyTokenResponse verifyTokenResponse, String clientId, boolean expected) {
        // Given
        when(googleOAuthConfig.getClientId()).thenReturn(clientId);

        // When
        boolean result = loginService.compareVerifiedTokenAndUser(userDto, verifyTokenResponse);

        // Then
        assertThat(result, is(expected));

    }

    private static AppUser getAppUser() {
        return new AppUser(
                "awesomeGoogleId",
                "Teddy Teddyson",
                "c3983ee971f7159041eb1348bea18ec514529713b5b63a118ba6a0fc4ac265bb"
        );
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

    private static Stream<Arguments> provideUserAndVerifiedToken() {

        GoogleOAuthUserDTO userDTO = GoogleOAuthUserDTO.builder()
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

        return Stream.of(
                Arguments.of(userDTO, VerifyTokenResponse.builder()
                        .clientId("awesomeClientId")
                        .googleId("awesomeGoogleId")
                        .email("teddysAwesomeEmail")
                        .name("Teddy Teddyson")
                        .build(), "awesomeClientId", true),
                Arguments.of(userDTO, VerifyTokenResponse.builder()
                        .clientId("awesomeClientId")
                        .googleId("awesomeGoogleId")
                        .email("teddysAwesomeEmail")
                        .name("Teddy Teddyson")
                        .build(), "notAnAwesomeClientId", false),
                Arguments.of(userDTO, VerifyTokenResponse.builder()
                        .clientId("awesomeClientId")
                        .googleId("notAnAwesomeGoogleId")
                        .email("teddysAwesomeEmail")
                        .name("Teddy Teddyson")
                        .build(), "awesomeClientId", false),
                Arguments.of(userDTO, VerifyTokenResponse.builder()
                        .clientId("awesomeClientId")
                        .googleId("awesomeGoogleId")
                        .email("notTeddysAwesomeEmail")
                        .name("Teddy Teddyson")
                        .build(), "awesomeClientId", false),
                Arguments.of(userDTO, VerifyTokenResponse.builder()
                        .clientId("awesomeClientId")
                        .googleId("awesomeGoogleId")
                        .email("teddysAwesomeEmail")
                        .name("Not Teddys Name")
                        .build(), "awesomeClientId", false)
        );
    }
}