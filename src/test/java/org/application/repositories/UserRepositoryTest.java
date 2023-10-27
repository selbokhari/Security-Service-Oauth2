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


    @Test
    @DisplayName("Tester la recuperation d'un utilisateur par son login")
    public void saveUserEntiteTest() {

        // init: enregistrer un utilisateur pour effectuer le test => déjà faite sur @BeforeEach

        // Action: récupérer l'utilisateur créer par son login
        UtilisateurEntite nouveauUtilisateur = userRepository.save(utilisateurEntite);

        // Vérification: vérifier l'utilisateur récupéré
        Assertions.assertThat(nouveauUtilisateur).isNotNull();
        Assertions.assertThat(nouveauUtilisateur.getUserId()).isNotNull();
        Assertions.assertThat(nouveauUtilisateur.getLogin()).isEqualTo(utilisateurEntite.getLogin());
        Assertions.assertThat(nouveauUtilisateur.getEmail()).isEqualTo(utilisateurEntite.getEmail());
    }

    @Test
    @DisplayName("Tester la recuperation d'un utilisateur par son login")
    public void findByLoginTest() {

        // init : persister un utilisteur dans la BDD
        UtilisateurEntite nouveauUtilisateur = userRepository.save(utilisateurEntite);

        // Action: récupérer l'utilisateur depuis la BDD par son login
        UtilisateurEntite utilisateurRecupere = userRepository.findByLogin(LOGIN_TEST);

        // Vérification: vérifier si c'est le bon utilisateur
        Assertions.assertThat(utilisateurRecupere).isNotNull();
        Assertions.assertThat(utilisateurRecupere.getUserId()).isNotNull();
        Assertions.assertThat(utilisateurRecupere.getLogin()).isEqualTo(LOGIN_TEST);
    }

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

}
