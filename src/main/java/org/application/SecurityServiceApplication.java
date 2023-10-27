package org.application;

import org.application.dto.RoleDto;
import org.application.dto.UserDto;
import org.application.config.RsaKeysConfig;
import org.application.service.RoleService;
import org.application.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties(RsaKeysConfig.class)
public class SecurityServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityServiceApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    @Profile("!test")
    public CommandLineRunner init(RoleService roleService, UserService userService, PasswordEncoder passwordEncoder) {
        return args -> {
            // création des roles
            RoleDto userRole = RoleDto.builder()
                    .roleId(1l)
                    .nom("USER")
                    .build();

            RoleDto adminRole = RoleDto.builder()
                    .roleId(2l)
                    .nom("ADMIN")
                    .build();

            RoleDto userRoleDto = roleService.creerRole(userRole);
            RoleDto adminRoleDto = roleService.creerRole(adminRole);

            // création d'utilisateur
            UserDto user = UserDto.builder()
                    .nom("nom01")
                    .prenom("prenom01")
                    .login("user")
                    .email("user@gmail.com")
                    .password(passwordEncoder.encode("user"))
                    .build();

            // création d'admin
            UserDto admin = UserDto.builder()
                    .prenom("prenom02")
                    .nom("nom02")
                    .login("admin")
                    .email("admin@gmail.com")
                    .password(passwordEncoder.encode("admin"))
                    .build();

            UserDto userDto = userService.creerUtilisateur(user);
            UserDto adminDto = userService.creerUtilisateur(admin);

            // attacher les roles aux utilisateurs
            userService.affecterRolesUtilisateur( adminDto.getUserId(),Set.of(userRoleDto, adminRoleDto));
            userService.affecterRolesUtilisateur( userDto.getUserId(),Set.of(userRoleDto));
        };
    }

}
