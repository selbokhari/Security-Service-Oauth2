package org.application.mapper;

import org.application.dto.UserDto;
import org.application.entities.UserEntite;

public class UserEntiteMapper {

    public static UserEntite mapToUserEntite(UserDto userDto) {
        return UserEntite.builder()
                .password(userDto.getPassword())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .roles(RoleMapper.mapToListRoleEntite(userDto.getRoles()))
                .build();
    }

    public static UserDto mapToUserDto(UserEntite userEntite) {
        return UserDto.builder()
                .userId(userEntite.getUserId())
                .username(userEntite.getUsername())
                .email(userEntite.getEmail())
                .roles(RoleMapper.mapToListRoleDto(userEntite.getRoles()))
                .build();
    }


}
