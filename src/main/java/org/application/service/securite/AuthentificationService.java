package org.application.service;

import org.springframework.security.core.Authentication;

import java.util.Map;

public interface AuthentificationService {

    Map<String, String> authentifier(String username,String password);

    String genererAccessToken(Authentication authentication);

    String genrerRefreshToken(Authentication authentication);

    String genrerRefreshDepuisAccessToken(String refreshToken);

}
