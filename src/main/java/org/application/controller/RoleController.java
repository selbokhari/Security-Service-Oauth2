package org.application.controller;

import org.application.dto.RoleDto;
import org.application.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class RoleController {

    private final RoleService roleServiceImpl;

    @GetMapping("/roleId")
    public RoleDto recupererParRoleId(@PathVariable(name="roleId") Long id) {
        return roleServiceImpl.recupererRoleParId(id);
    }

}
