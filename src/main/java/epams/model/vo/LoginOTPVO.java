package epams.model.vo;

import lombok.*;

/***
 * @author 140024
 * @implNote OEHR.THURXE_COTPIM (otp 발급내역)
 * @since 2024-06-09
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginOTPVO extends BaseVO {
	
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
    private String otp;
    
    
    /***
     * @author 140024
     * @implNote 인증종류명(CER_KD_NM)
     * @since 2024-06-09
     */
    private String mfa;
    
}
