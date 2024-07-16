package epams.domain.com.member.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import epams.domain.com.member.dto.TempRoleDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import lombok.RequiredArgsConstructor;

/**
 * @author K140024
 * @implNote 회원 등록 및 역할 설정을 관리하는 컨트롤러 클래스
 * @since 2024-06-11
 */
@RequiredArgsConstructor
@Controller
public class MemberController {

    /**
     * @author K140024
     * @implNote 비밀번호 인코더 주입
     * @since 2024-06-11
     */
    private final PasswordEncoder passwordEncoder; 

    /**
     * @author K140024
     * @implNote 회원 역할을 설정하는 메서드
     * @since 2024-06-11
     */
    @ModelAttribute("roles")
    public Map<String, TempRoleDTO> roles() {
        final Map<String, TempRoleDTO> map = new ConcurrentHashMap<>();
        map.put("ADMIN", TempRoleDTO.ROLE_ADMIN);
        map.put("KDB", TempRoleDTO.ROLE_NORMAL);
        return map;
    }
}
