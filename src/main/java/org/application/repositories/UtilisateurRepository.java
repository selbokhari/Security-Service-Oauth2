package org.application.repositories;

import org.application.entities.UtilisateurEntite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaAuditing
public interface UtilisateurRepository extends JpaRepository<UtilisateurEntite, Long> {

    UtilisateurEntite findByLogin(String username);

    // recherche d'un utilisateur par son nom et prenom, en utilisant named params JPQL (on peut aussi utiliser, l'approche des indexs ?1 et ?2)
    @Query("SELECT u FROM UtilisateurEntite u WHERE u.nom = :nom AND u.prenom = :prenom")
    UtilisateurEntite trouverParNomEtPrenom(@Param("nom") String nom, @Param("prenom") String prenom);

}
