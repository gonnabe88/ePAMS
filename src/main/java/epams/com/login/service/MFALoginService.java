package epams.com.login.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import epams.com.login.dto.LoginOTPDTO;
import epams.com.login.repository.LoginOTPRepository;
import epams.com.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author K140024
 * @implNote 멀티 팩터 인증 로그인 서비스
 * @since 2024-06-11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MFALoginService {

    /**
     * @author K140024
     * @implNote 로그인 OTP 저장소 주입
     * @since 2024-06-11
     */
    private final LoginOTPRepository loginOTPRepo;

    /**
     * @author K140024
     * @implNote SecureRandom 함수를 통해 안전하게 6자리 인증번호 생성
     * @since 2024-06-11
     */
    private int generateOTP(final int length) {
        final SecureRandom secureRandom = new SecureRandom();
        final int upperLimit = (int) Math.pow(10, length);
        return secureRandom.nextInt(upperLimit);
    }

    /**
     * @author K140024
     * @implNote 멀티 팩터 인증 요청을 처리하는 메서드
     * @since 2024-06-11
     */
    public Map<String, String> requestMFA(final MemberDTO memberDTO) throws NoSuchAlgorithmException {

        final LoginOTPDTO loginOTPDTO = new LoginOTPDTO();

        if (memberDTO.getMFA().equals("SMS") || memberDTO.getMFA().equals("카카오톡")) {
            // SMS, 카카오톡 인증 시 필요한 인증번호 Random 숫자 6자리 발급
            final String OTP = String.format("%06d", generateOTP(6));
            loginOTPDTO.setOTP(OTP);

            //TODO: SMS, 카카오톡 ONEGUARD mOTP 연동 인증부 구현 필요 
            log.warn("SMS & 카카오톡 인증문자 발송 : " + OTP);
        }

		loginOTPDTO.setUsername(memberDTO.getUsername());
        loginOTPDTO.setMFA(memberDTO.getMFA());
        loginOTPRepo.insert(loginOTPDTO);

        final Map<String, String> mfaInfo = new HashMap<>();
        mfaInfo.put("username", memberDTO.getUsername());
        mfaInfo.put("MFA", memberDTO.getMFA());

        return mfaInfo;
    }
}
