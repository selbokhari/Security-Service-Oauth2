package org.application.controller;

import lombok.AllArgsConstructor;
import org.application.security.service.AuthentificationService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@AllArgsConstructor
@RestController
public class AuthenticationController {

    private final AuthentificationService authentificationService;

    @PostMapping("/auth")
    public Map<String, Object> authentification(Authentication authentication) {
        return Map.of(
                "accessToken", authentificationService.genererAccessToken(authentication),
                "refreshToken", authentificationService.genrerRefreshToken(authentication)
        );
    }

}
