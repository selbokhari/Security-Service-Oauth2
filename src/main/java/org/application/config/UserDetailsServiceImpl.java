package org.application.config;

import org.application.entities.RoleEntite;
import org.application.entities.UtilisateurEntite;
import org.application.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UtilisateurEntite userEntite = userService.loadUserByUsername(username);
        String[] roles = userEntite.getRoles().stream().map(RoleEntite::getNom).toArray(String[]::new);
        return User.builder()
                .username(userEntite.getLogin())
                .password(userEntite.getPassword())
                .authorities(roles)
                .build();
    }
}
