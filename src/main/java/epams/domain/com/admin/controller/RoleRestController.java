package epams.domain.com.admin.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import epams.domain.com.member.dto.RoleDTO;
import epams.domain.com.member.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/***
 * @author 140024
 * @implNote Rest API 요청처리를 위한 컨트롤러
 * @since 2024-06-09
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class RoleRestController {

    /***
     * @author 140024
     * @implNote 역할 서비스
     * @since 2024-06-09
     */
    private final RoleService roleService;

    /***
     * @author 140024
     * @implNote 모든 데이터를 검색
     * @since 2024-06-09
     */
    @GetMapping("/role")
    public ResponseEntity<List<RoleDTO>> searchRole(final Model model) throws IOException {
        final List<RoleDTO> dtos = roleService.findAll();
        return ResponseEntity.ok(dtos);
    }

    /***
     * @author 140024
     * @implNote 변경된 테이터를 저장
     * @since 2024-06-09
     */
    @PostMapping("/role/save")
    public ResponseEntity<Map<String, String>> saveRole(@RequestBody final Map<String, List<RoleDTO>> payload) {
        final List<RoleDTO> added = payload.get("added");
        final List<RoleDTO> changed = payload.get("changed");
        final List<RoleDTO> deleted = payload.get("deleted");
        roleService.save(added, changed, deleted);

        return ResponseEntity.ok(Map.of("message", "Data saved successfully!"));
    }
}
