package epams.domain.dtm.controller;

import epams.domain.com.commonCode.CommonCodeService;
import epams.domain.com.holiday.HolidayService;
import epams.domain.dtm.dto.DtmAnnualStatusDTO;
import epams.domain.dtm.dto.DtmCalendarDTO;
import epams.domain.dtm.dto.DtmSearchDTO;
import epams.domain.dtm.service.DtmAnnualStatusService;
import epams.domain.dtm.service.DtmHistoryService;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import epams.domain.com.admin.service.HtmlLangDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author K210058
 * @implNote 근태신청 컨트롤러
 * @since 2024-08-27
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/dtm")
public class DtmRegController<S extends Session> {

    /**
     * @author K210058
     * @implNote 근태신청 컨트롤러
     * @since 2024-08-27
     */
    private final HtmlLangDetailService langDetailService;
    private static final Logger logger = LoggerFactory.getLogger(DtmRegController.class);
    private final CommonCodeService commonCodeService;

    /**
     * @author K140024
     * @implNote 근태조회 서비스
     * @since 2024-06-11
     */
    private final DtmHistoryService dtmHisService;

    /***
     * @author 140024
     * @implNote 휴일 서비스
     * @since 2024-06-09
     */
    private final HolidayService holidayService;

    /**
     * @author K140024
     * @implNote DTM 서비스 주입
     * @since 2024-06-11
     */
    private final DtmAnnualStatusService dtmAnnualStatusService;

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

    /**
     * @author K210058
     * @implNote 근태신청 컨트롤러
     * @since 2024-08-27
     */
    @GetMapping("/dtmReg")
    public String dtmReg(final Model model) {

        // 언어목록
        final Map<String, String> langList = langDetailService.getCodeHtmlDetail("dtm/dtmReg");
        model.addAttribute("langList", langList);
        
        // 근태목록
        final DtmSearchDTO searchDTO = new DtmSearchDTO();
        searchDTO.setEmpId(Long.parseLong(authentication().getName().replace('K', '7')));
        final List<DtmCalendarDTO> dtmCalDTOList = dtmHisService.findByYears(searchDTO);

        try {
            // 휴가보유 현황
            final DtmAnnualStatusDTO dtmAnnualStatusDTO = dtmAnnualStatusService.getDtmAnnualStatus(searchDTO.getEmpId());
            DtmAnnualStatusDTO.removeBracket(dtmAnnualStatusDTO);
            model.addAttribute("dtmAnnualStatus", dtmAnnualStatusDTO);
        } catch (Exception e) {
            final DtmAnnualStatusDTO dtmAnnualStatusDTO = new DtmAnnualStatusDTO();
            model.addAttribute("dtmAnnualStatus", dtmAnnualStatusDTO);
            log.error("휴가보유 현황 조회 실패", e);
        }

        // 휴일목록
        final List<String> holiDayList = holidayService.findholiYmd();

        // Jackson을 사용하여 List<DtmCalendarDTO>를 JSON으로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.registerModule(new JavaTimeModule());  // Java 8 날짜 및 시간 모듈 등록
            // 기본적으로 ISO 형식(yyyy-MM-dd)으로 변환되도록 설정
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            String dtmHisJson = objectMapper.writeValueAsString(dtmCalDTOList);
            model.addAttribute("dtmHisEvents", dtmHisJson);
            model.addAttribute("holiDayList", holiDayList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return "dtm/dtmReg";
    }

    @PostMapping("/submitDtmReg")
    public String submitDtmReg(@RequestParam("startDate") String startDate,
                               @RequestParam("endDate") String endDate,
                               @RequestParam("dtmReasonCd") String dtmReasonCd,
                               Model model) {

        final String DTMREGDET = "dtm/dtmApplPopup";

        // 근태 사유코드명 조회
        String codeName = commonCodeService.getCodeName("DTM_KIND", dtmReasonCd);

        // 파라미터를 모델에 추가하여 팝업으로 전달할 데이터 구성
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("dtmKind", "DTM01");
        model.addAttribute("dtmReasonCd", dtmReasonCd);
        model.addAttribute("dtmReasonCdName", codeName);

        return DTMREGDET;
    }

}
