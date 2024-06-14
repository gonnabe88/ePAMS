package com.kdb.com.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/***
 * @author 140024
 * @implNote Login Log Table을 객체로 관리하기 위한 entity
 * @since 2024-06-09
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Table(name = "log_login")
public class LogLoginEntity {

    /***
     * @author 140024
     * @implNote 자동으로 생성되는 auto increment number
     * @since 2024-06-09
     */
    @Id // 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column(name = "seqId")
    private Long seqId; 
    
    /***
     * @author 140024
     * @implNote 행번 
     * @since 2024-06-09
     */
    @Column(name = "empNo", nullable = false)
    private String empNo; 
    
    /***
     * @author 140024
     * @implNote 인증방식
     * @since 2024-06-09
     */
    @Column(name = "type", nullable = false)
    private String type; 
    
    /***
     * @author 140024
     * @implNote 성공여부
     * @since 2024-06-09
     */
    @Column(name = "result", nullable = false)
    private boolean result;
    
    /***
     * @author 140024
     * @implNote 생성시간(인증시간)
     * @since 2024-06-09
     */
    @Column(name = "createdTime", updatable = false)
    private LocalDateTime createdTime;  

}