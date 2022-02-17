package com.nika.ebook.service;

import com.nika.ebook.domain.Role.ERole;
import com.nika.ebook.domain.Role.Role;
import com.nika.ebook.persistance.repository.RoleRepository;
import com.nika.ebook.persistance.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role findByRequestRole(String requestRole) {
        Role role;
        switch (requestRole) {
            case "admin":
                role = findByName(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role 'ADMIN' Not Found!"));
                break;
            case "moder":
                role = findByName(ERole.ROLE_MODERATOR)
                        .orElseThrow(() -> new RuntimeException("Error: Role 'MODERATOR' Not Found!"));
                break;
            default:
                role = findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role 'USER' Not Found!"));
        }
        return role;
    }

    @Override
    public Optional<Role> findByName(ERole name) {
        return roleRepository.findByName(name);
    }
}
