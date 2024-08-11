package epams.domain.dtm.controller;

import epams.domain.dtm.service.DtmApplyService;
import epams.domain.dtm.dto.DtmHisDTO;
import epams.framework.exception.CustomGeneralRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
@RequestMapping("/api/dtm")
public class DtmApplRestController {

    /***
     * @author 140024
     * @implNote 멤버 서비스
     * @since 2024-06-09
     */
    private final DtmApplyService dtmApplyService;
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
     * @implNote 모든 멤버 데이터를 검색
     * @since 2024-06-09
     */
    @PostMapping("/appl")
    public ResponseEntity<Map<String, String>> applyDtm(@RequestBody final DtmHisDTO dto) throws IOException {
        // 사용자 ID 설정
        dto.setEmpId(Long.parseLong(authentication().getName().replace('K', '7')));
        dto.setModUserId(Long.parseLong(authentication().getName().replace('K', '7')));

        // 날짜를 YYYY-MM-DD 형식으로 포맷팅
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 응답 메시지 설정
        Map<String, String> response = new ConcurrentHashMap<>();

        try {
            // 서비스 호출 및 결과 메시지 설정
            String resultMessage = dtmApplyService.insert(dto);
            response.put("message", resultMessage);
            response.put("staYmd",  dto.getStaYmd().toLocalDate().format(formatter));
            response.put("endYmd",  dto.getEndYmd().toLocalDate().format(formatter));
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (CustomGeneralRuntimeException e) {
            // 비즈니스 로직 오류 처리
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            // 일반 예외 처리
            response.put("error", "서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
