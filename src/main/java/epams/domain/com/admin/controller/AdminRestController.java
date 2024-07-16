package epams.domain.com.admin.controller;

import epams.domain.com.admin.dto.LogLoginDTO;
import epams.domain.com.admin.service.LogService;
import epams.domain.com.member.dto.IamUserDTO;
import epams.domain.com.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
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
@RequestMapping("/api/admin")
public class AdminRestController {

    /***
     * @author 140024
     * @implNote 멤버 서비스
     * @since 2024-06-09
     */
    private final MemberService memberService2;

    /***
     * @author 140024
     * @implNote 로그 서비스
     * @since 2024-06-09
     */
    private final LogService logService;

    /***
     * @author 140024
     * @implNote 모든 로그인 로그 데이터를 검색
     * @since 2024-06-09
     */
    @GetMapping("/login")
    public ResponseEntity<List<LogLoginDTO>> searchAllLoginLog(final Model model) throws IOException {
        final List<LogLoginDTO> logloginDTOs = logService.findAll();
        return ResponseEntity.ok(logloginDTOs);
    }

    /***
     * @author 140024
     * @implNote 모든 멤버 데이터를 검색
     * @since 2024-06-09
     */
    @GetMapping("/member")
    public ResponseEntity<List<IamUserDTO>> searchAllMember(final Model model) throws IOException {
        final List<IamUserDTO> memberList = memberService2.findAll();
        final Map<String, Object> data = new ConcurrentHashMap<>();
        data.put("data", memberList);

        // 마지막 페이지 및 데이터 설정
        final Map<String, Object> response = new ConcurrentHashMap<>();
        //response.put("last_page", 10);
        response.put("data", memberList);

        return ResponseEntity.ok(memberList);
    }

}
