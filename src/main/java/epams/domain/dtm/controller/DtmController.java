package epams.domain.dtm.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Map;

import epams.domain.com.commonCode.CommonCodeService;
import epams.domain.dtm.dto.DtmHisDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.format.annotation.DateTimeFormat;

import epams.domain.com.admin.service.CodeHtmlDetailService;
import epams.domain.com.board.dto.BoardDTO;
import epams.domain.com.board.service.BoardMainService;
import epams.domain.dtm.service.DtmService;
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
public class DtmController<S extends Session> {

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
     * @implNote DTM 메인 화면을 반환하는 메서드
     * @since 2024-06-11
     */
    @GetMapping("/main")
    public String dtmMain(@PageableDefault(page = 1) final Pageable pageable, final Model model) {
        final String VIEW = "/dtm/main";

        // 코드
        final Map<String, String> codeList = codeDetailService.getCodeHtmlDetail(VIEW);
        model.addAttribute("codeList", codeList);

        // 메인화면 공지사항 출력
        final Page<BoardDTO> boardList = boardService.paging(pageable);
        final int blockLimit = 3;
        final int startPage = ((int) Math.ceil((double) pageable.getPageNumber() / blockLimit) - 1) * blockLimit + 1;
        final int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();
        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        // 메인화면 직원조회 출력
        //final List<TempUserEntity> memberList = memberservice.findAll();
        //model.addAttribute("memberList", memberList);

        // 메인화면 빠른근태신청 출력
        final LocalDate today = LocalDate.now();
        final DayOfWeek dayOfWeek = today.getDayOfWeek();
        model.addAttribute("nowDate", today + "(" + dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.KOREAN) + ")");

        final LocalDate tomorrow = LocalDate.now().plusDays(1);
        final DayOfWeek dayOfWeek2 = tomorrow.getDayOfWeek();
        model.addAttribute("tomorrowDate", tomorrow + "(" + dayOfWeek2.getDisplayName(TextStyle.NARROW, Locale.KOREAN) + ")");

        return VIEW;
    }

    @GetMapping("/dtmApplPopup")
    public String dtmApplPopup(
            @RequestParam String dtmKindCd,
            @RequestParam String dtmReasonCd,
            @RequestParam String staYmd,
            @RequestParam String endYmd,
            @PageableDefault(page = 1) final Pageable pageable,
            final Model model) {

        final String VIEW = "/dtm/dtmApplPopup";

        // DtmHisDTO 객체를 생성하여 필요한 로직에 활용 가능
        DtmHisDTO dto = new DtmHisDTO();
        dto.setDtmKindCd(dtmKindCd);
        dto.setDtmReasonCd(dtmReasonCd);
        dto.setStaYmd(LocalDateTime.parse(staYmd));
        dto.setEndYmd(LocalDateTime.parse(endYmd));

        // 필요한 로직 처리 후 모델에 추가
        model.addAttribute("dtmHisDTO", dto);

        // 기타 필요한 모델 속성 설정
        final LocalDate today = LocalDate.now();
        final DayOfWeek dayOfWeek = today.getDayOfWeek();
        String formattedDate = today + "(" + dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.KOREAN) + ")";
        model.addAttribute("nowDate", formattedDate);

        return VIEW; // View 이름 반환
    }

    @GetMapping("/main2")
    public String dtmMain2(@PageableDefault(page = 1) final Pageable pageable, final Model model) {
        final String VIEW = "/dtm/main2";

        return VIEW;
    }

    @GetMapping("/DtmRegister")
    public String dtmRegister(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Model model
    ) {
        final String VIEW = "/dtm/DtmRegister";

        if (date != null) {
            model.addAttribute("selectedDate", date);
        }

        return VIEW;
    }

}
