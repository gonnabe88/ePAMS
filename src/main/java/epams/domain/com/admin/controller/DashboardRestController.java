package epams.domain.com.admin.controller;

import epams.domain.com.admin.dto.CountByCategoryDTO;
import epams.domain.com.admin.dto.CountByDateDTO;
import epams.domain.com.admin.service.DashboardService;
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

/***
 * @author 140024
 * @implNote Rest API 요청처리를 위한 컨트롤러
 * @since 2024-06-09
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class DashboardRestController {

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
    private final DashboardService dashboardService;

    /***
     * @author 140024
     * @implNote 모든 로그인 로그 데이터를 검색
     * @since 2024-06-09
     */
    @GetMapping("/loginUserCount")
    public ResponseEntity<List<CountByDateDTO>> getLoginUserCountsByDate(final Model model) throws IOException {
        final List<CountByDateDTO> countByDateDTO = dashboardService.getLoginUserCountsByDate();
        return ResponseEntity.ok(countByDateDTO);
    }

    /***
     * @author 140024
     * @implNote 모든 로그인 로그 데이터를 검색
     * @since 2024-06-09
     */
    @GetMapping("/logincount")
    public ResponseEntity<List<CountByDateDTO>> getLoginCountsByDate(final Model model) throws IOException {
        final List<CountByDateDTO> countByDateDTO = dashboardService.getLoginCountsByDate();
        return ResponseEntity.ok(countByDateDTO);
    }

    /***
     * @author 140024
     * @implNote 모든 로그인 로그 데이터를 검색
     * @since 2024-06-09
     */
    @GetMapping("/viewcount")
    public ResponseEntity<List<CountByDateDTO>> getViewCountsByDate(final Model model) throws IOException {
        final List<CountByDateDTO> countByDateDTO = dashboardService.getViewCountsByDate();
        return ResponseEntity.ok(countByDateDTO);
    }

    /***
     * @author 140024
     * @implNote 모든 로그인 로그 데이터를 검색
     * @since 2024-06-09
     */
    @GetMapping("/logintype")
    public ResponseEntity<List<CountByCategoryDTO>> getLoginTypeCountByCategory(final Model model) throws IOException {
        final List<CountByCategoryDTO> countByCategoryDTO = dashboardService.getLoginTypeCountByCategory();
        return ResponseEntity.ok(countByCategoryDTO);
    }

}
