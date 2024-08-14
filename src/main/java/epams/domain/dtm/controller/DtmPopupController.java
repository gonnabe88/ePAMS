package epams.domain.dtm.controller;

import epams.domain.com.admin.service.CodeHtmlDetailService;
import epams.domain.com.board.dto.BoardDTO;
import epams.domain.com.board.service.BoardMainService;
import epams.domain.com.commonCode.CommonCodeService;
import epams.domain.dtm.dto.DtmHisDTO;
import epams.domain.dtm.service.DtmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
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
     * @author K140024 kk
     * @implNote list 화면에 노출할 게시물 수 설정값 (application.yml)
     * @since 2024-06-11
     */
    @Value("${kdb.listBrdCnt}")
    private int listBrdCnt;

    /**
     * @author K140024
     * @implNote 모든 페이지네이션 시 노출할 최대 버튼 수 설정값 (application.yml)
     * @since 2024-06-11
     */
    @Value("${kdb.maxPageBtn}")
    private int maxPageBtn;

    /**
     * @author K140024
     * @implNote 게시판 서비스 주입
     * @since 2024-06-11
     */
    private final BoardMainService boardService;

    /**
     * @author K140024
     * @implNote 코드 상세 서비스 주입
     * @since 2024-06-11
     */
    private final CodeHtmlDetailService codeDetailService;

    /**
     * @author K140024
     * @implNote DTM 서비스 주입
     * @since 2024-06-11
     */
    private final DtmService dtmService;


    /**
     * @author K140024
     * @implNote 공통코드 서비스 주입
     * @since 2024-06-11
     */
    private final CommonCodeService commonCodeService;

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
            @PageableDefault(page = 1) final Pageable pageable,
            final Model model) {

        final String VIEW = "/dtm/dtmApplPopup";

        // 날짜 형식이 ISO-8601 형식인 경우
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        // DtmHisDTO 객체를 생성하여 필요한 로직에 활용 가능
        DtmHisDTO dto = new DtmHisDTO();
        dto.setDtmKindCd(dtmKindCd);
        dto.setDtmReasonCd(dtmReasonCd);

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
        final LocalDate today = LocalDate.now();
        final DayOfWeek dayOfWeek = today.getDayOfWeek();
        String formattedDate = today + "(" + dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.KOREAN) + ")";
        model.addAttribute("nowDate", formattedDate);

        return VIEW; // View 이름 반환
    }

}
