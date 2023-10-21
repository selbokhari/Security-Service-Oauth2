package org.application.service;

import org.application.dto.UserDto;
import org.application.entities.UserEntite;

public interface UserService {

    UserDto recupererUtilisateurParId(Long id);

    UserDto creerUtilisateur(UserDto userDto);

    UserEntite recupererUtilisateurParLogin(String login);

    void supprimerUtilisateur(Long id);

    UserEntite loadUserByUsername(String username);

}
