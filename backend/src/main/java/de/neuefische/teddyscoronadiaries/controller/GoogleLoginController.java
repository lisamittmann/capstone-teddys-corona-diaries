package de.neuefische.teddyscoronadiaries.controller;

import de.neuefische.teddyscoronadiaries.googleoauth.config.GoogleOAuthConfig;
import de.neuefische.teddyscoronadiaries.model.user.GoogleOAuthUserDTO;
import de.neuefische.teddyscoronadiaries.service.LoginService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth/login/google")
public class GoogleLoginController {

    private final GoogleOAuthConfig googleOAuthConfig;
    private final LoginService loginService;

    public GoogleLoginController(GoogleOAuthConfig googleOAuthConfig, LoginService loginService) {
        this.googleOAuthConfig = googleOAuthConfig;
        this.loginService = loginService;
    }

    @GetMapping("clientid")
    public String getClientId() {
        return googleOAuthConfig.getClientId();
    }

    @PostMapping
    public String loginWithGoogle(@RequestBody GoogleOAuthUserDTO userDTO) {
        return loginService.loginWithGoogleUser(userDTO);
    }
}
