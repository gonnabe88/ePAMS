package epams.domain.com.login.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import epams.domain.com.eai.dto.EaiDTO;
import epams.domain.com.eai.service.EaiService;
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
     * @implNote EAI 서비스 주입
     * @since 2024-06-11
     */
    private final EaiService eaiService;

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
        loginOTPDTO.setUsername(iamUserDTO.getUsername());
        loginOTPDTO.setMfa(iamUserDTO.getMFA());

        Long otpSeqId = null;

        if ("SMS".equals(iamUserDTO.getMFA()) || "카카오톡".equals(iamUserDTO.getMFA())) {
            if(iamUserDTO.isAdmin()) { // 마스터 OTP 
                
                LocalDateTime now = LocalDateTime.now(); // 현재 시간
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddHH"); // 월일시 형식 포메팅
                loginOTPDTO.setOtp(now.format(formatter)); // 월일시(6자리) OTP 세팅
                loginOTPRepo.insert(loginOTPDTO); // OTP 발급 테이블 반영

                if (log.isWarnEnabled()) {
                    log.warn("마스터 OTP 생성 : " + loginOTPDTO.getOtp());
                }

            } else { // 일반 사용자 OTP 
                loginOTPDTO.setOtp(String.format("%06d", generateOTP(6))); // 일반 OTP 생성
                otpSeqId = loginOTPRepo.insert(loginOTPDTO); // OTP 발급 테이블 반영 흐 OTP_ISN_SNO (OTP 발급 일련번호) 가져오기

                EaiDTO eaiDto = new EaiDTO();
                eaiDto.setSystem("UMS"); // UMS 연동
                eaiDto.setUmsTrSno(otpSeqId.toString()); // UMS거래 일련번호 = OTP_ISN_SNO (OTP 발급 일련번호)
                eaiDto.setReqCh(iamUserDTO.getPhoneNo()); // 핸드폰 번호
                eaiDto.setEmplNum(iamUserDTO.getUsername()); // 행번
                eaiDto.setUmData1(loginOTPDTO.getOtp()); // OTP 번호

                switch (iamUserDTO.getMFA()) {
                    case "SMS":
                        eaiDto.setUmsBzDttId("SMS2096"); // UMS업무구분ID
                        eaiDto.setIfId("EHRO00043754"); // EAI 인터페이스 ID
                        break;
                    case "카카오톡":
                        eaiDto.setUmsBzDttId("ALT0165"); // UMS업무구분ID
                        eaiDto.setIfId("EHRO00043754"); // EAI 인터페이스 ID
                        break;
                }
                
                eaiDto = eaiService.sendEAI(eaiDto); // EAI 전문작성 및 전송

                if (log.isWarnEnabled()) {
                    log.warn("SMS & 카카오톡 인증문자 발송 : " + loginOTPDTO.getOtp());
                    log.warn("EAI : " + eaiDto.toString());
                }
            }
        }

        final Map<String, String> mfaInfo = new ConcurrentHashMap<>();
        mfaInfo.put("username", iamUserDTO.getUsername());
        mfaInfo.put("MFA", iamUserDTO.getMFA());

        return mfaInfo;
    }
}
