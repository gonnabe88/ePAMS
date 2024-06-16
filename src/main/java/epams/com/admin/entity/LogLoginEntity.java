package epams.com.admin.entity;

import java.time.LocalDateTime;

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
@Table(name = "com_log_login")
public class LogLoginEntity {

    /***
     * @author 140024
     * @implNote 자동으로 생성되는 auto increment number
     * @since 2024-06-09
     */
    @Id // 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column(name = "SEQ_ID")
    private Long SEQ_ID; 
    
    /***
     * @author 140024
     * @implNote 행번 
     * @since 2024-06-09
     */
    @Column(name = "EMP_NO", nullable = false)
    private String EMP_NO; 
    
    /***
     * @author 140024
     * @implNote 인증방식
     * @since 2024-06-09
     */
    @Column(name = "LOGIN_TYPE", nullable = false)
    private String LOGIN_TYPE; 
    
    /***
     * @author 140024
     * @implNote 성공여부
     * @since 2024-06-09
     */
    @Column(name = "LOGIN_RESULT", nullable = false)
    private boolean LOGIN_RESULT;
    
    /***
     * @author 140024
     * @implNote 생성시간(인증시간)
     * @since 2024-06-09
     */
    @Column(name = "CREATED_TIME", updatable = false)
    private LocalDateTime CREATED_TIME;  

}