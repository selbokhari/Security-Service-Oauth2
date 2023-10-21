package org.application.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class RoleDto {

    @NotNull
    private Long roleId;

    @NotEmpty
    private String nom;

    private String authorite;

}
