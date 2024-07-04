package epams.com.login.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


/***
 * @author 140024
 * @implNote MFA 로그인 관련 정보를 객체로 관리하기 위한 entity
 * @since 2024-06-09
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "THURXE_COTPIM")
@Comment("인사_외부근태 OTP발급이력기본")
public class LoginOTPEntity {
	
    /***
     * @author 140024
     * @implNote 자동으로 생성되는 auto increment number
     * @since 2024-06-09
     */
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "OTP_ISN_SNO", columnDefinition = "NUMBER(22)")
    @Comment("OTP발급일련번호")
    private Long OTP_ISN_SNO;
    
    /***
     * @author 140024
     * @implNote 행번
     * @since 2024-06-09
     */
    @Column(name = "ENO", length = 32, nullable = false)
    @Comment("사원번호")
    private String ENO; //행번
    
    /***
     * @author 140024
     * @implNote OTP 번호
     * @since 2024-06-09
     */
    @Column(name = "OTP_SMS_CER_NO", length = 8) 
    @Comment("OTPSMS인증번호")
    private String OTP_SMS_CER_NO; //OTP
    
    /***
     * @author 140024
     * @implNote MFA 인증방식
     * @since 2024-06-09
     */
    @Column(name = "CER_KD_NM", length = 100)
    @Comment("인증종류명")
    private String CER_KD_NM; 
    
    
    /***
     * @author 140024
     * @implNote 생성시간
     * @since 2024-06-09
     */
    @CreationTimestamp
    @Column(name = "GNT_DTM", updatable = false, columnDefinition = "DATE")
    @Comment("생성시간")
    private LocalDateTime GNT_DTM;
    
}
