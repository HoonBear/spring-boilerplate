package com.example.boilerplate.domain.member.repository;

import com.example.boilerplate.domain.member.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
