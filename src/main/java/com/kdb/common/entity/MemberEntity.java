package com.kdb.common.entity;

import java.io.Serializable;

import com.kdb.common.dto.MemberRole;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private boolean enabled;
    @Enumerated(EnumType.STRING)
    private MemberRole role;
    private String uuid;

    @Builder
    public MemberEntity(String username, String password, boolean enabled, MemberRole role, String uuid) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.role = role;
        this.uuid = uuid;
    }

}