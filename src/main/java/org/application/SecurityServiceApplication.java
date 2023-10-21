package org.application;

import org.application.dto.RoleDto;
import org.application.dto.UserDto;
import org.application.security.config.RsaKeysConfig;
import org.application.service.RoleService;
import org.application.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@EnableConfigurationProperties(RsaKeysConfig.class)
@SpringBootApplication
public class SecurityServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityServiceApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public CommandLineRunner init(RoleService roleServiceImpl, UserService userService, PasswordEncoder passwordEncoder) {
        return args -> {
            RoleDto userRole = RoleDto.builder()
                    .nom("USER")
                    .build();

            RoleDto adminRole = RoleDto.builder()
                    .nom("ADMIN")
                    .build();

            roleServiceImpl.creerRole(userRole);
            roleServiceImpl.creerRole(adminRole);

            // création des utilisateurs

            UserDto user = UserDto.builder()
                    .username("user")
                    .email("user@gmail.com")
                    .password(passwordEncoder.encode("user"))
                    .roles(List.of(userRole))
                    .build();

            UserDto admin = UserDto.builder()
                    .username("admin")
                    .roles(List.of(userRole, adminRole))
                    .email("admin@gmail.com")
                    .password(passwordEncoder.encode("admin"))
                    .build();

            userService.creerUtilisateur(user);
            userService.creerUtilisateur(admin);
        };
    }

}
