package epams.com.login.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import epams.com.login.dto.LoginMFADTO;
import epams.com.member.dto.MemberDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter

@Table(name = "mfa")
public class LoginMFAEntity {
	
    @Id // pk 컬럼 지정. 필수
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;
    
    @Column(length = 20, nullable = false) // 크기 20, not null
    private String username; //행번
    
    @Column(length = 6) // 크기 20, not null
    private String OTP; //OTP
    
    @Column(length = 10)
    private String MFA; //MfA 방식
    
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createTime; //OTP 생성 시간
    
    public static LoginMFAEntity toSaveEntity(LoginMFADTO mfaDTO) {
    	LoginMFAEntity mfaEntity = new LoginMFAEntity();
    	mfaEntity.setId(mfaDTO.getId());
    	mfaEntity.setUsername(mfaDTO.getUsername());
    	mfaEntity.setOTP(mfaDTO.getOTP());
    	mfaEntity.setMFA(mfaDTO.getMFA());
    	mfaEntity.setCreateTime(mfaDTO.getCreateTime());
        return mfaEntity;
    }
    
    public static LoginMFAEntity toSaveEntity(MemberDTO memberDTO) {
    	LoginMFAEntity mfaEntity = new LoginMFAEntity();
    	mfaEntity.setUsername(memberDTO.getUsername());
    	mfaEntity.setMFA(memberDTO.getMFA());
        return mfaEntity;
    }
    
    
}
