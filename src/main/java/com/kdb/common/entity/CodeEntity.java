package com.kdb.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Table(name = "common_code")
public class CodeEntity extends BaseEntity {
    @Id // 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;
    
    @Column(length = 20, nullable = false)
    private String CD;
    
    @Column(length = 500, nullable = false)
    private String CD_NM;

    @Column(length = 20, nullable = false) // 크기 20, not null
    private String CD_TYPE;
    
    @Column(length = 100, nullable = false) // 크기 20, not null
    private String CD_HTML;
    
    @Builder
    public CodeEntity(String CD, String CD_NM, String CD_TYPE, String CD_HTML) {
        this.CD = CD;
        this.CD_NM = CD_NM;
        this.CD_TYPE = CD_TYPE;
        this.CD_HTML = CD_HTML;
    }
    
}