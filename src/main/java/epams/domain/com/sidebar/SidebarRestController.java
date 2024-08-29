package epams.domain.com.sidebar;

import epams.domain.com.index.dto.TeamSearchDTO;
import epams.domain.com.member.service.MemberService;
import epams.domain.com.sidebar.dto.UserInfoDTO;
import epams.domain.com.sidebar.service.SidebarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/***
 * @author 140024
 * @implNote Rest API 요청처리를 위한 컨트롤러
 * @since 2024-06-09
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sidebar/")
public class SidebarRestController {

    /***
     * @author 140024
     * @implNote 코드 서비스
     * @since 2024-06-09
     */
    private final SidebarService sidebarService;

    /**
     * @author K140024
     * @implNote 현재 인증된 사용자 정보를 반환하는 메서드
     * @since 2024-06-11
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

    /***
     * @author 140024
     * @implNote 현재 로그인된 사용자정보 조회
     * @since 2024-06-09
     */
    @GetMapping("/getUserInfo")
    public ResponseEntity<Map<String, UserInfoDTO>> getUserInfo() {

        // 현재 로그인한 사용자 정보
        final Authentication auth = authentication();

        // 사용자 정보를 가져오는 로직
        UserInfoDTO userInfo = sidebarService.findByUserNo(auth.getName());

        // 사용자 정보 객체 전달 
        Map<String, UserInfoDTO> userInfoMap = new ConcurrentHashMap<>();
        userInfoMap.put("userInfo", userInfo);
        return ResponseEntity.ok(userInfoMap);
    }

}