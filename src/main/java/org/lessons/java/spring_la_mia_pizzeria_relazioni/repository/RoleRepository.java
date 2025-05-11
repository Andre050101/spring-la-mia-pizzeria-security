package org.lessons.java.spring_la_mia_pizzeria_relazioni.repository;

import java.util.Optional;

import org.lessons.java.spring_la_mia_pizzeria_relazioni.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
