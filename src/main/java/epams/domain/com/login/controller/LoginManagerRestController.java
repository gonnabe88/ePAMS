package epams.domain.com.login.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import epams.domain.com.admin.dto.LogLoginDTO;
import epams.domain.com.admin.service.LogService;
import epams.domain.com.sidebar.dto.UserInfoDTO;
import epams.framework.exception.CustomGeneralRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * @author K140024
 * @implNote 인증관리 화면 컨트롤러
 * @since 2024-11-02
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/loginManager")
public class LoginManagerRestController {

    /***
     * @author 140024
     * @implNote 로그 서비스
     * @since 2024-06-09
     */
    private final LogService logService;

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
    @GetMapping("/myLoginLog")
    public ResponseEntity<List<LogLoginDTO>> getMyLoginLog() {

        // 현재 로그인한 사용자 정보
        final Authentication auth = authentication();
        if(auth != null) {
            // 특정 사용자의 로그인 로그 조회
            final List<LogLoginDTO> logloginDTOs = logService.findAllByUsername(auth.getName());
            return ResponseEntity.ok(logloginDTOs);
        } else {
            throw new CustomGeneralRuntimeException("로그인이 만료되었습니다. 로그인 후 사용해주세요");
        }
    }

}