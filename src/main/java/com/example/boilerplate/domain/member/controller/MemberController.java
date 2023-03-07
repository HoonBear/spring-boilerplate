package com.example.boilerplate.domain.member.controller;

import com.example.boilerplate.domain.member.dto.MemberDTO.InfoResponse;
import com.example.boilerplate.domain.member.service.MemberService;
import com.example.boilerplate.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<Response> info(@AuthenticationPrincipal UserDetails user) {
        InfoResponse response = memberService.info(user.getUsername());
        return ResponseEntity.ok(Response.success(response));
    }
}
