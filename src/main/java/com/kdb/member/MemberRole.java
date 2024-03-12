package com.kdb.member;

public enum MemberRole {
    ROLE_ADMIN("관리자"),
    ROLE_MANAGER("매니저"),
    ROLE_MEMBER("일반사용자");

    private String description;

    MemberRole(String description){
        this.description = description;
    }
}