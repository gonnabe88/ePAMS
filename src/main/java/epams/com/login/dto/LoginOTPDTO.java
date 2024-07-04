package epams.com.login.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class LoginOTPDTO {
	
	/***
     * @author 140024
     * @implNote 자동으로 생성되는 auto increment number
     * @since 2024-06-09
     */
    private Long seqId;    
    
    /***
     * @author 140024
     * @implNote 행번
     * @since 2024-06-09
     */
    private String username; //행번
    
    /***
     * @author 140024
     * @implNote OTP 번호
     * @since 2024-06-09
     */
    private String OTP; //OTP   
    
    
    /***
     * @author 140024
     * @implNote MFA 인증방식
     * @since 2024-06-09
     */
    private String MFA; //MfA 방식
    
    /***
     * @author 140024
     * @implNote 생성시간
     * @since 2024-06-09
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime; //OTP 생성 시간

    /***
     * @author 140024
     * @implNote Entity > DTO 변경 메소드
     * @since 2024-06-09
     */
    public static LoginOTPEntity toEntity(final LoginOTPDTO loginOTPDTO) {
        final LoginOTPEntity mfaEntity = new LoginOTPEntity();
        mfaEntity.setOTP_ISN_SNO(loginOTPDTO.getSeqId());
        mfaEntity.setENO(loginOTPDTO.getUsername());
        mfaEntity.setOTP_SMS_CER_NO(loginOTPDTO.getOTP());
        mfaEntity.setCER_KD_NM(loginOTPDTO.getMFA());
        mfaEntity.setGNT_DTM(loginOTPDTO.getCreateTime());
        return mfaEntity;
    }
    
    /***
     * @author 140024
     * @implNote DTO > Entity 변경 메소드
     * @since 2024-06-09
     */
    public static LoginOTPDTO toDTO(final LoginOTPEntity loginOTPEntity) {
        final LoginOTPDTO mfaDTO = new LoginOTPDTO();
        mfaDTO.setSeqId(loginOTPEntity.getOTP_ISN_SNO());
        mfaDTO.setUsername(loginOTPEntity.getENO());
        mfaDTO.setOTP(loginOTPEntity.getOTP_SMS_CER_NO());
        mfaDTO.setMFA(loginOTPEntity.getCER_KD_NM());
        mfaDTO.setCreateTime(loginOTPEntity.getGNT_DTM());
        return mfaDTO;
    }

    /***
     * @author 140024
     * @implNote List<Entity> > List<DTO> 변경 메소드
     * @since 2024-06-09
     */
    public static List<LoginOTPDTO> toDTOList(final List<LoginOTPEntity> entityList) {
        return entityList.stream().map(LoginOTPDTO::toDTO).collect(Collectors.toList());
    }

    /***
     * @author 140024
     * @implNote List<DTO> > List<Entity> 변경 메소드
     * @since 2024-06-09
     */
    public static List<LoginOTPEntity> toEntityList(final List<LoginOTPDTO> dtoList) {
        return dtoList.stream().map(LoginOTPDTO::toEntity).collect(Collectors.toList());
    }    
    
}
