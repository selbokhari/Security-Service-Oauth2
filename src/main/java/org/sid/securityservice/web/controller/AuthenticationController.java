package org.sid.securityservice.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
public class AuthenticationController {

    private JwtEncoder jwtEncoder;

    @PostMapping("/auth")
    public Map<String, Object> authentification(Authentication authentication) {
        Instant instant = Instant.now();
        String authorites = authentication.getAuthorities().stream().map(auth -> auth.getAuthority()).collect(Collectors.joining(" "));
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuer("security-module")
                .issuedAt(instant)
                .expiresAt(instant.plus(5, ChronoUnit.MINUTES))
                .subject(authentication.getName())
                .claim("scope", authorites)
                .build();
        String accessToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
        return new HashMap<>() {
            {
                put("accessToken", accessToken);
            }
        };
    }
}
