package com.kdb.common.entity;

import java.util.ArrayList;
import java.util.List;

import com.kdb.common.dto.BoardDTO;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
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
}