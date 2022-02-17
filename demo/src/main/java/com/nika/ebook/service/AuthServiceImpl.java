package com.nika.ebook.service;

import com.nika.ebook.api.utility.JwtUtils;
import com.nika.ebook.domain.Role.ERole;
import com.nika.ebook.domain.Role.Role;
import com.nika.ebook.domain.jwt.JwtResponse;
import com.nika.ebook.domain.user.User;
import com.nika.ebook.domain.user.UserLoginRequest;
import com.nika.ebook.domain.user.UserRegisterRequest;
import com.nika.ebook.persistance.repository.RoleRepository;
import com.nika.ebook.persistance.repository.UserRepository;
import com.nika.ebook.persistance.service.AuthService;
import com.nika.ebook.persistance.service.RoleService;
import com.nika.ebook.persistance.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public JwtResponse loginUser(UserLoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateToken(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            return new JwtResponse(
                    jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles);
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public void registerUser(UserRegisterRequest signUpRequest) {
        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()));

        Set<String> requestRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        if(requestRoles == null) {
            Role role = roleService.findByName(ERole.ROLE_USER).orElse(null);
            if(Objects.isNull(role))
                throw new RuntimeException("Error: Role 'USER' Not Found!");
            roles.add(role);
        }
        else {
            requestRoles.forEach(r -> {
                Role role = roleService.findByRequestRole(r);
                roles.add(role);
            });
        }
        user.setRoles(roles);
        userService.save(user);
    }
}
