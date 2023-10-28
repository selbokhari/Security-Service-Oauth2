package org.application.service.impl;

import org.application.dto.RoleDto;
import org.application.dto.UtilisateurDto;
import org.application.entities.RoleEntite;
import org.application.entities.UtilisateurEntite;
import org.application.exception.BusinessException.Raison;
import org.application.exception.BusinessException;
import org.application.mapper.UserEntiteMapper;
import org.application.repositories.UserRepository;
import org.application.service.RoleService;
import org.application.service.UtilisateurService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    @Override
    public UtilisateurDto recupererUtilisateurParId(Long id) {
        return userRepository.findById(id)
                .map(UserEntiteMapper::mapToUserDto)
                .orElse(null);
    }

    @Override
    public UtilisateurDto creerUtilisateur(UtilisateurDto utilisateurDto) {
        return Optional.of(UserEntiteMapper.mapToUserEntite(utilisateurDto))
                .map(userRepository::save)
                .map(UserEntiteMapper::mapToUserDto)
                .orElse(null);
    }

    @Override
    public UtilisateurDto mettreAjourUtilisateur(UtilisateurDto utilisateurDto) {
        return Optional.of(UserEntiteMapper.mapToUserEntite(utilisateurDto))
                .map(userRepository::save)
                .map(UserEntiteMapper::mapToUserDto)
                .orElse(null);
    }

    @Override
    public void affecterRolesUtilisateur(Long id, Set<RoleDto> roles) {
        UtilisateurEntite utilisateur = userRepository.findById(id).orElseThrow(() -> new BusinessException(Raison.UTILISATEUR_NON_TROUVEE));
        Set<String> nomeRoles = roles.stream().map(RoleDto::getNom).collect(Collectors.toSet());
        Set<RoleEntite> rolesEntites =  roleService.recupererRolesParNoms(nomeRoles);
        utilisateur.getRoles().addAll(rolesEntites);
        userRepository.save(utilisateur);
    }

    @Override
    public UtilisateurEntite recupererUtilisateurParLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void supprimerUtilisateur(Long id) {
        userRepository.findById(id)
                .ifPresent(userRepository::delete);
    }

    public UtilisateurEntite loadUserByUsername(String username) {
        return userRepository.findByLogin(username);
    }

}
