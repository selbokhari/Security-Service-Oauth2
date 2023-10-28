package org.application.service;

import org.application.dto.RoleDto;
import org.application.dto.UtilisateurDto;
import org.application.entities.UtilisateurEntite;

import java.util.Set;

public interface UtilisateurService {

    UtilisateurDto recupererUtilisateurParId(Long id);

    UtilisateurDto creerUtilisateur(UtilisateurDto utilisateurDto);

    UtilisateurDto mettreAjourUtilisateur(UtilisateurDto utilisateurDto);

    UtilisateurEntite affecterRolesUtilisateur(Long id, Set<RoleDto> roles);

    UtilisateurEntite recupererUtilisateurParLogin(String login);

    void supprimerUtilisateur(Long id);

}
