package org.application.config;

import org.application.entities.RoleEntite;
import org.application.entities.UtilisateurEntite;
import org.application.service.UtilisateurService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service("CustomUserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UtilisateurService utilisateurService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UtilisateurEntite userEntite = utilisateurService.recupererUtilisateurParLogin(username);
        String[] roles = userEntite.getRoles().stream().map(RoleEntite::getNom).toArray(String[]::new);
        return User.builder()
                .username(userEntite.getLogin())
                .password(userEntite.getPassword())
                .authorities(roles)
                .build();
    }
}
