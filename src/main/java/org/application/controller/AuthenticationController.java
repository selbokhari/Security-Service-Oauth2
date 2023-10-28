package org.application.controller;

import lombok.AllArgsConstructor;
import org.application.service.securite.AuthentificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@RestController
public class AuthenticationController {

    private final AuthentificationService authentificationService;

    @PostMapping("/auth")
    public ResponseEntity<Map<String, String>> authentification(@RequestParam String username, @RequestParam String password) {
        Map<String, String> response = authentificationService.authentifier(username, password);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, Object>> refreshToken(@RequestBody Map<String, String> refreshTokenMap) {
        String refreshToken = Optional.ofNullable(refreshTokenMap)
                .map(map -> map.get("refreshToken"))
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Le refresh token est non envoyé ! "));
        return ResponseEntity.ok(Map.of("accessToken", authentificationService.genrerRefreshDepuisAccessToken(refreshToken)));
    }

}
