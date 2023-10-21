package org.application.mapper;

import org.application.dto.RoleDto;
import org.application.entities.RoleEntite;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RoleMapper {

    public static RoleEntite mapToRoleEntite(RoleDto roleDto) {
        return RoleEntite.builder()
                .nom(roleDto.getNom())
                .build();
    }

    public static RoleDto mapToRoleDto(RoleEntite roleEntite) {
        return RoleDto.builder()
                .roleId(roleEntite.getRoleId())
                .nom(roleEntite.getNom())
                .build();
    }

    public static List<RoleDto> mapToListRoleDto(List<RoleEntite> roleEntites) {
        return Optional.ofNullable(roleEntites)
                .map(roles -> roles.stream().map(RoleMapper::mapToRoleDto).collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    public static List<RoleEntite> mapToListRoleEntite(List<RoleDto> roleDtos) {
        return Optional.ofNullable(roleDtos)
                .map(roles -> roles.stream().map(RoleMapper::mapToRoleEntite).collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

}
