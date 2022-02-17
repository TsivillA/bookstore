package com.nika.ebook.persistance.repository;

import com.nika.ebook.domain.Role.ERole;
import com.nika.ebook.domain.Role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
