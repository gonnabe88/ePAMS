package com.kdb.common.dto;

import java.time.LocalDateTime;

import com.kdb.common.entity.MfaEntity;

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
public class MfaDTO {
    private Long id;    
    private String username; //행번    
    private String OTP; //OTP    
    private String MFA; //MfA 방식    
    private LocalDateTime createTime; //OTP 생성 시간    
    
    public MfaDTO (String username, String OTP, String MFA)
    {
    	this.username = username;
    	this.OTP = OTP;
    	this.MFA = MFA;
    }
    
    public static MfaDTO toMfaDTO(MfaEntity mfaEntity) {
    	MfaDTO mfaDTO = new MfaDTO();
    	mfaDTO.setId(mfaEntity.getId());
    	mfaDTO.setUsername(mfaEntity.getUsername());
    	mfaDTO.setOTP(mfaEntity.getOTP());
    	mfaDTO.setMFA(mfaEntity.getMFA());
    	mfaDTO.setCreateTime(mfaEntity.getCreateTime());
    	
    	return mfaDTO;
    }
}
