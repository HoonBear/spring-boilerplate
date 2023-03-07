package com.example.boilerplate.mocking;

import com.example.boilerplate.domain.auth.dto.AuthDTO.CommonLoginRequest;
import com.example.boilerplate.domain.auth.dto.AuthDTO.LoginResponse;
import com.example.boilerplate.domain.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthMocking {
    @Autowired
    AuthService authService;

    public LoginResponse login() {
        return authService.commonLogin(
            CommonLoginRequest.builder()
                              .email("withMockUser@gmail.com")
                              .password("password")
                              .build()
        );
    }
}
