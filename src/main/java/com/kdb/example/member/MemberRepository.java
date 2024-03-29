package com.kdb.example.member;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kdb.common.entity.MemberEntity;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByUsername(String username);
}