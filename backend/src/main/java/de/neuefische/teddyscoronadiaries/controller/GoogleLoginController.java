package de.neuefische.teddyscoronadiaries.controller;

import de.neuefische.teddyscoronadiaries.googleoauth.config.GoogleOAuthConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class GoogleLoginController {

    private final GoogleOAuthConfig googleOAuthConfig;

    public GoogleLoginController(GoogleOAuthConfig googleOAuthConfig) {
        this.googleOAuthConfig = googleOAuthConfig;
    }

    @GetMapping("login/google/clientid")
    public String getClientId() {
        return googleOAuthConfig.getClientId();
    }
}
