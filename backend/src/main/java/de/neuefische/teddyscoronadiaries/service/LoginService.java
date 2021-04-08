package de.neuefische.teddyscoronadiaries.service;

import de.neuefische.teddyscoronadiaries.security.AppUserMongoDb;
import de.neuefische.teddyscoronadiaries.googleoauth.config.GoogleOAuthConfig;
import de.neuefische.teddyscoronadiaries.googleoauth.model.VerifyTokenResponse;
import de.neuefische.teddyscoronadiaries.googleoauth.service.GoogleOAuthService;
import de.neuefische.teddyscoronadiaries.security.AppUser;
import de.neuefische.teddyscoronadiaries.model.user.GoogleOAuthUserDTO;
import de.neuefische.teddyscoronadiaries.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class LoginService {

    private final GoogleOAuthConfig googleOAuthConfig;
    private final GoogleOAuthService googleOAuthService;
    private final AppUserMongoDb appUserMongoDb;
    private final JwtUtils jwtUtils;

    @Autowired
    public LoginService(GoogleOAuthConfig googleOAuthConfig, GoogleOAuthService googleOAuthService, AppUserMongoDb appUserMongoDb, JwtUtils jwtUtils) {
        this.googleOAuthConfig = googleOAuthConfig;
        this.googleOAuthService = googleOAuthService;
        this.jwtUtils = jwtUtils;
        this.appUserMongoDb = appUserMongoDb;
    }


    public String loginWithGoogleUser(GoogleOAuthUserDTO userDTO) {

        Optional<VerifyTokenResponse> tokenResponse = googleOAuthService.verifyToken(userDTO.getToken());

        if (tokenResponse.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User cannot be verified");
        }

        VerifyTokenResponse verifiedToken = tokenResponse.get();

        //TODO create new method
        if (!verifiedToken.getClientId().equals(googleOAuthConfig.getClientId()) ||
                !verifiedToken.getGoogleId().equals(userDTO.getProfile().getGoogleId()) ||
                !verifiedToken.getEmail().equals(userDTO.getProfile().getEmail()) ||
                !verifiedToken.getName().equals(userDTO.getProfile().getName())
        ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unverified user or client ID");
        }

        AppUser appUser = new AppUser(
                userDTO.getProfile().getGoogleId(),
                userDTO.getProfile().getName(),
                getEmailSha(userDTO.getProfile().getEmail())
        );

        appUserMongoDb.findById(appUser.getGoogleId()).orElse(appUserMongoDb.save(appUser));

        return jwtUtils.createToken(appUser.getGoogleId(), new HashMap<>(Map.of(
                "name", appUser.getName(),
                "emailSha", appUser.getEmailSha256()
        )));

    }

    public String getEmailSha(String email) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
            return digest.digest(email.getBytes(StandardCharsets.UTF_8)).toString();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot hash email");
        }
    }

}
