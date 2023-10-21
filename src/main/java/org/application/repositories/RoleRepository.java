package org.application.repositories;


import org.application.entities.RoleEntite;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntite, Long> {

}
