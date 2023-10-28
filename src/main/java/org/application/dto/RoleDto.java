package org.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Builder
public class RoleDto {

    @NotNull
    private Long roleId;

    @NotEmpty
    private String nom;

    private String authorites;

}
