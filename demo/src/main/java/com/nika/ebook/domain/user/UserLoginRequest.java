package com.nika.ebook.domain.user;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String username;
    private String password;
}
