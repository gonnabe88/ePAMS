package epams.com.admin.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/***
 * @author 140024
 * @implNote Login Log Table을 객체로 관리하기 위한 entity
 * @since 2024-06-09
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "THURXE_CLOGNL")
@Comment("인사_외부근태 로그인로그")
public class LogLoginEntity {

    /***
     * @author 140024
     * @implNote 자동으로 생성되는 auto increment number
     * @since 2024-06-09
     */
    @Id // 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column(name = "LOGIN_LOG_SNO")
    @Comment("로그인로그일련번호")
    private Long SEQ_ID; 
    
    /***
     * @author 140024
     * @implNote 행번 
     * @since 2024-06-09
     */
    @Column(name = "SNO", nullable = false)
    @Comment("사원번호")
    private String EMP_NO; 
    
    /***
     * @author 140024
     * @implNote 인증방식
     * @since 2024-06-09
     */
    @Column(name = "LOGIN_TYPE", nullable = false)
    @Comment("로그인타입")
    private String LOGIN_TYPE; 
    
    /***
     * @author 140024
     * @implNote 성공여부
     * @since 2024-06-09
     */
    @Column(name = "LOGIN_RESULT", nullable = false)
    @Comment("로그인결과")
    private boolean LOGIN_RESULT;
    
    /***
     * @author 140024
     * @implNote 생성시간(인증시간)
     * @since 2024-06-09
     */
    @Column(name = "CREATED_TIME", updatable = false)
    @Comment("생성시간")
    private LocalDateTime CREATED_TIME;  

}