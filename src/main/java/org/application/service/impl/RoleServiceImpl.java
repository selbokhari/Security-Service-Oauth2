package org.application.service.impl;

import org.application.dto.RoleDto;
import org.application.entities.RoleEntite;
import org.application.mapper.RoleMapper;
import org.application.repositories.RoleRepository;
import org.application.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public RoleDto creerRole(RoleDto roleDto) {
        RoleEntite roleEntite = RoleMapper.mapToRoleEntite(roleDto);
        RoleEntite roleCree = roleRepository.save(roleEntite);
        return RoleMapper.mapToRoleDto(roleCree);
    }

    @Override
    public RoleDto recupererRoleParId(Long roleId) {
        return roleRepository.findById(roleId)
                .map(RoleMapper::mapToRoleDto)
                .orElse(null);
    }

    @Override
    public Set<RoleEntite> recupererRolesParNoms(Set<String> nomRoles) {
        return roleRepository.findByNomIn(nomRoles);
    }

}
