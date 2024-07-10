package epams.com.login.entity;

import epams.com.board.entity.BaseEntity;
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
public class LoginOTPEntity extends BaseEntity {


    /**
     * @author 140024
     * @implNote 시퀀스 이름 상수
     * @since 2024-06-09
     */
    private static final String SEQUENCE = "OTP_ISN_SNO";

    /***
     * @author 140024
     * @implNote 자동으로 생성되는 auto increment number
     * @since 2024-06-09
     */
    @Id
    @Column(name = SEQUENCE, columnDefinition = "NUMBER(22)")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE)
    @SequenceGenerator(name = SEQUENCE, sequenceName = SEQUENCE, allocationSize = 1)
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
    
}
