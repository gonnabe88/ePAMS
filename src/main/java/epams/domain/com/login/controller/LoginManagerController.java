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
     * Return 뷰(html) 파일 경로
     */
    private String VIEW_URL;
    {
        VIEW_URL = "/common/index";
    }
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

    	VIEW_URL = "common/loginManager";

        // 현재 로그인한 사용자 정보
        final Authentication auth = authentication();

        // 간편인증 등록 여부 확인
        final AppUser existingUser = service.getUserRepo().findByUsername(auth.getName());
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

        return VIEW_URL;
    }

    /**
     * 비밀번호(+ MFA) 로그인 처리
     * 
     * @return 응답 데이터 맵
     * @throws Exception 예외 발생 시
     */
    @PostMapping("/loginManager")
    @ResponseBody
    public Map<String, Object> pwlogin(final HttpServletResponse response, @ModelAttribute final IamUserDTO param, final Model model)
    {
        // Front-end에서 ID가 대문자로 바뀌지 않는 경우에 대비하여 한번 더 대문자 변환 처리
        if (param != null && param.getUsername() != null) {
            final String uppercaseUsername = param.getUsername().toUpperCase(Locale.getDefault());
            param.setUsername(uppercaseUsername);
        }

		final AppUser existingUser = service.getUserRepo().findByUsername(Objects.requireNonNull(param).getUsername());
        final Map<String, Object> res = new ConcurrentHashMap<>();
        if (loginService.pwLogin(param)) {

            // 사용자 정보 가져오기
            final IamUserDTO iamUserDTO = memberService.findUserByUserNo(param.getUsername());
            iamUserDTO.setMFA(param.getMFA());
            iamUserDTO.setAdmin(param.isAdmin());

            // 마스킹된 휴대폰 번호 설정
            final String maskedPhoneNo = MaskPhoneNoUtil.maskPhoneNo(iamUserDTO.getPhoneNo());
            res.put("maskedPhoneNo", maskedPhoneNo);

            // 유효하지 않은 휴대폰 번호인 경우 (9자리 이하)
            if(iamUserDTO.getPhoneNo().length() <= 9)
                throw new CustomGeneralRuntimeException("유효한 휴대폰 번호가 등록되어있지 않습니다.("+maskedPhoneNo+")");

            // PW 로그인 성공 시 MFA 로그인 진행
            try {
				restapiservice.requestMFA(iamUserDTO);
			} catch (NoSuchAlgorithmException e) {
	            throw new CustomGeneralRuntimeException("NoSuchAlgorithmException : restapiservice.requestMFA(iamUserDTO)", e);
			}
            // 로그인 성공 시 추가 인증 단계로 넘어가기 위해 성공 여부를 반환
            res.put("result", true);

            if (existingUser == null) {
                res.put("simpleauth", false);
            }
            else {
                res.put("simpleauth", true);
            }

        } else {
            // 로그인 실패 시 실패 여부를 반환
            //res.put("result", false);
            throw new CustomGeneralRuntimeException("인증 정보가 유효하지 않습니다.");
        }
        return res;
    }

}