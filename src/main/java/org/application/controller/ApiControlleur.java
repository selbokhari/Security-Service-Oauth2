package org.application.controller;

import org.application.dto.UserDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
public class ApiControlleur {

    @GetMapping("/test")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER')")
    public Map<String, Object> dataTest(Authentication authentication) {
        return Map.of("message", "Data test",
                "username", authentication.getName(),
                "authorities", authentication.getAuthorities()
        );
    }

    @GetMapping("/admins-api")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public Map<String, String> dataForAdminsOnly() {
        return Map.of("message", "Data for admins only !!");
    }

}
