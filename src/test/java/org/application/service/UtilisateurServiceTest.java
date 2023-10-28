package org.application.service;


import org.application.dto.RoleDto;
import org.application.dto.UtilisateurDto;
import org.application.entities.RoleEntite;
import org.application.entities.UtilisateurEntite;
import org.application.exception.BusinessException;
import org.application.repositories.UtilisateurRepository;
import org.application.service.impl.UtilisateurServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UtilisateurServiceTest {

    @Mock
    private RoleService roleService;

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @InjectMocks
    private UtilisateurServiceImpl utilisateurServiceImpl;

    private UtilisateurEntite utilisateurEntite;
    private UtilisateurDto utilisateurDto;

    @BeforeEach
    void init() {
        utilisateurEntite = UtilisateurEntite.builder()
                .userId(1L)
                .login("login01")
                .email("email@gmail.fr")
                .password("password")
                .nom("nom01")
                .prenom("prenom01")
                .roles(new HashSet<>())
                .build();

        utilisateurDto = UtilisateurDto.builder()
                .userId(1L)
                .login("login01")
                .email("email@gmail.fr")
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
        UtilisateurDto utilisateurDto = utilisateurServiceImpl.recupererUtilisateurParId(utilisateurEntite.getUserId());

        // vérification: des données d'utilisateur retourné
        assertThat(utilisateurDto).isNotNull();
        assertThat(utilisateurDto.getUserId()).isEqualTo(utilisateurEntite.getUserId());
        assertThat(utilisateurDto.getLogin()).isEqualTo(utilisateurEntite.getLogin());
        assertThat(utilisateurDto.getPassword()).isNull();
    }

    @Test
    @DisplayName("Tester l'affectation des roles à un utilisateur")
    void affecterRolesUtilisateurTest() {
        // init: redéfinir le retour des fonctions
        given(utilisateurRepository.findById(1L)).willReturn(Optional.of(utilisateurEntite));
        given(utilisateurRepository.findById(2L)).willReturn(Optional.empty());

        final Set<RoleDto> rolesDto = Set.of(new RoleDto(1L, "ADMIN", null), new RoleDto(2L, "USER", null));
        final Set<RoleEntite> rolesEntite = Set.of(new RoleEntite(1L, "ADMIN"), new RoleEntite(2L, "USER"));
        final Set<String> nomRoles = Set.of("ADMIN", "USER");

        given(roleService.recupererRolesParNoms(nomRoles)).willReturn(rolesEntite);
        given(utilisateurRepository.save(utilisateurEntite)).willReturn(utilisateurEntite);

        // action: affecter les roles à l'utilisateur
        UtilisateurEntite utilisateur = utilisateurServiceImpl.affecterRolesUtilisateur(1L, rolesDto);

        // vérification: si les roles ont été bien affectés à l'utilisateur en question.
        assertThat(utilisateur).isNotNull();
        assertThat(utilisateur.getRoles()).isEqualTo(rolesEntite);
        assertThatThrownBy(() -> utilisateurServiceImpl.affecterRolesUtilisateur(2L, rolesDto)).isInstanceOf(BusinessException.class);
        // on peut aussi utiliser : Assertions.assertThrows(BusinessException.class, () -> utilisateurServiceImpl.affecterRolesUtilisateur(2L, rolesDto));
        // verify(utilisateurRepository, never()).save(utilisateurEntite);
    }

    @Test
    @DisplayName("Tester la mise à jour d'un utilisateur")
    void mettreAjourUtilisateurTest() {
        // init: initialiser le jeu de données de test
        UtilisateurDto utilisateurDto = UtilisateurDto.builder()
                .userId(1L)
                .login("Login MAJ")
                .email("emailMaj@gmaiL.fr")
                .nom("nom MAJ")
                .prenom("prenom MAJ")
                .roles(new HashSet<>())
                .build();

        UtilisateurEntite utilisateurMaj = UtilisateurEntite.builder()
                .userId(1L)
                .login("Login MAJ")
                .email("emailMaj@gmaiL.fr")
                .nom("nom MAJ")
                .prenom("prenom MAJ")
                .roles(new HashSet<>())
                .build();

        given(utilisateurRepository.findById(utilisateurDto.getUserId())).willReturn(Optional.of(utilisateurEntite));
        given(utilisateurRepository.save(utilisateurMaj)).willReturn(utilisateurMaj);

        // action: mettre à jour l'utilisateur
        UtilisateurDto utilisateur = utilisateurServiceImpl.mettreAjourUtilisateur(utilisateurDto);

        // vérification: si l'utilisateur a été mise à jour
        assertThat(utilisateur).isEqualTo(utilisateurDto);
    }

    @Test
    @DisplayName("Tester la récupération de tous les utilisateurs")
    void recupererTousLesUtilisateursTest() {
        // init:
        UtilisateurDto utilisateurDto02 = UtilisateurDto.builder()
                .userId(2L)
                .login("Login MAJ")
                .email("emailMaj@gmaiL.fr")
                .nom("nom MAJ")
                .prenom("prenom MAJ")
                .roles(new HashSet<>())
                .build();

        UtilisateurEntite utilisateurEntite02 = UtilisateurEntite.builder()
                .userId(2L)
                .login("Login MAJ")
                .email("emailMaj@gmaiL.fr")
                .nom("nom MAJ")
                .prenom("prenom MAJ")
                .roles(new HashSet<>())
                .build();

        given(utilisateurRepository.findAll()).willReturn(List.of(utilisateurEntite, utilisateurEntite02));

        // action: récupérer tous les utilisateurs
        List<UtilisateurDto> utilisateurs = utilisateurServiceImpl.recupererTousLesUtilisateurs();

        // vérification: si les utilisateurs retournés sont correctes
        assertThat(utilisateurs).isNotNull();
        assertThat(utilisateurs.size()).isEqualTo(2L);
        assertThat(utilisateurs).isEqualTo(List.of(utilisateurDto, utilisateurDto02));
    }

    @Test
    @DisplayName("Tester la récupération de tous les utilisateurs dont la table est vide")
    void recupererTousLesUtilisateurs_SansUtilisateursDansLaBDDTest() {
        // init: redéfinir la récupération des utilisateurs
        given(utilisateurRepository.findAll()).willReturn(Collections.emptyList());

        // action: récupérer tous les utilisateurs
        List<UtilisateurDto> utilisateurs = utilisateurServiceImpl.recupererTousLesUtilisateurs();

        // vérification: si les utilisateurs retournés sont correctes
        assertThat(utilisateurs.size()).isZero();
        assertThat(utilisateurs).isEqualTo(Collections.emptyList());
    }

    @Test
    @DisplayName("Tester la suppression d'un utilisateur")
    void supprimerUtilisateurTest() {
        // init: redéfinir la méthode de la suppression d'un utilisateur
        willDoNothing().given(utilisateurRepository).delete(utilisateurEntite);
        given(utilisateurRepository.findById(1L)).willReturn(Optional.of(utilisateurEntite));

        // action: récupérer tous les utilisateurs
        utilisateurServiceImpl.supprimerUtilisateur(1L);

        // vérification:
        verify(utilisateurRepository, times(1)).delete(utilisateurEntite);
    }

}
