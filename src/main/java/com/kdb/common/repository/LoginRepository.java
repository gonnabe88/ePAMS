package com.kdb.common.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kdb.common.dto.MemberDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LoginRepository {
    private final SqlSessionTemplate sql;

    public MemberDTO login(MemberDTO memberDTO) {
        return sql.selectOne("Member.login", memberDTO);
    }
    public MemberDTO findByUserId(String userId) {
        return sql.selectOne("Member.findByUserId", userId);
    }
    public MemberDTO auth(MemberDTO memberDTO) {
        return sql.selectOne("Member.auth", memberDTO);
    }
    public String findUuid(String username) {
        return sql.selectOne("Member.findUuid", username);
    }    
    public void updateUuid(MemberDTO memberDTO) {
    	sql.update("Member.updateUuid", memberDTO);
    }
}
