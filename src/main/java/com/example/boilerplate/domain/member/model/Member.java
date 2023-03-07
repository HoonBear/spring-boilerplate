package com.example.boilerplate.domain.member.model;

import com.example.boilerplate.domain.member.enumeration.Social;
import com.example.boilerplate.domain.member.enumeration.Status;
import com.example.boilerplate.global.enumeration.Yn;
import com.example.boilerplate.global.model.BaseTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Builder
@Table(name = "tb_member")
public class Member extends BaseTime implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    @Comment("회원 식별값")
    private Long id;

    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
        name = "tb_member_role",
        joinColumns = {@JoinColumn(name = "member_id", referencedColumnName = "member_id")},
        inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id")})
    private Collection<Role> roleList = new ArrayList<>();

    @Column(name = "member_code", nullable = false, length = 40)
    @Comment("회원 코드")
    private String memberCode;

    @Enumerated(EnumType.STRING)
    @Comment("소셜로그인 종류")
    private Social social;

    @Column(name = "open_id", length = 10)
    @Comment("회원 고유번호 ")
    private String openId;

    @Column(length = 150)
    @Comment("이메일")
    private String email;

    @Comment("비밀번호")
    private String password;

    @Column(nullable = false, length = 15)
    @Comment("전화번호")
    private String hp;

    @Column(nullable = false, length = 50)
    @Comment("고객명")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(length = 2, nullable = false)
    @Comment("탈퇴 여부")
    private Yn withdraw;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    @Comment("상태 NORMAL: 정상, LIMITS: 제약, STOP: 정지, SLEEP: 휴면")
    private Status status;

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role role : roleList) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
