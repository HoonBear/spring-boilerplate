package com.example.boilerplate.domain.auth.service;

import com.example.boilerplate.domain.auth.dto.AuthDTO.CommonLoginRequest;
import com.example.boilerplate.domain.auth.dto.AuthDTO.CommonSignupRequest;
import com.example.boilerplate.domain.auth.dto.AuthDTO.LoginResponse;
import com.example.boilerplate.domain.auth.exception.AuthException;
import com.example.boilerplate.domain.member.model.Member;
import com.example.boilerplate.domain.member.repository.MemberRepository;
import com.example.boilerplate.global.exception.ErrorCode;
import com.example.boilerplate.global.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManagerBuilder managerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    @Transactional
    public void commonSignup(CommonSignupRequest request) {
        memberRepository.findByEmail(request.getEmail()).ifPresent((member -> {
            throw new AuthException(ErrorCode.PRESENT_MEMBER);
        }));

        memberRepository.save(request.toMember(passwordEncoder));
    }

    @Override
    public LoginResponse commonLogin(CommonLoginRequest request) {
        try {
            Member member = memberRepository.findByEmail(request.getEmail())
                                            .orElseThrow(() -> new AuthException(ErrorCode.CAN_NOT_AUTH_MEMBER));

            UsernamePasswordAuthenticationToken authenticationToken = request.toAuthentication();

            Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);

            return LoginResponse.from(member.getMemberCode(), authentication, jwtProvider);
        } catch (BadCredentialsException exception) {
            throw new AuthException(ErrorCode.CAN_NOT_AUTH_MEMBER);
        }
    }
}
