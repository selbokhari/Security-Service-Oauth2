package org.application.repositories;

import org.application.entities.UtilisateurEntite;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private final String LOGIN_TEST = "user01";
    private UtilisateurEntite utilisateurEntite;


    @BeforeEach
    private void initialiserUtilisateurParDefaut() {
        utilisateurEntite = UtilisateurEntite.builder()
                .login(LOGIN_TEST)
                .email("email@gmail.fr")
                .password("password")
                .nom("nom01")
                .prenom("prenom01")
                .build();
    }

    @Test
    @DisplayName("Tester la persistance d'un utilisateur")
    public void saveUserEntiteTest() {

        // init: enregistrer un utilisateur pour effectuer le test => déjà faite sur @BeforeEach

        // action: récupérer l'utilisateur créer par son login
        UtilisateurEntite nouveauUtilisateur = userRepository.save(utilisateurEntite);

        // vérification:  d'utilisateur récupéré
        Assertions.assertThat(nouveauUtilisateur).isNotNull();
        Assertions.assertThat(nouveauUtilisateur.getUserId()).isNotNull();
        Assertions.assertThat(nouveauUtilisateur.getUserId()).isGreaterThan(0);
        Assertions.assertThat(nouveauUtilisateur.getLogin()).isEqualTo(utilisateurEntite.getLogin());
        Assertions.assertThat(nouveauUtilisateur.getEmail()).isEqualTo(utilisateurEntite.getEmail());
    }

    @Test
    @DisplayName("Tester la recuperation d'un utilisateur par son login")
    public void findByLoginTest() {

        // init : persister un utilisteur dans la BDD
        UtilisateurEntite nouveauUtilisateur = userRepository.save(utilisateurEntite);

        // action: récupérer l'utilisateur depuis la BDD par son login
        UtilisateurEntite utilisateurRecupere = userRepository.findByLogin(LOGIN_TEST);

        // vérification: si c'est le bon utilisateur
        Assertions.assertThat(utilisateurRecupere).isNotNull();
        Assertions.assertThat(utilisateurRecupere.getUserId()).isNotNull();
        Assertions.assertThat(utilisateurRecupere.getLogin()).isEqualTo(LOGIN_TEST);
    }

    @Test
    @DisplayName("Tester la mise à jour d'un utilisateut")
    public void miseAjourUtilisateurTest() {
        final String nouveauEmail = "emailUpdate@gmail.fr";
        final String nouveauNom = "nouveau nom";
        final String nouveauPrenom = "nouveau prenom";
        final String noveauLogin = "nouveau login";

        // init: persister un utilisateur
        UtilisateurEntite nouveauUtilisateur = userRepository.save(utilisateurEntite);
        nouveauUtilisateur.setEmail(nouveauEmail);
        nouveauUtilisateur.setNom(nouveauNom);
        nouveauUtilisateur.setPrenom(nouveauPrenom);
        nouveauUtilisateur.setLogin(noveauLogin);
        // action: modifier l'utilisateur
        UtilisateurEntite updatedUser = userRepository.save(nouveauUtilisateur);

        // vérification: si les modifications ont ete prise en compte
        Assertions.assertThat(updatedUser.getUserId()).isEqualTo(nouveauUtilisateur.getUserId());
        Assertions.assertThat(updatedUser.getEmail()).isEqualTo(nouveauEmail);
        Assertions.assertThat(updatedUser.getNom()).isEqualTo(nouveauNom);
        Assertions.assertThat(updatedUser.getPrenom()).isEqualTo(nouveauPrenom);
        Assertions.assertThat(updatedUser.getLogin()).isEqualTo(noveauLogin);
    }

}
