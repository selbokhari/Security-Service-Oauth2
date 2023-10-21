package org.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@Builder
public class UserDto {

    @NotNull
    private Long userId;

    private String username;

    @JsonIgnore
    private String password;

    @Email
    private String email;

    @NotEmpty
    List<RoleDto> roles;

}
