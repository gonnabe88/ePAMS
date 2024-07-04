package epams.com.member.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import epams.com.member.dto.TempRoleDTO;
import epams.com.member.dto.TempUserDTO;
import epams.com.member.entity.TempUserEntity;
import epams.com.member.repository.MemberJpaRepository;
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
     * @implNote 회원 저장소 주입
     * @since 2024-06-11
     */
    private final MemberJpaRepository memberRepository;

    /**
     * @author K140024
     * @implNote 비밀번호 인코더 주입
     * @since 2024-06-11
     */
    private final PasswordEncoder passwordEncoder; 

    /**
     * @author K140024
     * @implNote 회원 등록 폼을 반환하는 메서드
     * @since 2024-06-11
     */
    @GetMapping("/register")
    public String registryForm(final Model model) {
        model.addAttribute("member", new TempUserDTO());
        return "/common/register";
    }

    /**
     * @author K140024
     * @implNote 회원 등록을 처리하는 메서드
     * @since 2024-06-11
     */
    @PostMapping("/register")
    public String registry(@ModelAttribute final TempUserDTO registryRequest) {
        final TempUserEntity member = TempUserEntity.builder()
                .username(registryRequest.getUsername())
                .password(passwordEncoder.encode(registryRequest.getPassword()))
                .role(registryRequest.getRole())
                .build();
        memberRepository.save(member);

        return "redirect:/common/login";
    }

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
