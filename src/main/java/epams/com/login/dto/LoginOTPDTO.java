package epams.com.login.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import epams.com.login.entity.LoginOTPEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


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
    private LocalDate createTime; //OTP 생성 시간    

    /***
     * @author 140024
     * @implNote Entity > DTO 변경 메소드
     * @since 2024-06-09
     */
    public static LoginOTPEntity toEntity(LoginOTPDTO loginOTPDTO) {
        LoginOTPEntity mfaEntity = new LoginOTPEntity();
        mfaEntity.setSEQ_ID(loginOTPDTO.getSeqId());
        mfaEntity.setEMP_NO(loginOTPDTO.getUsername());
        mfaEntity.setOTP(loginOTPDTO.getOTP());
        mfaEntity.setMFA(loginOTPDTO.getMFA());
        mfaEntity.setCREATED_TIME(loginOTPDTO.getCreateTime());
        return mfaEntity;
    }
    
    /***
     * @author 140024
     * @implNote DTO > Entity 변경 메소드
     * @since 2024-06-09
     */
    public static LoginOTPDTO toDTO(LoginOTPEntity loginOTPEntity) {
        LoginOTPDTO mfaDTO = new LoginOTPDTO();
        mfaDTO.setSeqId(loginOTPEntity.getSEQ_ID());
        mfaDTO.setUsername(loginOTPEntity.getEMP_NO());
        mfaDTO.setOTP(loginOTPEntity.getOTP());
        mfaDTO.setMFA(loginOTPEntity.getMFA());
        mfaDTO.setCreateTime(loginOTPEntity.getCREATED_TIME());
        return mfaDTO;
    }

    /***
     * @author 140024
     * @implNote List<Entity> > List<DTO> 변경 메소드
     * @since 2024-06-09
     */
    public static List<LoginOTPDTO> toDTOList(List<LoginOTPEntity> entityList) {
        return entityList.stream().map(LoginOTPDTO::toDTO).collect(Collectors.toList());
    }

    /***
     * @author 140024
     * @implNote List<DTO> > List<Entity> 변경 메소드
     * @since 2024-06-09
     */
    public static List<LoginOTPEntity> toEntityList(List<LoginOTPDTO> dtoList) {
        return dtoList.stream().map(LoginOTPDTO::toEntity).collect(Collectors.toList());
    }    
    
}
