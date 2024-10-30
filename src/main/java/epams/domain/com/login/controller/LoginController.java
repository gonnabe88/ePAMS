package epams.domain.com.login.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import epams.domain.com.login.util.webauthn.user.AppUser;
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

import epams.framework.exception.CustomGeneralRuntimeException;
import epams.framework.exception.CustomLoginFailException;
import epams.domain.com.admin.dto.LogLoginDTO;
import epams.domain.com.admin.repository.LogRepository;
import epams.domain.com.admin.service.LogService;
import epams.domain.com.board.dto.BoardDTO;
import epams.domain.com.board.service.BoardMainService;
import epams.domain.com.login.dto.LoginOTPDTO;
import epams.domain.com.login.service.LoginService;
import epams.domain.com.login.service.MFALoginService;
import epams.domain.com.login.util.webauthn.service.RegistrationService;
import epams.domain.com.login.util.MaskPhoneNoUtil;
import epams.domain.com.login.util.webauthn.authenticator.Authenticator;
import epams.domain.com.member.dto.IamUserDTO;
import epams.domain.com.member.service.MemberService;
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
     * @author K140024
     * @implNote 로그인 기록 저장소 주입
     * @since 2024-06-11
     */
    private final LogService logService;

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
     * @author K140024
     * @implNote 게시글 관련 서비스
     * @since 2024-04-26
     */
    private final BoardMainService boardService;

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
     * @author K140024
     * @implNote 페이징 처리된 게시글 목록을 보여주는 메소드
     * @since 2024-04-26
     */
    @GetMapping("/login/faq")
    public String findAllFaq(final Model model) {
        final List<BoardDTO> boardList = boardService.findAllFaq();
        model.addAttribute("boardList", boardList);
        return "common/sub/loginFaq";
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
        return "redirect:login";
    }
    

    /**
     * 로그인 페이지 요청 처리
     * 
     * @return 뷰 이름
     * @throws Exception 예외 발생 시
     */
    @GetMapping({ "/", "/login" })
    public String login(final HttpServletResponse response, @CookieValue(value = "USERNAME", required = false) final String username, final Model model) {
    	String VIEW = "common/login";
        final Authentication auth = authentication();
        final AppUser existingUser = service.getUserRepo().findByUsername(username);
        final Authenticator existingAuthUser = service.getAuthRepository().findByUser(existingUser);
        final Boolean loginLock = loginService.checkLoginLock(username);
        model.addAttribute("loginLock", loginLock);
        
        if (existingAuthUser == null) {
            model.addAttribute("webauthn", "N");            
            log.info("Not simple auth user");
        } else {
            model.addAttribute("webauthn", "Y");
            log.info("simple auth user");
        }

        if (auth != null && auth.isAuthenticated()) {
        	VIEW = "redirect:index";
        	
        }

        // 로그인 페이지에 대한 캐시 비활성화 헤더 추가
        // 로그인 후 메인페이지에서 바로 뒤로가기를 눌렀을 때 로그인 페이지가 나타나지 않도록 함
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        return VIEW;
    }

    /**
     * 비밀번호(+ MFA) 로그인 처리
     * 
     * @return 응답 데이터 맵
     * @throws Exception 예외 발생 시
     */
    @PostMapping("/login")
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
			} catch (CustomLoginFailException e) {
	            throw new CustomLoginFailException("NoSuchAlgorithmException : restapiservice.requestMFA(iamUserDTO)", e);
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
            throw new CustomLoginFailException("인증 정보가 유효하지 않습니다.");
        }
        return res;
    }

    /**
     * 비밀번호(+ MFA) 로그인 처리
     * 
     * @return 응답 데이터 맵
     * @throws Exception 예외 발생 시
     */
    @PostMapping("/login/unlock/sendOtp")
    @ResponseBody
    public Map<String, Object> unlockSendOtp(final HttpServletResponse response, @ModelAttribute final IamUserDTO param, final Model model)
    {
        // Front-end에서 ID가 대문자로 바뀌지 않는 경우에 대비하여 한번 더 대문자 변환 처리
        if (param != null && param.getUsername() != null) {
            final String uppercaseUsername = param.getUsername().toUpperCase(Locale.getDefault());
            param.setUsername(uppercaseUsername);
        }

        final Map<String, Object> res = new ConcurrentHashMap<>();

        // 사용자 정보 가져오기
        final IamUserDTO iamUserDTO = memberService.findUserByUserNo(Objects.requireNonNull(param).getUsername());
        iamUserDTO.setMFA(param.getMFA());

        // 마스킹된 휴대폰 번호 설정
        final String maskedPhoneNo = MaskPhoneNoUtil.maskPhoneNo(iamUserDTO.getPhoneNo());
        res.put("maskedPhoneNo", maskedPhoneNo);

        // 유효하지 않은 휴대폰 번호인 경우 (9자리 이하)
        if(iamUserDTO.getPhoneNo().length() <= 9)
            throw new CustomGeneralRuntimeException("유효한 휴대폰 번호가 등록되어있지 않습니다.("+maskedPhoneNo+")");

        // MFA 인증 진행
        try {
            restapiservice.requestMFA(iamUserDTO);
        } catch (CustomLoginFailException e) {
            throw new CustomLoginFailException("NoSuchAlgorithmException : restapiservice.requestMFA(iamUserDTO)", e);
        }
        // 로그인 성공 시 추가 인증 단계로 넘어가기 위해 성공 여부를 반환
        res.put("result", true);

        return res;
    }

    /**
     * 비밀번호(+ MFA) 로그인 처리
     * 
     * @return 응답 데이터 맵
     * @throws Exception 예외 발생 시
     */
    @PostMapping("/login/unlock/authOtp")
    @ResponseBody
    public Map<String, Object> unlockAuthOtp(final HttpServletResponse response, @ModelAttribute final LoginOTPDTO param, final Model model)
    {
        // Front-end에서 ID가 대문자로 바뀌지 않는 경우에 대비하여 한번 더 대문자 변환 처리
        if (param != null && param.getUsername() != null) {
            final String uppercaseUsername = param.getUsername().toUpperCase(Locale.getDefault());
            param.setUsername(uppercaseUsername);
        }

        // 리턴 객체 할당
        final Map<String, Object> res = new ConcurrentHashMap<>();

        // 사용자 정보 가져오기
        final IamUserDTO iamUserDTO = memberService.findUserByUserNo(Objects.requireNonNull(param).getUsername());
        iamUserDTO.setMFA(param.getMfa());

        // OTP 번호 일치 여부 확인
        Boolean result = loginService.otpLogin(iamUserDTO, param.getOtp());

        // OTP 번호 일치 시 잠금 해제 로그 기록
        if(result) {
            logService.insert(LogLoginDTO.getDTO(iamUserDTO.getUsername(), iamUserDTO.getMFA()+"(unlock)", "1"));
        } 

        // OTP 번호 일치 여부 반환
        res.put("result", result);

        return res;
    }

}