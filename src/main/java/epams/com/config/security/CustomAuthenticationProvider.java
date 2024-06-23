package epams.com.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import epams.com.admin.dto.LogLoginDTO;
import epams.com.admin.repository.LogRepository;
import epams.com.login.service.LoginService;
import epams.com.login.service.ShaEncryptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author K140024
 * @implNote 2단계 인증 로직 (Custom)
 * @since 2024-06-20
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    /**
     * @author K140024
     * @implNote SHA 암호화 서비스 주입
     * @since 2024-06-11
     */
    private final ShaEncryptService encshaService;

    /**
     * @author K140024
     * @implNote 로그인 서비스 주입
     * @since 2024-06-11
     */
    private final LoginService loginService;

    /**
     * @author K140024
     * @implNote 로그인 기록 저장소 주입
     * @since 2024-06-11
     */
    private final LogRepository logRepository;

    /**
     * @author K140024
     * @implNote 사용자 세부 정보 서비스 주입
     * @since 2024-06-11
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * @author K140024
     * @implNote 인증을 수행하는 메서드
     * @since 2024-06-11
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        final CustomWebAuthenticationDetails customWebAuthenticationDetails = (CustomWebAuthenticationDetails) authentication.getDetails();
        final String username = authentication.getName();
        String password = null;
        try {
            // 입력받은 password HASH
            password = encshaService.encrypt(authentication.getCredentials().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        final String OTP = customWebAuthenticationDetails.getOTP();
        final String MFA = customWebAuthenticationDetails.getMFA();

        // DB에 저장된 사용자 정보(패스워드 등) 가져옴
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        boolean loginResult = false;

        switch (MFA) {
            case "SMS":
            case "카카오톡":
            case "OTP":
                loginResult = loginService.otpLogin(username, OTP);
                break;
            case "FIDO":
                loginResult = loginService.fidoLogin(username);
                break;
        }

        // 사용자가 없는 경우
        if (userDetails == null) {
            logRepository.insert(LogLoginDTO.getDTO(username, "Unknown User", false));
            throw new UsernameNotFoundException("User not found");
        }

		// 패스워드가 Null인 경우
        if (password == null) {
            log.warn("패스워드 NULL");
            logRepository.insert(LogLoginDTO.getDTO(username, "패스워드", false));
            throw new AuthenticationException("Invalid credentials") {};
        }

		// 패스워드가 일치하지 않는 경우
        if (!password.equals(userDetails.getPassword())) {
            log.warn("패스워드 불일치");
            logRepository.insert(LogLoginDTO.getDTO(username, "패스워드", false));
            throw new AuthenticationException("Invalid credentials") {};
        }

        // 2차 인증(SMS, 카카오, OTP, FIDO) 실패
        if (!loginResult) {
            log.warn("2차 인증 실패");
            logRepository.insert(LogLoginDTO.getDTO(username, MFA, false));
            throw new AuthenticationException("Invalid MFA credentials") {};
        }

        // Create a fully authenticated Authentication object
        final Authentication authenticated = new UsernamePasswordAuthenticationToken(
                userDetails, password, userDetails.getAuthorities());
        // 로그인 성공 로깅
        logRepository.insert(LogLoginDTO.getDTO(username, MFA, true));
        return authenticated;
    }

    /**
     * @author K140024
     * @implNote 제공된 인증 클래스를 지원하는지 확인하는 메서드
     * @since 2024-06-11
     */
    @Override
    public boolean supports(Class<?> authentication) {
        log.info("[LOG] CustomAuthenticationProvider > support");
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
