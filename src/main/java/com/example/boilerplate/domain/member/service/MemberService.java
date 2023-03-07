package com.example.boilerplate.domain.member.service;

import com.example.boilerplate.domain.member.dto.MemberDTO.InfoResponse;

public interface MemberService {

    InfoResponse info(String memberCode);
}
