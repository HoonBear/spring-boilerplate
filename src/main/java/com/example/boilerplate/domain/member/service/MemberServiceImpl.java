package com.example.boilerplate.domain.member.service;

import com.example.boilerplate.domain.member.dto.MemberDTO.InfoResponse;
import com.example.boilerplate.domain.member.exception.MemberException;
import com.example.boilerplate.domain.member.model.Member;
import com.example.boilerplate.domain.member.repository.MemberRepository;
import com.example.boilerplate.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public InfoResponse info(String memberCode) {
        Member member = memberRepository.findByMemberCode(memberCode)
                                        .orElseThrow(() -> new MemberException(ErrorCode.NOT_EXIST_MEMBER));

        return InfoResponse.from(member);
    }
}
