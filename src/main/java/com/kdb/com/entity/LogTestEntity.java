package com.kdb.com.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Table(name = "log_test")
public class LogTestEntity {

    /***
     * @author 140024
     * @implNote 자동으로 생성되는 auto increment number
     * @since 2024-06-09
     */
    @Id // 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column(name = "AI_ID")
    private Long aiID; 
    
    /***
     * @author 140024
     * @implNote 행번 
     * @since 2024-06-09
     */
    @Column(name = "EMP_NO", nullable = false)
    private String empNo; 
    
    /***
     * @author 140024
     * @implNote 인증방식
     * @since 2024-06-09
     */
    @Column(name = "TYPE", nullable = false)
    private String type; 
    
    /***
     * @author 140024
     * @implNote 성공여부
     * @since 2024-06-09
     */
    @Column(name = "RESULT", nullable = false)
    private boolean result;
    
    /***
     * @author 140024
     * @implNote 생성시간(인증시간)
     * @since 2024-06-09
     */
    @CreationTimestamp
    @Column(name = "CREATED_TIME", updatable = false)
    private LocalDateTime createdTime;  

}