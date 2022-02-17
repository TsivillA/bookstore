package com.nika.ebook.persistance.service;

import com.nika.ebook.domain.Role.ERole;
import com.nika.ebook.domain.Role.Role;

import java.util.Optional;

public interface RoleService {
    Role findByRequestRole(String requestRole);
    Optional<Role> findByName(ERole name);
}
