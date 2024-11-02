package epams.domain.com.login.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import epams.domain.com.login.util.webauthn.authenticator.Authenticator;
import epams.domain.com.login.util.webauthn.service.RegistrationService;
import epams.domain.com.login.util.webauthn.user.AppUser;
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