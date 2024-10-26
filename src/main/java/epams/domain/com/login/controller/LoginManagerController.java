package epams.domain.com.login.controller;

import epams.domain.com.admin.service.HtmlLangDetailService;
import epams.domain.com.login.service.LoginService;
import epams.domain.com.login.service.MFALoginService;
import epams.domain.com.login.util.MaskPhoneNoUtil;
import epams.domain.com.login.util.webauthn.authenticator.Authenticator;
import epams.domain.com.login.util.webauthn.service.RegistrationService;
import epams.domain.com.login.util.webauthn.user.AppUser;
import epams.domain.com.member.dto.IamUserDTO;
import epams.domain.com.member.service.MemberService;
import epams.framework.exception.CustomGeneralRuntimeException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author K140024
 * @implNote 로그인 처리를 위한 컨트롤러
 * @since 2024-04-26
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginManagerController {

    /**
     * @author K140024
     * @implNote 코드 상세 서비스 주입
     * @since 2024-06-11
     */
    private final HtmlLangDetailService langDetailService;

    /**
     * 로그인 서비스
     */
    private final LoginService loginService;

    /**
     * Webauthn 등록 서비스
     */
    private final RegistrationService service;

    /**
     * mfa 로그인 서비스
     */
    private final MFALoginService restapiservice;
    
    /**
     * 사용자 서비스
     */
    private final MemberService memberService;

    /**
     * 현재 인증된 사용자 정보를 가져오는 메소드
     * 
     * @return 인증된 사용자 정보 또는 null
     */
    private Authentication authentication() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Authentication result;
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            result = null;
        } else {
            result = authentication;
        }
        return result;
    }

    /**
     * 로그인 페이지 요청 처리
     * 
     * @return 뷰 이름
     * @throws Exception 예외 발생 시
     */
    @GetMapping("/loginManager")
    public String loginManager(final Model model) {

    	String VIEW_URL = "common/loginManager";

        // 현재 로그인한 사용자 정보
        final Authentication auth = authentication();

        // 간편인증 등록 여부 확인
        final AppUser existingUser;
        if(auth != null) {
        	existingUser = service.getUserRepo().findByUsername(auth.getName());
        } else {
        	throw new CustomGeneralRuntimeException("로그인이 만료되었습니다. 로그인 후 사용해주세요");
        }
        
        final Authenticator existingAuthUser = service.getAuthRepository().findByUser(existingUser);

        // 간편인증 사용 여부를 모델에 추가
        if (existingAuthUser == null) {
            model.addAttribute("webauthnYn", false);
        } else {
            model.addAttribute("webauthnYn", true);
            model.addAttribute("webauthnRegDate", existingUser.getFST_ENR_DTM());
        }

        // 언어목록
        final Map<String, String> langList = langDetailService.getCodeHtmlDetail(VIEW_URL);
        model.addAttribute("langList", langList);

        // 사용자 계정
        model.addAttribute("username", auth.getName());
        log.warn(auth.getName());

        return VIEW_URL;
    }

}