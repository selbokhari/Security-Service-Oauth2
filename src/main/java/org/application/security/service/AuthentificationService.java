package org.application.security.service;

import org.springframework.security.core.Authentication;

public interface AuthentificationService {

    String genererAccessToken(Authentication authentication);

    String genrerRefreshToken(Authentication authentication);

}
