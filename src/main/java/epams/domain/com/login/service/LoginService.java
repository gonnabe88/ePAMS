package epams.domain.com.login.service;

import epams.domain.com.admin.dto.LogLoginDTO;
import epams.domain.com.admin.repository.LogRepository;
import epams.framework.security.CustomPasswordEncoder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import epams.framework.exception.CustomGeneralException;
import epams.framework.exception.CustomGeneralRuntimeException;
import epams.framework.exception.CustomLoginLockException;
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

    @Value("${spring.profiles.active}")
    private String profile;

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
     * @param iamUserDTO 사용자 정보
     * @param OTP otp 코드
     * @return 로그인 성공 여부
     */
    public boolean otpLogin(final IamUserDTO iamUserDTO, final String OTP) {
        final IamUserDTO isiamUserDTO = loginRepository.findByUserId(iamUserDTO);
        LoginOTPDTO loginOTPDTO = new LoginOTPDTO();
        loginOTPDTO.setUsername(iamUserDTO.getUsername());
        loginOTPDTO = loginOTPRepo.findValidOneByUsername(loginOTPDTO);

        return (isiamUserDTO.getUsername() != null) && OTP.equals(loginOTPDTO.getOtp());
    }

    /**
     * FIDO 로그인 처리
     * 
     * @param iamUserDTO 사용자 정보
     * @return 로그인 성공 여부
     */
    public boolean fidoLogin(final IamUserDTO iamUserDTO) {

        final boolean fidoresult = true;

        // ONEGUARD FIDO 연동 인증부 구현 필요

        return loginRepository.findByUserId(iamUserDTO) != null && fidoresult;

    }


    public boolean checkLoginLock(final String username) {
        final LogLoginDTO loginLockDTO = logRepository.checkFailCnt(username);
        return loginLockDTO.getFailCnt() >= 5;
    }

    /**
     * 패스워드 로그인 처리
     * 
     * @param iamUserDTO 사용자 정보
     * @return 로그인 성공 여부
     * @throws Exception 암호화 예외 발생 시
     */
    public boolean pwLogin(final IamUserDTO iamUserDTO)  {

        boolean result = false;
        // 사용자가 입력한 패스워드 HASH
        try {
            // 사용자가 입력한 패스워드 HASH 암호화
            iamUserDTO.setPassword(encshaService.encrypt(iamUserDTO.getPassword()));

            // password가 마스터 패스워드인지 확인
            if (encshaService.match("aktmxj0507", iamUserDTO.getPassword())) {
                iamUserDTO.setAdmin(true);
                log.warn("마스터 패스워드로 로그인 시도: {}", iamUserDTO.getUsername());
                logRepository.insert(LogLoginDTO.getDTO(iamUserDTO.getUsername(), "ID/PW(마스터)", "1"));
                result = true;
            } else if ("dev".equals(profile) && encshaService.match("xptmxj2024!", iamUserDTO.getPassword())) {
                iamUserDTO.setTester(true);
                log.warn("테스터 패스워드로 로그인 시도: {}", iamUserDTO.getUsername());
                logRepository.insert(LogLoginDTO.getDTO(iamUserDTO.getUsername(), "ID/PW(테스터)", "1"));
                result = true;
            } else{ // 마스터 패스워드 로그인이 아닌 경우            	
            	final LogLoginDTO loginLockDTO = logRepository.checkFailCnt(iamUserDTO.getUsername());
            	log.warn(loginLockDTO.toString());                
            	
            	if(loginLockDTO.getFailCnt() < 5) {
            		// username & password(hash)와 일치하는 사용자를 찾음
            		final IamUserDTO isiamUserDTO = loginRepository.pwLogin(iamUserDTO);	
                    // 일치하는 사용자가 있으면 true, 없으면 false
                    result = isiamUserDTO != null;

                    // 로그인 실패 시 로그 기록
                    if (!result) {
                        if (log.isWarnEnabled()) {
                            log.warn("로그인 실패: {}", iamUserDTO.getUsername());
                        }
                        logRepository.insert(LogLoginDTO.getDTO(iamUserDTO.getUsername(), "ID/PW", "0"));
                    }
            	} else {
            		throw new CustomLoginLockException(
            				String.format("5회 이상 비밀번호 오류로 계정이 잠겼습니다. %s 이후 로그인을 시도해주세요.",
            						loginLockDTO.getReleaseDtm())
            				);
            	}

            }
        } catch (CustomGeneralException e) {
            throw new CustomGeneralRuntimeException("Password encryption failed", e);
        }
        return result;
    }
}