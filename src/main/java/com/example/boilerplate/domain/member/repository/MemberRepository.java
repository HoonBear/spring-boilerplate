package com.example.boilerplate.domain.member.repository;

import com.example.boilerplate.domain.member.model.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByMemberCode(String memberCode);
}
