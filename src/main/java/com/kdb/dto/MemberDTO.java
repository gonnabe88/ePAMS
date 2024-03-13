package com.kdb.dto;

import com.kdb.member.MemberRole;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {
    private String username; //행번
    private String password; //패스워드 
    private MemberRole role = MemberRole.ROLE_KDB; //권한
}