package com.nika.ebook.domain.user;

import lombok.Data;

import java.util.Set;

@Data
public class UserRegisterRequest {
    private String username;
    private String email;
    private String password;
    private Set<String> roles;
}
