package epams.domain.dtm.controller;

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
     * @implNote 연차저축 서비스 주입
     * @since 2024-09-13
     */
    private final DtmApplStatusService dtmApplStatusService;

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
    @PostMapping("/check")
    public ResponseEntity<Map<String, Object>> checkDtm(@RequestBody final DtmHisDTO dto) {

        // 근태 리스트 생성 (@TODO 나중에 기본적으로 DTO List를 받으면 지워도 됨)
        List<DtmHisDTO> dtmHisDTOList = new ArrayList<>();
        dtmHisDTOList.add(dto);

        // 사용자 ID 설정
        final Long empId = Long.parseLong(authentication().getName().replace('K', '7'));
        dtmHisDTOList.forEach(dtmHisDTO -> dtmHisDTO.setEmpId(empId));
        dtmHisDTOList.forEach(dtmHisDTO -> dtmHisDTO.setModUserId(empId));

        // 현재 기준 년도(YYYY) 세팅 ex)2024
        LocalDate currenDate = LocalDate.now();
        String thisYear = String.valueOf(currenDate.getYear());

        // 응답 메시지 설정
        Map<String, Object> response = new ConcurrentHashMap<>();

        try {
/* @TODO 외부 테스트 시 주석 처리(시작)

            // 근태 유형별 합계 시간 데이터 저장용 객체 생성
            final DtmKindSumDTO sumDTO = new DtmKindSumDTO();

            // 연차촉진 관련 데이터 가져오기 (dtm010_03_03_p_07, dtm010_23_03_p_01, dtm010_23_03_p_04)
            final DtmPromotionDTO dtmPromotionDTO = dtmPromotionService.getDtmPromotionYnCheckData(empId, thisYear);

            // 연차저축 관련 데이터 가져오기 (dtm010_03_03_p_08, dtm010_03_03_p_09)
            final DtmSaveDTO dtmSaveDTO = dtmSaveService.getDtmSaveCheckData(empId, thisYear);

            // 근태신청 관련 데이터 가져오기 (dtm010_03_03_p_08)
            final DtmApplStatusDTO statusDTO = dtmApplStatusService.getApplStatus(empId, thisYear);

            // 근태 체크 로직 수행
            Boolean adUseYn = dtmApplyService.check(dtmHisDTOList, dtmPromotionDTO, dtmSaveDTO, statusDTO, sumDTO);

            // 서비스 호출 및 결과 메시지 설정
            response.put("adUseYn", adUseYn); // 선연차 사용 동의
            response.put("annualSum", sumDTO.getAnnualDaySum()); // 신청시간
            response.put("annualUsedCnt", statusDTO.getAnnualDayUsedCnt()); // 기 사용시간
            response.put("annualTotalCnt", statusDTO.getAnnualDayTotalCnt()); // 총 보유시간

 @TODO 외부 테스트 시 주석 처리(끝) */
            response.put("dtmHisDTOList", dtmHisDTOList); // 근태신청 리스트
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (CustomGeneralRuntimeException e) {
            // 비즈니스 로직 오류 처리
            e.printStackTrace();
            log.warn("CustomGeneralRuntimeException");
            log.error(e.getMessage());
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            // 일반 예외 처리
            e.printStackTrace();
            log.warn("Exception");
            log.error(e.getMessage());
            response.put("error", "서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /***
     * @author 140024
     * @implNote 근태 신청
     * @since 2024-06-09
     */
    @PostMapping("/appl")
    public ResponseEntity<Map<String, String>> applyDtm(@RequestBody final List<DtmHisDTO> dto) throws IOException {    

        // 응답 메시지 설정
        Map<String, String> response = new ConcurrentHashMap<>();

        try {
            // 서비스 호출 및 결과 메시지 설정
            String resultMessage = dtmApplyService.insert(dto);
            response.put("message", resultMessage);

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
