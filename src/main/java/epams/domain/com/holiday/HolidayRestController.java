package epams.domain.com.holiday;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import epams.domain.dtm.dto.DtmApplStatusDTO;
import epams.domain.dtm.dto.DtmHisDTO;
import epams.domain.dtm.dto.DtmKindSumDTO;
import epams.domain.dtm.dto.DtmPromotionDTO;
import epams.domain.dtm.dto.DtmSaveDTO;
import epams.domain.dtm.service.DtmApplStatusService;
import epams.domain.dtm.service.DtmApplyService;
import epams.domain.dtm.service.DtmPromotionService;
import epams.domain.dtm.service.DtmSaveService;
import epams.framework.exception.CustomGeneralRuntimeException;
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
@RequestMapping("/api/com")
public class HolidayRestController {

    /***
     * @author 140024
     * @implNote 멤버 서비스
     * @since 2024-06-09
     */
    private final HolidayService holidayService;


    /**
     * @author K140024
     * @implNote 현재 인증된 사용자 정보를 가져오는 메소드
     * @since 2024-04-26
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
     * @implNote 근태 신청내역 검사
     * @since 2024-09-20
     */
    @GetMapping("/getHoliList")
    public ResponseEntity<Map<String, Object>> getHoliList() throws IOException {
        
        final HolidayDTO holiDTO = new HolidayDTO();

        // 현재 기준 년도(YYYY) 세팅 ex)2024 
        LocalDate currenDate = LocalDate.now();
        String thisYear = String.valueOf(currenDate.getYear());

        // 응답 메시지 설정
        Map<String, Object> response = new ConcurrentHashMap<>();

        try {
            // 근태 유형별 합계 시간 데이터 저장용 객체 생성
            final List<String> holiDayList = holidayService.findholiYmd();

            // 서비스 호출 및 결과 메시지 설정
            response.put("holiDayList", holiDayList);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (CustomGeneralRuntimeException e) {
            // 비즈니스 로직 오류 처리
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            // 일반 예외 처리
            // e.printStackTrace();
            response.put("error", "서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
