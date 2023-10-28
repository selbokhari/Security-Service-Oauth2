package org.application.service;


import org.application.dto.UtilisateurDto;
import org.application.entities.UtilisateurEntite;
import org.application.repositories.UserRepository;
import org.application.service.impl.UtilisateurServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

class UtilisateurServiceTest {

    private RoleService roleService;
    private UserRepository userRepository;
    private UtilisateurService utilisateurService;
    private UtilisateurEntite utilisateurEntite;

    @BeforeEach
    void init() {
        userRepository = Mockito.mock(UserRepository.class);
        roleService = Mockito.mock(RoleService.class);
        utilisateurService = new UtilisateurServiceImpl(userRepository, roleService);
        utilisateurEntite = UtilisateurEntite.builder()
                .userId(1L)
                .login("login01")
                .email("email@gmail.fr")
                .password("password")
                .nom("nom01")
                .prenom("prenom01")
                .build();
    }

    @Test
    @DisplayName("Tester la récupération d'utilisateur par nom")
    void recupererUtilisateurParIdTest() {
        // init: redéfinir le comportement de DAO
        given(userRepository.findById(utilisateurEntite.getUserId())).willReturn(Optional.of(utilisateurEntite));

        // action: récupérer l'utilisateur par son id
        UtilisateurDto utilisateurDto = utilisateurService.recupererUtilisateurParId(utilisateurEntite.getUserId());

        // vérification: des données d'utilisateur retourné
        assertThat(utilisateurDto).isNotNull();
        assertThat(utilisateurDto.getUserId()).isEqualTo(utilisateurEntite.getUserId());
        assertThat(utilisateurDto.getLogin()).isEqualTo(utilisateurEntite.getLogin());
        assertThat(utilisateurDto.getPassword()).isNull();
    }
}
