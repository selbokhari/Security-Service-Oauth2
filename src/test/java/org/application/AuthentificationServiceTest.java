package org.application;


import org.application.service.securite.AuthentificationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class AuthentificationServiceTest {

    @Autowired
    AuthentificationService authentificationService;

    @Autowired
    JwtDecoder jwtDecoder;

    @Test
    void authentifierTest() {

        Map<String, String> tokens = authentificationService.authentifier("user", "user");

        String accessToken = tokens.get("accessToken");
        String refreshToken = tokens.get("refreshToken");

        // tester la validité des tokens
        Assertions.assertDoesNotThrow(() -> jwtDecoder.decode(accessToken));
        Assertions.assertDoesNotThrow(() -> jwtDecoder.decode(refreshToken));

        // vérifier les authorites, vu qu'on sait déjà que user doit avoir une seule authorité "USER"
        Jwt decodedJwt = jwtDecoder.decode(accessToken);
        List<String> scopesList = Arrays.asList(((String) decodedJwt.getClaims().get("scope")).split(" "));

        // vérifier les authorités
        Assertions.assertEquals(List.of("USER"), scopesList);
    }

    @Test
    void genrerRefreshDepuisAccessTokenTest() {

        Map<String, String> tokens = authentificationService.authentifier("user", "user");
        String refreshToken = tokens.get("refreshToken");

        // tester le raffreshement d'un token à partir d'un refresh token
        String newAccessToken = authentificationService.genrerRefreshDepuisAccessToken(refreshToken);

        // tester la validité de token (expriation et signature)
        Assertions.assertDoesNotThrow(() -> jwtDecoder.decode(newAccessToken));

        // vérifer le username
        Jwt decodedJwt = jwtDecoder.decode(newAccessToken);
        Assertions.assertEquals("user", decodedJwt.getSubject());
    }

    @Test
    void genrerRefreshTokenTest() {

        List<GrantedAuthority> authorities = List.of("USER").stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        Authentication authentication = new UsernamePasswordAuthenticationToken("user", "user", authorities);

        // generation de refresh token
        String refreshToken = authentificationService.genrerRefreshToken(authentication);
        Assertions.assertDoesNotThrow(() -> jwtDecoder.decode(refreshToken));

        // vérifier le username
        Jwt decodedJwt = jwtDecoder.decode(refreshToken);
        Assertions.assertEquals(decodedJwt.getSubject(), authentication.getName());
    }

}
