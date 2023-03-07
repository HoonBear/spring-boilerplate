package com.example.boilerplate.mocking;

import com.example.boilerplate.domain.auth.dto.AuthDTO.CommonSignupRequest;
import com.example.boilerplate.domain.member.enumeration.Status;
import com.example.boilerplate.domain.member.model.Member;
import com.example.boilerplate.domain.member.model.Role;
import com.example.boilerplate.domain.member.repository.MemberRepository;
import com.example.boilerplate.domain.member.repository.RoleRepository;
import com.example.boilerplate.global.enumeration.Yn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MemberMocking {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public void createMocking() {
        roleRepository.save(Role.builder().id(1).name("ROLE_MEMBER").build());

        CommonSignupRequest memberData = CommonSignupRequest.builder()
                                                            .email("test@google.com")
                                                            .password("비밀번호")
                                                            .hp("01012345678")
                                                            .name("hong gil-dong")
                                                            .build();

        Member member = memberData.toMember(passwordEncoder);
        memberRepository.save(member);


        memberRepository.save(
            Member.builder()
                  .memberCode("user")
                  .email("witMockUser@gmail.com")
                  .password(passwordEncoder.encode("password"))
                  .hp("01012345679")
                  .name("withMockUser")
                  .withdraw(Yn.N)
                  .status(Status.NORMAL)
                  .build()
        );
    }
}
