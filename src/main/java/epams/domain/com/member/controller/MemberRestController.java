package epams.domain.com.member.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import epams.domain.com.member.dto.IamUserDTO;
import epams.domain.com.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author K140024
 * @implNote 회원 관련 처리를 위한 Rest 컨트롤러
 * @since 2024-06-11
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class MemberRestController {

    /**
     * @author K140024
     * @implNote MemberDetailsService 주입
     * @since 2024-06-11
     */
    private final MemberService memberservice;

    /**
     * @author K140024
     * @implNote 회원 검색을 처리하는 메서드
     * @since 2024-06-11
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(final Model model, @RequestParam("text") final String text) {
        final List<IamUserDTO> list = memberservice.findBySearchValue(text);
        final Map<String, Object> response = new ConcurrentHashMap<>();
        response.put("memberList", list);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/deptlist")
    public ResponseEntity<Map<String, Object>> searchAllDept(final Model model) {
        final List<IamUserDTO> deptlist = memberservice.findAllDept();
        final Map<String, Object> response = new ConcurrentHashMap<>();
        response.put("response", deptlist);
        return ResponseEntity.ok(response);
    }
}
