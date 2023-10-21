package org.application.service.impl;

import org.application.dto.RoleDto;
import org.application.entities.RoleEntite;
import org.application.mapper.RoleMapper;
import org.application.repositories.RoleRepository;
import org.application.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public RoleEntite creerRole(RoleDto roleDto) {
        RoleEntite roleEntite = RoleMapper.mapToRoleEntite(roleDto);
        return roleRepository.save(roleEntite);
    }

    @Override
    public RoleDto recupererRoleParId(Long roleId) {
        return roleRepository.findById(roleId)
                .map(RoleMapper::mapToRoleDto)
                .orElse(null);
    }

}
