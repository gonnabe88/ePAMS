package com.kdb.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kdb.dto.MemberDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor

public class MemberRepository {
    private final SqlSessionTemplate sql;

    public MemberDTO login(MemberDTO memberDTO) {
    	System.out.println("REP" + memberDTO.toString());
        return sql.selectOne("Member.login", memberDTO);
    }
    public MemberDTO findByUserId(String userId) {
        return sql.selectOne("Member.findByUserId", userId);
    }
    public MemberDTO auth(MemberDTO memberDTO) {
        return sql.selectOne("Member.auth", memberDTO);
    }
}
