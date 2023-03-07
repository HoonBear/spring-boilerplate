package com.example.boilerplate.domain.member.dto;

import com.example.boilerplate.domain.member.enumeration.Status;
import com.example.boilerplate.domain.member.model.Member;
import com.example.boilerplate.global.enumeration.Yn;
import lombok.Builder;
import lombok.Getter;

public class MemberDTO {

    private MemberDTO() throws IllegalStateException {
        throw new IllegalStateException();
    }

    /**************************************************************** Request ****************************************************************/
    /**************************************************************** Response ****************************************************************/

    @Getter
    @Builder
    public static class InfoResponse {
        private String email;
        private String hp;
        private String name;
        private boolean withdraw;
        private Status status;

        public static InfoResponse from(Member member) {
            return InfoResponse.builder()
                               .email(member.getEmail())
                               .hp(member.getHp())
                               .name(member.getName())
                               .withdraw(Yn.toBoolean(member.getWithdraw()))
                               .status(member.getStatus())
                               .build();
        }
    }
}
