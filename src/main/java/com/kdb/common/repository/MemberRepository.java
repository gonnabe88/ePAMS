package com.kdb.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kdb.common.entity.MemberEntity;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, String> {
    Optional<MemberEntity> findByUsername(String username);
}