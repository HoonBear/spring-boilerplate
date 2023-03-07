package com.example.boilerplate.domain.auth.service;

import com.example.boilerplate.domain.auth.dto.AuthDTO.CommonLoginRequest;
import com.example.boilerplate.domain.auth.dto.AuthDTO.CommonSignupRequest;
import com.example.boilerplate.domain.auth.dto.AuthDTO.LoginResponse;

public interface AuthService {

    void commonSignup(CommonSignupRequest request);

    LoginResponse commonLogin(CommonLoginRequest request);
}
