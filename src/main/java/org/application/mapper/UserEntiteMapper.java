package org.application.mapper;

import org.application.dto.UtilisateurDto;
import org.application.entities.UtilisateurEntite;

public class UserEntiteMapper {

    public static UtilisateurEntite mapToUserEntite(UtilisateurDto utilisateurDto) {
        return UtilisateurEntite.builder()
                .userId(utilisateurDto.getUserId())
                .prenom(utilisateurDto.getPrenom())
                .nom(utilisateurDto.getNom())
                .password(utilisateurDto.getPassword())
                .login(utilisateurDto.getLogin())
                .email(utilisateurDto.getEmail())
                .roles(RoleMapper.mapToListRoleEntite(utilisateurDto.getRoles()))
                .build();
    }

    public static UtilisateurDto mapToUserDto(UtilisateurEntite userEntite) {
        return UtilisateurDto.builder()
                .prenom(userEntite.getPrenom())
                .nom(userEntite.getNom())
                .userId(userEntite.getUserId())
                .login(userEntite.getLogin())
                .email(userEntite.getEmail())
                .roles(RoleMapper.mapToListRoleDto(userEntite.getRoles()))
                .build();
    }


}
