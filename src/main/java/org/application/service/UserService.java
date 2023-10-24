package org.application.service;

import org.application.dto.RoleDto;
import org.application.dto.UserDto;
import org.application.entities.UserEntite;

import java.util.Set;

public interface UserService {

    UserDto recupererUtilisateurParId(Long id);

    UserDto creerUtilisateur(UserDto userDto);

    UserDto mettreAjourUtilisateur(UserDto userDto);

    void affecterRolesUtilisateur(Long id, Set<RoleDto> roles);

    UserEntite recupererUtilisateurParLogin(String login);

    void supprimerUtilisateur(Long id);

    UserEntite loadUserByUsername(String username);

}
