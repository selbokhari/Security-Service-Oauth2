package org.application.service.impl;

import org.application.dto.RoleDto;
import org.application.dto.UserDto;
import org.application.entities.RoleEntite;
import org.application.entities.UserEntite;
import org.application.exception.BusinessException.Raison;
import org.application.exception.BusinessException;
import org.application.mapper.UserEntiteMapper;
import org.application.repositories.UserRepository;
import org.application.service.RoleService;
import org.application.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    @Override
    public UserDto recupererUtilisateurParId(Long id) {
        return userRepository.findById(id)
                .map(UserEntiteMapper::mapToUserDto)
                .orElse(null);
    }

    @Override
    public UserDto creerUtilisateur(UserDto userDto) {
        return Optional.of(UserEntiteMapper.mapToUserEntite(userDto))
                .map(userRepository::save)
                .map(UserEntiteMapper::mapToUserDto)
                .orElse(null);
    }

    @Override
    public UserDto mettreAjourUtilisateur(UserDto userDto) {
        return Optional.of(UserEntiteMapper.mapToUserEntite(userDto))
                .map(userRepository::save)
                .map(UserEntiteMapper::mapToUserDto)
                .orElse(null);
    }

    @Override
    public void affecterRolesUtilisateur(Long id, Set<RoleDto> roles) {
        UserEntite utilisateur = userRepository.findById(id).orElseThrow(() -> new BusinessException(Raison.UTILISATEUR_NON_TROUVEE));
        Set<String> nomeRoles = roles.stream().map(RoleDto::getNom).collect(Collectors.toSet());
        Set<RoleEntite> rolesEntites =  roleService.recupererRolesParNoms(nomeRoles);
        utilisateur.getRoles().addAll(rolesEntites);
        userRepository.save(utilisateur);
    }

    @Override
    public UserEntite recupererUtilisateurParLogin(String login) {
        return userRepository.findByUsername(login);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void supprimerUtilisateur(Long id) {
        userRepository.findById(id)
                .ifPresent(userRepository::delete);
    }

    public UserEntite loadUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
