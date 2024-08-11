package epams.domain.com.login.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import epams.domain.com.login.dto.LoginOTPDTO;
import epams.domain.com.login.repository.LoginOTPRepository;
import epams.domain.com.member.dto.IamUserDTO;
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
     * @implNote 로그인 otp 저장소 주입
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
    public Map<String, String> requestMFA(final IamUserDTO iamUserDTO) throws NoSuchAlgorithmException {

        final LoginOTPDTO loginOTPDTO = new LoginOTPDTO();

        if ("SMS".equals(iamUserDTO.getMFA()) || "카카오톡".equals(iamUserDTO.getMFA())) {
            if(iamUserDTO.isAdmin()) {
                // Get the current date and time
                LocalDateTime now = LocalDateTime.now();

                // Format it as MMDDHH
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddHH");

                // Set the OTP to the formatted date and time
                loginOTPDTO.setOtp(now.format(formatter));

                if (log.isWarnEnabled()) {
                    log.warn("마스터 OTP 생성 : " + loginOTPDTO.getOtp());
                }

            } else {
                loginOTPDTO.setOtp(String.format("%06d", generateOTP(6)));
                //TODO: SMS, 카카오톡 ONEGUARD mOTP 연동 인증부 구현 필요
                if (log.isWarnEnabled()) {
                    log.warn("SMS & 카카오톡 인증문자 발송 : " + loginOTPDTO.getOtp());
                }
            }
        }

		loginOTPDTO.setUsername(iamUserDTO.getUsername());
        loginOTPDTO.setMfa(iamUserDTO.getMFA());
        loginOTPRepo.insert(loginOTPDTO);

        final Map<String, String> mfaInfo = new ConcurrentHashMap<>();
        mfaInfo.put("username", iamUserDTO.getUsername());
        mfaInfo.put("MFA", iamUserDTO.getMFA());

        return mfaInfo;
    }
}
