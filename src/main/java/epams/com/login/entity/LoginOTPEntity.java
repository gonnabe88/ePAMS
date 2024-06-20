package epams.com.login.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/***
 * @author 140024
 * @implNote MFA 로그인 관련 정보를 객체로 관리하기 위한 entity
 * @since 2024-06-09
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "COM_LOGIN_OTP")
public class LoginOTPEntity {
	
    /***
     * @author 140024
     * @implNote 자동으로 생성되는 auto increment number
     * @since 2024-06-09
     */
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long SEQ_ID;
    
    /***
     * @author 140024
     * @implNote 행번
     * @since 2024-06-09
     */
    @Column(length = 20, nullable = false) // 크기 20, not null
    private String EMP_NO; //행번
    
    /***
     * @author 140024
     * @implNote OTP 번호
     * @since 2024-06-09
     */
    @Column(length = 6) // 크기 20, not null
    private String OTP; //OTP
    
    /***
     * @author 140024
     * @implNote MFA 인증방식
     * @since 2024-06-09
     */
    @Column(length = 10)
    private String MFA; //MfA 방식
    
    
    /***
     * @author 140024
     * @implNote 생성시간
     * @since 2024-06-09
     */
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime CREATED_TIME; //OTP 생성 시간
    
}
