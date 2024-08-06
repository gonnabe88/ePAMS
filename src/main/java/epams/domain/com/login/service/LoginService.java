package epams.domain.com.login.service;

import epams.domain.com.admin.dto.LogLoginDTO;
import epams.domain.com.admin.repository.LogRepository;
import org.springframework.stereotype.Service;

import epams.framework.exception.CustomGeneralException;
import epams.framework.exception.CustomGeneralRuntimeException;
import epams.domain.com.login.dto.LoginOTPDTO;
import epams.domain.com.login.repository.LoginOTPRepository;
import epams.domain.com.login.repository.LoginRepository;
import epams.domain.com.member.dto.IamUserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author K140024
 * @implNote 간편인증 요청 처리를 위한 서비스
 * @since 2024-06-22
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    /**
     * LoginRepository 인스턴스
     */
    private final LoginRepository loginRepository;

    /**
     * LoginOTPRepository 인스턴스
     */
    private final LoginOTPRepository loginOTPRepo;

    /**
     * ShaEncryptService 인스턴스
     */
    private final ShaEncryptService encshaService;

    /**
     * @author K140024
     * @implNote 로그인 기록 저장소 주입
     * @since 2024-06-11
     */
    private final LogRepository logRepository;

    /**
     * otp 로그인 처리
     * 
     * @param username 사용자 이름
     * @param OTP otp 코드
     * @return 로그인 성공 여부
     */
    public boolean otpLogin(final IamUserDTO iamUserDTO, final String OTP) {
        final IamUserDTO isiamUserDTO = loginRepository.findByUserId(iamUserDTO);
        LoginOTPDTO loginOTPDTO = new LoginOTPDTO();
        loginOTPDTO.setUsername(iamUserDTO.getUsername());
        loginOTPDTO = loginOTPRepo.findValidOneByUsername(loginOTPDTO);

        // ONEGUARD mOTP 연동 인증부 구현 필요
        log.warn("ONEGUAER otp 검증 요청 및 응답");

        return (isiamUserDTO.getUsername() != null) && OTP.equals(loginOTPDTO.getOtp());

    }

    /**
     * FIDO 로그인 처리
     * 
     * @param username 사용자 이름
     * @return 로그인 성공 여부
     */
    public boolean fidoLogin(final IamUserDTO iamUserDTO) {

        final boolean fidoresult = true;

        // ONEGUARD FIDO 연동 인증부 구현 필요

        return loginRepository.findByUserId(iamUserDTO) != null && fidoresult;

    }

    /**
     * 패스워드 로그인 처리
     * 
     * @param iamUserDTO 사용자 정보
     * @return 로그인 성공 여부
     * @throws Exception 암호화 예외 발생 시
     */
    public boolean pwLogin(final IamUserDTO iamUserDTO)  {
        // 사용자가 입력한 패스워드 HASH
        try {
            // 사용자가 입력한 패스워드 HASH
            iamUserDTO.setPassword(encshaService.encrypt(iamUserDTO.getPassword()));
        } catch (CustomGeneralException e) {
            throw new CustomGeneralRuntimeException("Password encryption failed", e);
        }
        // username & password(hash)와 일치하는 사용자를 찾음
        final IamUserDTO isiamUserDTO = loginRepository.login(iamUserDTO);
        final boolean result = isiamUserDTO != null;
        if(!result) {
            if(log.isWarnEnabled())
            {
                log.warn("로그인 실패: {}", iamUserDTO.getUsername());
            }
            logRepository.insert(LogLoginDTO.getDTO(iamUserDTO.getUsername(), "ID/PW", '0'));
        }
        return result;
    }

}