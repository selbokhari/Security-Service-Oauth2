package org.application.repositories;


import org.application.entities.RoleEntite;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@EnableJpaAuditing
public interface RoleRepository extends CrudRepository<RoleEntite, Long> {

    Set<RoleEntite> findByNomIn(Set<String> noms);

}
