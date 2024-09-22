package epams.domain.dtm.controller;

import epams.domain.com.admin.service.HtmlLangDetailService;
import epams.domain.dtm.dto.DtmHisDTO;
import epams.domain.dtm.dto.DtmPromotionDTO;
import epams.domain.dtm.dto.DtmSaveDTO;
import epams.domain.dtm.service.DtmHistoryService;
import epams.domain.dtm.service.DtmPromotionService;
import epams.domain.dtm.service.DtmRevokeService;
import epams.domain.dtm.service.DtmSaveService;
import epams.framework.exception.CustomGeneralRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author K140024
 * @implNote 근태관리 컨트롤러
 * @since 2024-06-11
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dtm")
public class DtmRevokeRestController<S extends Session> {

    /**
     * @author K140024
     * @implNote 근태 취소 서비스 주입
     * @since 2024-06-11
     */
    private final DtmRevokeService dtmRevokeService;

    /**
     * @author K140024
     * @implNote 연차촉진 서비스 주입
     * @since 2024-06-11
     */
    private final DtmPromotionService dtmPromotionService;

    /**
     * @author K140024
     * @implNote 연차저축 서비스 주입
     * @since 2024-09-13
     */
    private final DtmSaveService dtmSaveService;

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
    @PostMapping("/dtmRevoke")
     public ResponseEntity<Map<String, String>> dtmRevoke(@RequestBody final DtmHisDTO dto) throws IOException {

        // 사용자 ID 설정
        final Long empId = Long.parseLong(authentication().getName().replace('K', '7'));
        dto.setEmpId(empId);
        dto.setModUserId(empId);

        // 날짜 포메터 설정 (YYYY-MM-DD 형식)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 현재 기준 년도(YYYY) 세팅 ex)2024
        LocalDate currenDate = LocalDate.now();
        String thisYear = String.valueOf(currenDate.getYear());

        // 응답 메시지 설정
        Map<String, String> response = new ConcurrentHashMap<>();        

        try {

            // 연차촉진 관련 데이터 가져오기
            final DtmPromotionDTO dtmPromotionDTO = dtmPromotionService.getDtmPromotionYnCheckData(empId, thisYear);

            // 연차촉진 기간인 경우
            if("Y".equals(dtmPromotionDTO.getPromotionYn())) {
                // 연차저축 관련 데이터 가져오기
                final DtmSaveDTO dtmSaveDTO = dtmSaveService.getDtmSaveCheckData(empId, thisYear);
                // 연차촉진 및 저축 관련 유효성 체크
                dtmRevokeService.check(dto, dtmPromotionDTO, dtmSaveDTO);
            }

            // 서비스 호출 및 결과 메시지 설정
            String resultMessage = dtmRevokeService.revoke(dto);

            response.put("message", resultMessage);
            response.put("dtmReasonNm", dto.getDtmReasonNm());
            response.put("staYmd",  dto.getStaYmd().toLocalDate().format(formatter));
            response.put("endYmd",  dto.getEndYmd().toLocalDate().format(formatter));
            return new ResponseEntity<>(response, HttpStatus.OK);
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
