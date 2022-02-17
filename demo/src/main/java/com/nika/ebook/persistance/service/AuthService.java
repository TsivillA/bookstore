package com.nika.ebook.persistance.service;

import com.nika.ebook.domain.jwt.JwtResponse;
import com.nika.ebook.domain.user.UserLoginRequest;
import com.nika.ebook.domain.user.UserRegisterRequest;

public interface AuthService {
    JwtResponse loginUser(UserLoginRequest request);
    void registerUser(UserRegisterRequest request);
}
