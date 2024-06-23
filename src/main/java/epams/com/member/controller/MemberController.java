package epams.com.member.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import epams.com.member.dto.MemberDTO;
import epams.com.member.dto.MemberRole;
import epams.com.member.entity.MemberEntity;
import epams.com.member.repository.MemberRepository;
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
    private final MemberRepository memberRepository;

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
    public String registryForm(Model model) {
        model.addAttribute("member", new MemberDTO());
        return "/common/register";
    }

    /**
     * @author K140024
     * @implNote 회원 등록을 처리하는 메서드
     * @since 2024-06-11
     */
    @PostMapping("/register")
    public String registry(@ModelAttribute MemberDTO registryRequest) {
        MemberEntity member = MemberEntity.builder()
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
    public Map<String, MemberRole> roles() {
        Map<String, MemberRole> map = new LinkedHashMap<>();
        map.put("ADMIN", MemberRole.ROLE_ADMIN);
        map.put("KDB", MemberRole.ROLE_KDB);
        map.put("ITO", MemberRole.ROLE_ITO);
        return map;
    }
}
