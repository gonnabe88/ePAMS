package epams.framework.security;

import epams.domain.com.admin.dto.LogLoginDTO;
import epams.domain.com.admin.repository.LogRepository;
import epams.domain.com.login.service.LoginService;
import epams.domain.com.member.dto.IamUserDTO;
import epams.domain.com.member.dto.RoleDTO;
import epams.domain.com.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

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
     * @implNote 로그인 기록 저장소 주입
     * @since 2024-06-11
     */
    private final MemberService memberService;

    /**
     * @author K140024
     * @implNote 인증을 수행하는 메서드
     * @since 2024-06-11
     */
    @Override
    public Authentication authenticate(final Authentication authentication){

    	// authentication 객체를 통해 사용자로부터 전달받은 인증 정보
        final CustomWebAuthenticationDetails customDetails = (CustomWebAuthenticationDetails) authentication.getDetails();

        // 사용자 로그인 정보 추출
        final IamUserDTO iamUserDTO = new IamUserDTO();
        iamUserDTO.setUsername(authentication.getName());
        iamUserDTO.setPassword(authentication.getCredentials().toString());

        // ID & PW 로그인 검증
        final boolean pwLoginResult = loginService.pwLogin(iamUserDTO);
        
        // 사용자 역할 조회
        final RoleDTO role = memberService.findOneRoleByUsername(iamUserDTO);
        
        // otp & mfa 정보 추출
        final String OTP = customDetails.getOTP();
        final String MFA = customDetails.getMFA();

        boolean loginResult = false;
        log.info("MFA: {}", MFA);
        switch (MFA) {
            case "SMS":
                // @TODO SMS 인증 로직 추가
                loginResult = loginService.otpLogin(iamUserDTO, OTP);
                break;
            case "카카오톡":
                // @TODO 카카오톡 인증 로직 추가
                loginResult = loginService.otpLogin(iamUserDTO, OTP);
                break;
            case "otp":
                loginResult = loginService.otpLogin(iamUserDTO, OTP);
                break;
            case "FIDO":
                loginResult = loginService.fidoLogin(iamUserDTO);
                break;
            default:
            	loginResult = loginService.fidoLogin(iamUserDTO);
        }

        // 1차 인증(ID/PW) 실패
        if (!pwLoginResult) {
            log.warn("1차 인증 실패");
            logRepository.insert(LogLoginDTO.getDTO(iamUserDTO.getUsername(), "ID/PW(사후)", "0"));
            throw new AuthenticationException("Invalid credentials") {};
        }

        // 2차 인증(SMS, 카카오, otp, FIDO) 실패
        if (!loginResult) {
            log.warn("2차 인증 실패");
            logRepository.insert(LogLoginDTO.getDTO(iamUserDTO.getUsername(), MFA, "0"));
            throw new AuthenticationException("Invalid mfa credentials") {};
        }

        // Authentication 객체 생성 (인증 성공)
        final Authentication authenticated = new UsernamePasswordAuthenticationToken(
                iamUserDTO.getUsername(), iamUserDTO.getPassword(), List.of(new SimpleGrantedAuthority(role.getRoleId())));
        if(log.isWarnEnabled()){
            log.warn("로그인 사용자 정보: {}, {}", iamUserDTO.getUsername(), role.getRoleId());
        }
        // 로그인 성공 로깅
        logRepository.insert(LogLoginDTO.getDTO(iamUserDTO.getUsername(), MFA, "1"));

        return authenticated;
    }

    /**
     * @author K140024
     * @implNote 제공된 인증 클래스를 지원하는지 확인하는 메서드
     * @since 2024-06-11
     */
    @Override
    public boolean supports(final Class<?> authentication) {
        log.info("[LOG] CustomAuthenticationProvider > support");
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
