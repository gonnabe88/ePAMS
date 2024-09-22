package epams.domain.dtm.controller;

import epams.domain.com.admin.service.HtmlLangDetailService;
import epams.domain.com.board.service.BoardMainService;
import epams.domain.com.commonCode.CommonCodeService;
import epams.domain.dtm.dto.DtmHisDTO;
import epams.domain.dtm.service.DtmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author K140024
 * @implNote 근태관리 컨트롤러
 * @since 2024-06-11
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/dtm")
public class DtmPopupController<S extends Session> {

    /**
     * @author K140024
     * @implNote 코드 상세 서비스 주입
     * @since 2024-06-11
     */
    private final HtmlLangDetailService langDetailService;

    /**
     * @author K140024
     * @implNote DTM 신청팝업 메서드
     * @since 2024-06-11
     */
    @GetMapping("/dtmModifyPopup")
    public String dtmModifyPopup(
            @RequestParam("dtmKindCd") String dtmKindCd,
            @RequestParam("dtmReasonCd") String dtmReasonCd,
            @RequestParam("staYmd") String staYmd,
            @RequestParam("endYmd") String endYmd,
            @RequestParam("dtmDispName") String dtmDispName,
            @PageableDefault(page = 1) final Pageable pageable,
            final Model model) {

        final String VIEW = "dtm/dtmModifyPopup";
        // 언어목록 조회
        final Map<String, String> langList = langDetailService.getCodeHtmlDetail(VIEW);
        model.addAttribute("langList", langList);

        // 날짜 형식이 ISO-8601 형식인 경우
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        // DtmHisDTO 객체를 생성하여 필요한 로직에 활용 가능
        DtmHisDTO dto = new DtmHisDTO();
        dto.setDtmKindCd(dtmKindCd);
        dto.setDtmReasonCd(dtmReasonCd);
        dto.setDtmDispName(dtmDispName);

        // 날짜 문자열을 LocalDateTime으로 변환
        try {
            dto.setStaYmd(LocalDateTime.parse(staYmd, formatter));
            dto.setEndYmd(LocalDateTime.parse(endYmd, formatter));
        } catch (DateTimeParseException e) {
            // 날짜 파싱 실패 시 처리 (예: 기본값 설정 또는 예외 처리)
            model.addAttribute("error", "Invalid date format");
            return "errorView";
        }

        // 필요한 로직 처리 후 모델에 추가
        model.addAttribute("dtmHisDTO", dto);

        // 기타 필요한 모델 속성 설정
        final LocalDateTime staDay = dto.getStaYmd();
        final LocalDateTime endDay = dto.getEndYmd();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dayOfWeekFormatter = DateTimeFormatter.ofPattern("E", Locale.KOREAN);

        String dateRange;

        if (staDay.isEqual(endDay)) {
            // 시작일과 종료일이 같은 경우
            dateRange = staDay.format(dateFormatter) + "(" + staDay.format(dayOfWeekFormatter) + ")";
        } else {
            // 시작일과 종료일이 다른 경우
            String formattedStaYmd = staDay.format(dateFormatter) + "(" + staDay.format(dayOfWeekFormatter) + ")";
            String formattedEndYmd = endDay.format(dateFormatter) + "(" + endDay.format(dayOfWeekFormatter) + ")";
            dateRange = formattedStaYmd + " ~ " + formattedEndYmd;
            // 시작일과 종료일의 차이를 계산
            //long daysBetween = ChronoUnit.DAYS.between(staDay, endDay) + 1;

            // dtmDispName에 몇 일인지를 추가
            //dtmDispName += " (" + daysBetween + "일)";
        }

        model.addAttribute("nowDate", dateRange);
        model.addAttribute("dtmDispName", dtmDispName);

        return VIEW; // View 이름 반환
    }

    /**
     * @author K140024
     * @implNote DTM 신청팝업 메서드
     * @since 2024-06-11
     */
    @GetMapping("/dtmApplPopup")
    public String dtmApplPopup(
            @RequestParam("dtmKindCd") String dtmKindCd,
            @RequestParam("dtmReasonCd") String dtmReasonCd,
            @RequestParam("staYmd") String staYmd,
            @RequestParam("endYmd") String endYmd,
            @RequestParam("dtmDispName") String dtmDispName,
            @PageableDefault(page = 1) final Pageable pageable,
            final Model model) {

        final String VIEW = "dtm/dtmApplPopup";

        // 날짜 형식이 ISO-8601 형식인 경우
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        // DtmHisDTO 객체를 생성하여 필요한 로직에 활용 가능
        DtmHisDTO dto = new DtmHisDTO();
        dto.setDtmKindCd(dtmKindCd);
        dto.setDtmReasonCd(dtmReasonCd);
        dto.setDtmDispName(dtmDispName);

        // 날짜 문자열을 LocalDateTime으로 변환
        try {
            dto.setStaYmd(LocalDateTime.parse(staYmd, formatter));
            dto.setEndYmd(LocalDateTime.parse(endYmd, formatter));
        } catch (DateTimeParseException e) {
            // 날짜 파싱 실패 시 처리 (예: 기본값 설정 또는 예외 처리)
            model.addAttribute("error", "Invalid date format");
            return "errorView";
        }

        // 필요한 로직 처리 후 모델에 추가
        model.addAttribute("dtmHisDTO", dto);

        // 기타 필요한 모델 속성 설정
        final LocalDateTime staDay = dto.getStaYmd();
        final LocalDateTime endDay = dto.getEndYmd();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dayOfWeekFormatter = DateTimeFormatter.ofPattern("E", Locale.KOREAN);

        String dateRange;

        if (staDay.isEqual(endDay)) {
            // 시작일과 종료일이 같은 경우
            dateRange = staDay.format(dateFormatter) + "(" + staDay.format(dayOfWeekFormatter) + ")";
        } else {
            // 시작일과 종료일이 다른 경우
            String formattedStaYmd = staDay.format(dateFormatter) + "(" + staDay.format(dayOfWeekFormatter) + ")";
            String formattedEndYmd = endDay.format(dateFormatter) + "(" + endDay.format(dayOfWeekFormatter) + ")";
            dateRange = formattedStaYmd + " ~ " + formattedEndYmd;
            // 시작일과 종료일의 차이를 계산
            //long daysBetween = ChronoUnit.DAYS.between(staDay, endDay) + 1;

            // dtmDispName에 몇 일인지를 추가
            //dtmDispName += " (" + daysBetween + "일)";
        }

        model.addAttribute("nowDate", dateRange);
        model.addAttribute("dtmDispName", dtmDispName);

        return VIEW; // View 이름 반환
    }

    @PostMapping("/dtmApplListPopup")
    public String dtmListApplPopup(@RequestBody List<DtmHisDTO> dtmHisDTOList, final Model model) {

        final String VIEW = "dtm/dtmApplListPopup";

        // 기타 필요한 모델 속성 설정
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dayOfWeekFormatter = DateTimeFormatter.ofPattern("E", Locale.KOREAN);
        LocalDateTime staDay ;
        LocalDateTime endDay ;
        String dateRange; 

        for(DtmHisDTO dto : dtmHisDTOList) {
            staDay = dto.getStaYmd();
            endDay = dto.getEndYmd();
            if (staDay.isEqual(endDay)) {
                // 시작일과 종료일이 같은 경우
                dateRange = staDay.format(dateFormatter) + "(" + staDay.format(dayOfWeekFormatter) + ")";
            } else {
                // 시작일과 종료일이 다른 경우
                String formattedStaYmd = staDay.format(dateFormatter) + "(" + staDay.format(dayOfWeekFormatter) + ")";
                String formattedEndYmd = endDay.format(dateFormatter) + "(" + endDay.format(dayOfWeekFormatter) + ")";
                dateRange = formattedStaYmd + " ~ " + formattedEndYmd;
            }
            dto.setDtmRange(dateRange);
        }

        model.addAttribute("dtmHisDTOList", dtmHisDTOList);

        return VIEW; // View 이름 반환
    }

}
