package epams.com.login.service;

import org.springframework.stereotype.Service;

import epams.com.config.security.CustomGeneralEncryptionException;
import epams.com.config.security.CustomGeneralRuntimeException;
import epams.com.login.dto.LoginOTPDTO;
import epams.com.login.repository.LoginOTPRepository;
import epams.com.login.repository.LoginRepository;
import epams.com.member.dto.TempUserDTO;
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
     * OTP 로그인 처리
     * 
     * @param username 사용자 이름
     * @param OTP OTP 코드
     * @return 로그인 성공 여부
     */
    public boolean otpLogin(final String username, final String OTP) {
        final TempUserDTO ismemberDTO = loginRepository.findByUserId(username);
        LoginOTPDTO loginOTPDTO = new LoginOTPDTO();
        loginOTPDTO.setUsername(username);
        loginOTPDTO = loginOTPRepo.findValidOneByUsername(loginOTPDTO);

        // ONEGUARD mOTP 연동 인증부 구현 필요
        log.warn("ONEGUAER OTP 검증 요청 및 응답");

        return ismemberDTO != null && OTP.equals(loginOTPDTO.getOTP());

    }

    /**
     * FIDO 로그인 처리
     * 
     * @param username 사용자 이름
     * @return 로그인 성공 여부
     */
    public boolean fidoLogin(final String username) {
        final TempUserDTO ismemberDTO = loginRepository.findByUserId(username);
        final boolean fidoresult = true;

        // ONEGUARD FIDO 연동 인증부 구현 필요

        return ismemberDTO != null && fidoresult;

    }

    /**
     * 패스워드 로그인 처리
     * 
     * @param memberDTO 사용자 정보
     * @return 로그인 성공 여부
     * @throws Exception 암호화 예외 발생 시
     */
    public boolean pwLogin(final TempUserDTO memberDTO)  {
        // 사용자가 입력한 패스워드 HASH
        try {
            // 사용자가 입력한 패스워드 HASH
            memberDTO.setPassword(encshaService.encrypt(memberDTO.getPassword()));
        } catch (CustomGeneralEncryptionException e) {
            throw new CustomGeneralRuntimeException("Password encryption failed", e);
        }  
        // username & password(hash)와 일치하는 사용자를 찾음
        final TempUserDTO ismemberDTO = loginRepository.login(memberDTO);
        //log.warn(ismemberDTO.toString());
        return ismemberDTO != null;
    }

}