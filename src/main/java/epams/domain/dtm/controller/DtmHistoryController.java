package epams.domain.dtm.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import epams.domain.com.admin.service.HtmlLangDetailService;
import epams.domain.com.holiday.HolidayService;
import epams.domain.com.workTime.WorkTimeDTO;
import epams.domain.com.workTime.WorkTimeService;
import epams.domain.dtm.dto.DtmAnnualStatusDTO;
import epams.domain.dtm.dto.DtmCalendarDTO;
import epams.domain.dtm.dto.DtmHisDTO;
import epams.domain.dtm.dto.DtmSearchDTO;
import epams.domain.dtm.service.DtmAnnualStatusService;
import epams.domain.dtm.service.DtmHistoryService;
import epams.framework.exception.CustomGeneralRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author K140024
 * @implNote 근태관리 컨트롤러
 * @since 2024-06-11
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/dtm")
public class DtmHistoryController<S extends Session> {

    /**
     * @author K140024
     * @implNote 모든 페이지네이션 시 노출할 최대 버튼 수 설정값 (application.yml)
     * @since 2024-06-11
     */
    @Value("${kdb.maxPageBtn}")
    private int maxPageBtn;

    /**
     * @author K140024
     * @implNote 코드 상세 서비스 주입
     * @since 2024-06-11
     */
    private final HtmlLangDetailService langDetailService;

    /**
     * @author K140024
     * @implNote 근태조회 서비스
     * @since 2024-06-11
     */
    private final DtmHistoryService dtmHisService;

    /**
     * @author K140024
     * @implNote DTM 서비스 주입
     * @since 2024-06-11
     */
    private final DtmAnnualStatusService dtmAnnualStatusService;

    /***
     * @author 140024
     * @implNote 휴일 서비스
     * @since 2024-06-09
     */
    private final HolidayService holidayService;

    /***
     * @author 210058
     * @implNote 근무시간 서비스
     * @since 2024-10-13
     */
    private final WorkTimeService workTimeService;

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
     * @author K140024
     * @implNote DTM 달력 화면을 반환하는 메서드
     * @since 2024-06-11
     */
    @GetMapping("/dtmCalendar")
    public String dtmCalendar(final Model model) {

        // 언어목록
        final Map<String, String> langList = langDetailService.getCodeHtmlDetail("dtm/dtmCalendar");
        model.addAttribute("langList", langList);

        // 캘린더근태목록
        final DtmSearchDTO searchDTO = new DtmSearchDTO();
        String empIdStr = Optional.ofNullable(authentication())
        		.map(Authentication::getName)
        		.orElse("K000000");
        final Long empId = Long.parseLong(empIdStr.replace('K', '7'));
        searchDTO.setEmpId(empId);
        
        final List<DtmCalendarDTO> dtmCalDTOList = dtmHisService.findByYears(searchDTO);

        // 휴일목록
        final List<String> holiDayList = holidayService.findholiYmd();

        // 근무시간 불러오기
        final WorkTimeDTO workTimeList = workTimeService
                .findWorkTime(empId, LocalDate.now().toString());
        // WorkTimeDTO wk = workTimeList.get(0);
        // System.out.println("근무유형 : "+ workTimeList);
        // String wkText = timeFormat(wk.getStaTime()) + " ~ " +
        // timeFormat(wk.getEndTime());

        // Jackson을 사용하여 List<DtmCalendarDTO>를 JSON으로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.registerModule(new JavaTimeModule()); // Java 8 날짜 및 시간 모듈 등록
            // 기본적으로 ISO 형식(yyyy-MM-dd)으로 변환되도록 설정
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            String dtmHisJson = objectMapper.writeValueAsString(dtmCalDTOList);
            model.addAttribute("dtmHisEvents", dtmHisJson);
            model.addAttribute("holiDayList", holiDayList);
            model.addAttribute("empId", empId);// 근무시간 목록
            // model.addAttribute("workTime", wkText);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return "dtm/dtmCalendar";
    }

    /**
     * @author K140024
     * @implNote DTM 리스트 화면을 반환하는 메서드
     * @since 2024-06-11
     */
    @GetMapping("/dtmList")
    public String dtmList(@PageableDefault(page = 1) final Pageable pageable,
            @ModelAttribute final DtmSearchDTO searchDTO, final Model model) {

        final String DTMLIST = "dtm/dtmList";
        
        String empIdStr = Optional.ofNullable(authentication())
        		.map(Authentication::getName)
        		.orElse("K000000");
        final Long empId = Long.parseLong(empIdStr.replace('K', '7'));
        searchDTO.setEmpId(empId);        

        // 현재 기준 년도(YYYY) 세팅 ex)2024
        LocalDate currenDate = LocalDate.now();
        String thisYear = String.valueOf(currenDate.getYear());

        try {
            // 휴가보유 현황
            final DtmAnnualStatusDTO dtmAnnualStatusDTO = dtmAnnualStatusService
                    .getDtmAnnualStatus(searchDTO.getEmpId(), thisYear);
            DtmAnnualStatusDTO.removeBracket(dtmAnnualStatusDTO);
            dtmAnnualStatusDTO.formatter();
            model.addAttribute("dtmAnnualStatus", dtmAnnualStatusDTO);
        } catch (CustomGeneralRuntimeException e) {
            // 런타임 예외 처리
            // e.printStackTrace();
            final DtmAnnualStatusDTO dtmAnnualStatusDTO = new DtmAnnualStatusDTO();
            model.addAttribute("dtmAnnualStatus", dtmAnnualStatusDTO);
        } catch (Exception e) {
            final DtmAnnualStatusDTO dtmAnnualStatusDTO = new DtmAnnualStatusDTO();
            model.addAttribute("dtmAnnualStatus", dtmAnnualStatusDTO);
        }

        // 언어목록 조회
        final Map<String, String> langList = langDetailService.getCodeHtmlDetail(DTMLIST);
        model.addAttribute("langList", langList);

        // 근태목록 조회 및 페이지네이션 변수 설정
        final int currentPage = pageable.getPageNumber();
        final Pageable updatedPageable = PageRequest.of(currentPage, searchDTO.getItemsPerPage());
        final Page<DtmHisDTO> dtmHisDTOList = dtmHisService.findByCondition(updatedPageable, searchDTO);
        final int totalPages = dtmHisDTOList.getTotalPages();
        int startPage = Math.max(1, currentPage - (maxPageBtn / 2));
        final int endPage = Math.min(totalPages, startPage + maxPageBtn - 1);
        if (endPage - startPage < maxPageBtn - 1) {
            startPage = Math.max(1, endPage - maxPageBtn + 1);
        }

        // 기타 필요한 모델 속성 설정
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dayOfWeekFormatter = DateTimeFormatter.ofPattern("E", Locale.KOREAN);
        LocalDateTime staDay;
        LocalDateTime endDay;
        String dateRange;
        for (DtmHisDTO dto : dtmHisDTOList) {

            // 근태 기간을 깔끔하게 표기하기 위한 작업
            staDay = dto.getStaYmd();
            endDay = dto.getEndYmd();
            if (staDay.isEqual(endDay)) {
                // 시작일과 종료일이 같은 경우 하나만 표기
                dateRange = staDay.format(dateFormatter) + "(" + staDay.format(dayOfWeekFormatter) + ")";
            } else {
                // 시작일과 종료일이 다른 경우 두 날짜를 ~ 구분자로 표기
                String formattedStaYmd = staDay.format(dateFormatter) + "(" + staDay.format(dayOfWeekFormatter) + ")";
                String formattedEndYmd = endDay.format(dateFormatter) + "(" + endDay.format(dayOfWeekFormatter) + ")";
                dateRange = formattedStaYmd + " ~ " + formattedEndYmd;
            }
            dto.setDtmRange(dateRange);
        }

        // 캘린더근태목록
        final List<DtmCalendarDTO> dtmCalDTOList = dtmHisService.findByYears(searchDTO);

        // 휴일목록
        final List<String> holiDayList = holidayService.findholiYmd();

        // Jackson을 사용하여 List<DtmCalendarDTO>를 JSON으로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.registerModule(new JavaTimeModule()); // Java 8 날짜 및 시간 모듈 등록
            // 기본적으로 ISO 형식(yyyy-MM-dd)으로 변환되도록 설정
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            String dtmHisJson = objectMapper.writeValueAsString(dtmCalDTOList);
            model.addAttribute("dtmHisEvents", dtmHisJson);
            model.addAttribute("holiDayList", holiDayList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        model.addAttribute("dtmHis", dtmHisDTOList);
        model.addAttribute("searchDTO", searchDTO);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("itemsPerPage", searchDTO.getItemsPerPage());

        return DTMLIST;
    }
}
