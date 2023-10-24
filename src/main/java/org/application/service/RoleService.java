package org.application.service;

import org.application.dto.RoleDto;
import org.application.entities.RoleEntite;

import java.util.Set;


public interface RoleService {

    RoleDto creerRole(RoleDto roleDto);

    RoleDto recupererRoleParId(Long roleId);

    Set<RoleEntite> recupererRolesParNoms(Set<String> nomRoles);

}
