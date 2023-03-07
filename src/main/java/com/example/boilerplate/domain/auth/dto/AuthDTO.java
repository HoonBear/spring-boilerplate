package com.example.boilerplate.domain.auth.dto;

import com.example.boilerplate.domain.member.enumeration.Social;
import com.example.boilerplate.domain.member.enumeration.Status;
import com.example.boilerplate.domain.member.model.Member;
import com.example.boilerplate.domain.member.model.Role;
import com.example.boilerplate.global.enumeration.Yn;
import com.example.boilerplate.global.security.JwtProvider;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthDTO {

    private AuthDTO() throws IllegalStateException {
        throw new IllegalStateException();
    }

    /**************************************************************** Request ****************************************************************/

    @Getter
    @Builder
    public static class CommonSignupRequest {

        private String email;
        private String password;
        private String hp;
        private String name;

        public Member toMember(PasswordEncoder passwordEncoder) {
            Role role = Role.builder()
                            .id(1)
                            .name("ROLE_MEMBER")
                            .build();

            return Member.builder()
                         .roleList(List.of(role))
                         .memberCode(UUID.randomUUID().toString())
                         .email(email)
                         .password(passwordEncoder.encode(password))
                         .hp(hp)
                         .name(name)
                         .withdraw(Yn.N)
                         .status(Status.NORMAL)
                         .build();
        }
    }

    @Getter
    @Builder
    public static class SocialSignupRequest {

        private Social social;
        private String openId;
        private String hp;
        private String name;
    }

    @Getter
    @Builder
    public static class CommonLoginRequest {

        private String email;
        private String password;

        public UsernamePasswordAuthenticationToken toAuthentication() {
            return new UsernamePasswordAuthenticationToken(email, password);
        }
    }

    /**************************************************************** Response ****************************************************************/

    @Getter
    @Builder
    public static class LoginResponse {

        private String accessToken;
        private String refreshToken;
        private Date accessExpiredTime;
        private Date refreshExpiredTime;

        public static LoginResponse from(String memberCode, Authentication authentication, JwtProvider jwtProvider) {
            String accessToken = jwtProvider.createJwtAccessToken(memberCode, "/", authentication);
            String refreshToken = jwtProvider.createJwtRefreshToken();

            return LoginResponse.builder()
                                .accessToken(accessToken)
                                .refreshToken(refreshToken)
                                .accessExpiredTime(jwtProvider.getExpiredTime(accessToken))
                                .refreshExpiredTime(jwtProvider.getExpiredTime(refreshToken))
                                .build();
        }
    }

}
