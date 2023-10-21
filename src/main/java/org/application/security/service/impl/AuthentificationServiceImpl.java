package org.application.security.service.impl;

import lombok.AllArgsConstructor;
import org.application.security.service.AuthentificationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtClaimsSet.Builder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthentificationServiceImpl implements AuthentificationService {

    private final JwtEncoder jwtEncoder;

    @Override
    public String genererAccessToken(Authentication authentication) {
        Instant instant = Instant.now();
        Instant exprireIn = instant.plus(5, ChronoUnit.MINUTES);
        return genererToken(authentication, instant, exprireIn, false);
    }

    @Override
    public String genrerRefreshToken(Authentication authentication) {
        Instant instant = Instant.now();
        Instant expriresIn = instant.plus(30, ChronoUnit.MINUTES);
        return genererToken(authentication, instant, expriresIn, true);
    }

    private String genererToken(Authentication authentication, Instant instant, Instant expriresIn, boolean isRefresh) {
        Builder jwtClaimsSetBuilder = JwtClaimsSet.builder()
                .issuer("security-module")
                .issuedAt(instant)
                .expiresAt(expriresIn)
                .subject(authentication.getName());

        if (!isRefresh) {
            String authorites = authentication.getAuthorities().stream().map(auth -> auth.getAuthority()).collect(Collectors.joining(" "));
            jwtClaimsSetBuilder.claim("scope", authorites);
        }

        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSetBuilder.build())).getTokenValue();
    }

}
