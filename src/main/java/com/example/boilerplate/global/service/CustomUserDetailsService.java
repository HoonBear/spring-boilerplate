package com.example.boilerplate.global.service;

import com.example.boilerplate.domain.auth.exception.AuthException;
import com.example.boilerplate.domain.member.model.Member;
import com.example.boilerplate.domain.member.repository.MemberRepository;
import com.example.boilerplate.global.exception.ErrorCode;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByEmail(username)
                               .map(this::createUserDetails)
                               .orElseThrow(() -> new AuthException(ErrorCode.CAN_NOT_AUTH_MEMBER));
    }

    private UserDetails createUserDetails(Member member) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_MEMBER");

        return new User(
            String.valueOf(member.getEmail()),
            member.getPassword(),
            Collections.singleton(grantedAuthority)
        );
    }
}
