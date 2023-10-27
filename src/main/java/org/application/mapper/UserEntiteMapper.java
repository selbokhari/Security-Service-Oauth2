package org.application.mapper;

import org.application.dto.UserDto;
import org.application.entities.UtilisateurEntite;

public class UserEntiteMapper {

    public static UtilisateurEntite mapToUserEntite(UserDto userDto) {
        return UtilisateurEntite.builder()
                .prenom(userDto.getPrenom())
                .nom(userDto.getNom())
                .password(userDto.getPassword())
                .login(userDto.getLogin())
                .email(userDto.getEmail())
                .roles(RoleMapper.mapToListRoleEntite(userDto.getRoles()))
                .build();
    }

    public static UserDto mapToUserDto(UtilisateurEntite userEntite) {
        return UserDto.builder()
                .prenom(userEntite.getPrenom())
                .nom(userEntite.getNom())
                .userId(userEntite.getUserId())
                .login(userEntite.getLogin())
                .email(userEntite.getEmail())
                .roles(RoleMapper.mapToListRoleDto(userEntite.getRoles()))
                .build();
    }


}
