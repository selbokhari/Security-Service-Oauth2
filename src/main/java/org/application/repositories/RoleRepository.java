package org.application.repositories;


import org.application.entities.RoleEntite;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntite, Long> {

    Set<RoleEntite> findByNomIn(Set<String> noms);

}
