package org.application.repositories;

import org.application.entities.UserEntite;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntite, Long> {

    UserEntite findByUsername(String username);

}
