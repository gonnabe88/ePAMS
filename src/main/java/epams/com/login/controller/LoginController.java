package epams.com.login.controller;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import epams.com.config.security.CustomGeneralRuntimeException;
import epams.com.login.service.LoginService;
import epams.com.login.service.MFALoginService;
import epams.com.login.util.webauthn.RegistrationService;
import epams.com.member.dto.MemberDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author K140024
 * @implNote 로그인 처리를 위한 컨트롤러
 * @since 2024-04-26
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    /**
     * 로그인 서비스
     */
    private final LoginService loginService;

    /**
     * Webauthn 등록 서비스
     */
    private final RegistrationService service;

    /**
     * MFA 로그인 서비스
     */
    private final MFALoginService restapiservice;

    /**
     * 현재 인증된 사용자 정보를 가져오는 메소드
     * 
     * @return 인증된 사용자 정보 또는 null
     */
    private Authentication Authentication() { 
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || authentication instanceof AnonymousAuthenticationToken) return null;
	    return authentication;
    }

    /**
     * 로그아웃 처리
     * 
     * @param request HttpServletRequest 객체
     * @return 리디렉션 경로
     * @throws Exception 예외 발생 시
     */
    @GetMapping("/logout")
    public String logout(final HttpServletRequest request) {
        final HttpSession session = request.getSession(false);
        session.invalidate();
        return "redirect:/login";
    }

    /**
     * 로그인 페이지 요청 처리
     * 
     * @return 뷰 이름
     * @throws Exception 예외 발생 시
     */
    @GetMapping({ "/", "/login" })
    public String login(final HttpServletResponse response, @CookieValue(value = "idChk", required = false) final String username,
            @RequestParam(value = "isChecked", defaultValue = "false") final boolean isChecked, final Model model) {
    	String VIEW = "/common/login";
        final Authentication auth = Authentication();
        final int existingUser = service.getWebauthUserRepository().countByUsername(username);
        final int existingAuthUser = service.getWebauthDetailRepository().countByUser(username);
        
        if (existingAuthUser == 0) {
            model.addAttribute("isChecked", "false");
            log.info("Not simple auth user");
        } else {
            model.addAttribute("isChecked", "true");
            log.info("simple auth user");
        }

        if (auth != null && auth.isAuthenticated()) {
        	VIEW = "redirect:/index";
        	
        }
        return VIEW;
    }

    /**
     * 비밀번호 로그인 처리
     * 
     * @return 응답 데이터 맵
     * @throws Exception 예외 발생 시
     */
    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> pwlogin(final HttpServletResponse response, @ModelAttribute final MemberDTO memberDTO, final Model model)
    {
        final int existingUser = service.getWebauthUserRepository().countByUsername(memberDTO.getUsername());
        final Map<String, Object> res = new ConcurrentHashMap<>();
        if (loginService.pwLogin(memberDTO)) {
            // 로그인 성공 시 인증번호 생성
            try {
				restapiservice.requestMFA(memberDTO);
			} catch (NoSuchAlgorithmException e) {
	            throw new CustomGeneralRuntimeException("NoSuchAlgorithmException : restapiservice.requestMFA(memberDTO)", e);
			}
            // 로그인 성공 시 추가 인증 단계로 넘어가기 위해 성공 여부를 반환
            res.put("result", true);
            if (existingUser == 0) {
                res.put("simpleauth", false);
            }
            else {
                res.put("simpleauth", true);
            }
        } else {
            // 로그인 실패 시 실패 여부를 반환
            res.put("result", false);
        }
        return res;
    }

}