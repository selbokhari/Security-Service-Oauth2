package org.application.controller;

import lombok.AllArgsConstructor;
import org.application.security.service.AuthentificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
public class AuthenticationController {

    private final AuthentificationService authentificationService;
    private final JwtDecoder jwtDecoder;

    @PostMapping("/auth")
    public Map<String, Object> authentification(Authentication authentication) {
        return Map.of(
                "accessToken", authentificationService.genererAccessToken(authentication),
                "refreshToken", authentificationService.genrerRefreshToken(authentication)
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, Object>> refreshToken(@RequestBody Map<String, String> refreshTokenMap) {
        String refreshToken = Optional.ofNullable(refreshTokenMap)
                .map(map -> map.get("refreshToken"))
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Le refresh token est non envoyé ! "));
        return ResponseEntity.ok(Map.of("accessToken", authentificationService.genrerRefreshDepuisAccessToken(refreshToken)));
    }

}
