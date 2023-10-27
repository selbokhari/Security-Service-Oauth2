package org.application.repositories;

import org.application.entities.UtilisateurEntite;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UtilisateurEntite, Long> {

    UtilisateurEntite findByLogin(String username);

}
