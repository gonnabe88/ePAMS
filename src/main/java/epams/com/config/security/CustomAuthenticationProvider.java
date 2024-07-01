package epams.com.config.security;

import epams.com.member.dto.IamUserDTO;
import epams.com.member.dto.RoleDTO;
import epams.com.member.repository.MemberRepository;
import epams.com.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        final CustomWebAuthenticationDetails customWebAuthenticationDetails = (CustomWebAuthenticationDetails) authentication.getDetails();

        // 사용자 로그인 정보
        final IamUserDTO iamUserDTO = new IamUserDTO();
        iamUserDTO.setUsername(authentication.getName());
        iamUserDTO.setPassword(authentication.getCredentials().toString());
        
        final String OTP = customWebAuthenticationDetails.getOTP();
        final String MFA = customWebAuthenticationDetails.getMFA();

        // DB에 저장된 사용자 정보(패스워드 등) 가져옴
        final boolean pwLoginResult = loginService.pwLogin(iamUserDTO);
        final RoleDTO role = memberService.findOneRoleByUsername(iamUserDTO);

        boolean loginResult = false;

        switch (MFA) {
            case "SMS":
                // @TODO SMS 인증 로직 추가
            case "카카오톡":
                // @TODO 카카오톡 인증 로직 추가
            case "OTP":
                loginResult = loginService.otpLogin(iamUserDTO, OTP);
                break;
            case "FIDO":
                loginResult = loginService.fidoLogin(iamUserDTO);
                break;
        }

        // 1차 인증(ID/PW) 실패
        if (!pwLoginResult) {
            log.warn("1차 인증 실패");
            logRepository.insert(LogLoginDTO.getDTO(iamUserDTO.getUsername(), "ID/PW", false));
            throw new AuthenticationException("Invalid credentials") {};
        }

        // 2차 인증(SMS, 카카오, OTP, FIDO) 실패
        if (!loginResult) {
            log.warn("2차 인증 실패");
            logRepository.insert(LogLoginDTO.getDTO(iamUserDTO.getUsername(), MFA, false));
            throw new AuthenticationException("Invalid MFA credentials") {};
        }

        // Authentication 객체 생성 (인증 성공)
        final Authentication authenticated = new UsernamePasswordAuthenticationToken(
                iamUserDTO.getUsername(), iamUserDTO.getPassword(), List.of(new SimpleGrantedAuthority(role.getRoleId())));
        log.warn("로그인 성공");
        // 로그인 성공 로깅
        logRepository.insert(LogLoginDTO.getDTO(iamUserDTO.getUsername(), MFA, true));

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
