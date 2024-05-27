package com.kdb.common.entity;

import java.io.Serializable;

import com.kdb.common.dto.MemberRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Table(name = "member")
public class MemberEntity implements Serializable {
    @Id
    private String username;
    
    @Column
    private String password;
    
    @Column
    private String dept;
    
    @Column
    private String team;
    
    @Column
    private boolean enabled;
    
    @Enumerated(EnumType.STRING)
    private MemberRole role;
    
    @Column
    private String uuid;
    
    @Column
    private String responsibility;

    @Builder
    public MemberEntity(String username, String password, String dept, String team, String responsibility, boolean enabled, MemberRole role, String uuid) {
        this.username = username;
        this.password = password;
        this.dept = dept;
        this.team = team;
        this.responsibility = responsibility;
        this.enabled = enabled;
        this.role = role;
        this.uuid = uuid;
    }
    
}