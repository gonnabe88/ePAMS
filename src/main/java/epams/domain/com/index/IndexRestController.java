package epams.domain.com.index;

import epams.domain.com.index.dto.UserSearchDTO;
import epams.domain.com.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
@RequestMapping("/api/index")
public class IndexRestController {

    /***
     * @author 140024
     * @implNote 코드 서비스
     * @since 2024-06-09
     */
    private final MemberService memberService;

    /***
     * @author 140024
     * @implNote 모든 코드 데이터를 검색
     * @since 2024-06-09
     */
    @GetMapping("/getUserList")
    public ResponseEntity<Map<String, Object>> getUserLists() {
        // 사용자 목록을 가져옴
        final List<UserSearchDTO> userDTOs = memberService.findAllUser();

        // Map을 생성하여 두 리스트를 담음
        Map<String, Object> response = new ConcurrentHashMap<>();
        response.put("userList", userDTOs);

        // Map을 응답으로 반환
        return ResponseEntity.ok(response);
    }

    /***
     * @author 140024
     * @implNote 현재 시간을 UTC로 반환
     * @since 2024-10-13
     */
    @GetMapping("/current-utc-time")
    public ResponseEntity<ZonedDateTime> getCurrentUtcTime() {
        return ResponseEntity.ok(ZonedDateTime.now(ZoneOffset.UTC)); // 현재 시간을 UTC로 반환
    }

}