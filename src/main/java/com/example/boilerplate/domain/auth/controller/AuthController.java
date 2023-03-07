package com.example.boilerplate.domain.auth.controller;

import com.example.boilerplate.domain.auth.dto.AuthDTO.CommonLoginRequest;
import com.example.boilerplate.domain.auth.dto.AuthDTO.CommonSignupRequest;
import com.example.boilerplate.domain.auth.service.AuthService;
import com.example.boilerplate.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Response> signup(@RequestBody CommonSignupRequest request) {
        authService.commonSignup(request);
        return ResponseEntity.ok(Response.success());
    }

    @PostMapping("/common")
    public ResponseEntity<Response> login(@RequestBody CommonLoginRequest request) {
        return ResponseEntity.ok(Response.success(authService.commonLogin(request)));
    }
}
