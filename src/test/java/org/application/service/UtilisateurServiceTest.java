package org.application.service;


import org.application.dto.RoleDto;
import org.application.dto.UtilisateurDto;
import org.application.entities.RoleEntite;
import org.application.entities.UtilisateurEntite;
import org.application.exception.BusinessException;
import org.application.repositories.UserRepository;
import org.application.service.impl.UtilisateurServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

class UtilisateurServiceTest {

    private RoleService roleService;
    private UserRepository utilisateurRepository;
    private UtilisateurService utilisateurService;
    private UtilisateurEntite utilisateurEntite;


    @BeforeEach
    void init() {
        utilisateurRepository = Mockito.mock(UserRepository.class);
        roleService = Mockito.mock(RoleService.class);
        utilisateurService = new UtilisateurServiceImpl(utilisateurRepository, roleService);
        utilisateurEntite = UtilisateurEntite.builder()
                .userId(1L)
                .login("login01")
                .email("email@gmail.fr")
                .password("password")
                .nom("nom01")
                .prenom("prenom01")
                .roles(new HashSet<>())
                .build();
    }

    @Test
    @DisplayName("Tester la récupération d'utilisateur par nom")
    void recupererUtilisateurParIdTest() {
        // init: redéfinir le comportement de DAO
        given(utilisateurRepository.findById(utilisateurEntite.getUserId())).willReturn(Optional.of(utilisateurEntite));

        // action: récupérer l'utilisateur par son id
        UtilisateurDto utilisateurDto = utilisateurService.recupererUtilisateurParId(utilisateurEntite.getUserId());

        // vérification: des données d'utilisateur retourné
        assertThat(utilisateurDto).isNotNull();
        assertThat(utilisateurDto.getUserId()).isEqualTo(utilisateurEntite.getUserId());
        assertThat(utilisateurDto.getLogin()).isEqualTo(utilisateurEntite.getLogin());
        assertThat(utilisateurDto.getPassword()).isNull();
    }

    @Test
    @DisplayName("Tester l'affectation des roles à un utilisateur")
    void affecterRolesUtilisateurTest() {
        // init:
        given(utilisateurRepository.findById(1L)).willReturn(Optional.of(utilisateurEntite));
        given(utilisateurRepository.findById(2L)).willReturn(Optional.empty());

        final Set<RoleDto> rolesDto = Set.of(new RoleDto(1L, "ADMIN", null), new RoleDto(2L, "USER", null));
        final Set<RoleEntite> rolesEntite = Set.of(new RoleEntite(1L, "ADMIN"), new RoleEntite(2L, "USER"));
        final Set<String> nomRoles = Set.of("ADMIN", "USER");

        given(roleService.recupererRolesParNoms(nomRoles)).willReturn(rolesEntite);
        given(utilisateurRepository.save(utilisateurEntite)).willReturn(utilisateurEntite);

        // action:
        UtilisateurEntite utilisateur = utilisateurService.affecterRolesUtilisateur(1L, rolesDto);

        // vérification:
        assertThat(utilisateur).isNotNull();
        assertThat(utilisateur.getRoles()).isEqualTo(rolesEntite);
        assertThatThrownBy(() -> utilisateurService.affecterRolesUtilisateur(2L, rolesDto)).isInstanceOf(BusinessException.class);
    }

}
