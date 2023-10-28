package org.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;


@Data
@Builder
public class UtilisateurDto {

    @NotNull
    private Long userId;

    private String prenom;

    private String nom;

    private String login;

    @JsonIgnore
    private String password;

    @Email
    private String email;

    @NotEmpty
    Set<RoleDto> roles;

}
