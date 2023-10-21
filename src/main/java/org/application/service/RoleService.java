package org.application.service;

import org.application.dto.RoleDto;
import org.application.entities.RoleEntite;


public interface RoleService {

    RoleEntite creerRole(RoleDto roleDto);

    RoleDto recupererRoleParId(Long roleId);

}
