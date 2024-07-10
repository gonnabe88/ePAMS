package epams.com.login.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;
import epams.com.board.dto.BaseDTO;
import epams.com.login.entity.LoginOTPEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/***
 * @author 140024
 * @implNote THURXE_COTPIM (OTP 발급내역)
 * @since 2024-06-09
 */
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
public class LoginOTPDTO extends BaseDTO {
	
	/***
     * @author 140024
     * @implNote OTP발급일련번호(OTP_ISN_SNO)
     * @since 2024-06-09
     */
    private Long seqId;    
    
    /***
     * @author 140024
     * @implNote 사원번호(ENO)
     * @since 2024-06-09
     */
    private String username;
    
    /***
     * @author 140024
     * @implNote OTPSMS인증번호(OTP_SMS_CER_NO)
     * @since 2024-06-09
     */
    private String OTP;
    
    
    /***
     * @author 140024
     * @implNote 인증종류명(CER_KD_NM)
     * @since 2024-06-09
     */
    private String MFA;
    
}
