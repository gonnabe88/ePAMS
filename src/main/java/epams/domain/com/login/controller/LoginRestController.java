package epams.domain.com.login.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import epams.domain.com.login.util.webauthn.user.AppUser;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import epams.framework.exception.CustomGeneralRuntimeException;
import epams.domain.com.board.dto.BoardDTO;
import epams.domain.com.board.service.BoardMainService;
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
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/login")
public class LoginRestController {

    /**
     * Webauthn 등록 서비스
     */
    private final RegistrationService service;

    /**
     * 로그인 페이지 요청 처리
     * 
     * @return 뷰 이름
     * @throws Exception 예외 발생 시
     */
    @GetMapping("/isWebauthn")
    public ResponseEntity<Map<String, Object>> isWebauthn(@RequestParam("username") String username) {

        final AppUser existingUser = service.getUserRepo().findByUsername(username);
        final Authenticator existingAuthUser = service.getAuthRepository().findByUser(existingUser);

        // Map을 생성하여 두 리스트를 담음
        Map<String, Object> response = new ConcurrentHashMap<>();

        if (existingAuthUser == null) {
            response.put("isWebauthn", "N");
        } else {
            response.put("isWebauthn", "Y");
        }

        return ResponseEntity.ok(response);
    }

}