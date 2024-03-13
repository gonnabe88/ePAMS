package com.kdb.member;

public enum MemberRole {
    ROLE_ADMIN("ADMIN"),
    ROLE_KDB("KDB"),
    ROLE_ITO("ITO");

    private String description;

    MemberRole(String description){
        this.description = description;
    }
}