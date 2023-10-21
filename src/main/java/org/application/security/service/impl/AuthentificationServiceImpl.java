package org.application.security.service.impl;

import lombok.AllArgsConstructor;
import org.application.security.service.AuthentificationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.jwt.JwtClaimsSet.Builder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthentificationServiceImpl implements AuthentificationService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDeccoder;
    private final UserDetailsService userDetailsService;

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

    @Override
    public String genrerRefreshDepuisAccessToken(String refreshToken) {
        Instant instant = Instant.now();
        Instant expriresIn = instant.plus(5, ChronoUnit.MINUTES);
        Jwt decodedJwt = jwtDeccoder.decode(refreshToken);
        String username = decodedJwt.getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String authrites = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
        return genererToken(username, authrites, instant, expriresIn, true);
    }


    private String genererToken(Authentication authentication, Instant instant, Instant expriresIn, boolean isRefresh) {
        String authorites = authentication.getAuthorities().stream().map(auth -> auth.getAuthority()).collect(Collectors.joining(" "));
        return genererToken(authentication.getName(), authorites, instant, expriresIn, isRefresh);
    }

    private String genererToken(String username, String authorites, Instant instant, Instant expriresIn, boolean isRefresh) {
        Builder jwtClaimsSetBuilder = JwtClaimsSet.builder()
                .issuer("security-module")
                .issuedAt(instant)
                .expiresAt(expriresIn)
                .subject(username);

        if (!isRefresh) {
            jwtClaimsSetBuilder.claim("scope", authorites);
        }

        JwtClaimsSet jwtClaimsSet = jwtClaimsSetBuilder.build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
    }

}
