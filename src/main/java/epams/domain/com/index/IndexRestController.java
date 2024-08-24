package epams.domain.com.index;

import epams.domain.com.commonCode.CommonCodeDTO;
import epams.domain.com.commonCode.CommonCodeService;
import epams.domain.com.index.dto.DeptSearchDTO;
import epams.domain.com.index.dto.TeamSearchDTO;
import epams.domain.com.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/api/index/")
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
    @GetMapping("/getDeptList")
    public ResponseEntity<Map<String, Object>> getDeptAndTeamLists() {
        // 부서와 팀 목록을 가져옴
        //final List<DeptSearchDTO> deptDtos = memberService.findAllDept();
        final List<TeamSearchDTO> teamDtos = memberService.findAllTeam();

        // Map을 생성하여 두 리스트를 담음
        Map<String, Object> response = new ConcurrentHashMap<>();
        //response.put("deptList", deptDtos);
        response.put("teamList", teamDtos);

        // Map을 응답으로 반환
        return ResponseEntity.ok(response);
    }

}