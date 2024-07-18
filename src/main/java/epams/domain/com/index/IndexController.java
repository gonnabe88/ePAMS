package epams.domain.com.index;

import epams.domain.com.admin.service.CodeHtmlDetailService;
import epams.domain.com.board.dto.BoardDTO;
import epams.domain.com.board.service.BoardMainService;
import epams.domain.com.index.dto.BannerDTO;
import epams.domain.com.index.dto.QuickApplDTO;
import epams.domain.com.login.util.webauthn.RegistrationService;
import epams.domain.com.login.util.webauthn.authenticator.Authenticator;
import epams.domain.com.login.util.webauthn.user.AppUser;
import epams.domain.com.member.dto.IamUserDTO;
import epams.domain.com.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author K140024
 * @implNote 메인화면(index) Controller
 * @since 2024-06-20
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController<S extends Session> {

    /**
     * @author K140024
     * @implNote 스트링 상수 정의 (applcation.yml)
     * @since 2024-06-20
     */
    @Value("${kdb.indexBrdCnt}")
    private int indexBrdCnt;

    /**
     * @author K140024
     * @implNote 게시판 서비스 주입
     * @since 2024-06-11
     */
    private final BoardMainService boardService;
    /**
     * @author K140024
     * @implNote 회원 서비스 주입
     * @since 2024-06-11
     */
    private final MemberService memberservice;

    /**
     * @author K140024
     * @implNote 등록 서비스 주입
     * @since 2024-06-11
     */
    private final RegistrationService service;

    /**
     * @author K140024
     * @implNote 코드 상세 서비스 주입
     * @since 2024-06-11
     */
    private final CodeHtmlDetailService codeDetailService;

    /**
     * @author K140024
     * @implNote 현재 인증된 사용자 정보를 반환하는 메서드
     * @since 2024-06-11
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
     * @implNote 팝업 화면을 반환하는 메서드
     * @since 2024-06-11
     */
    @GetMapping("/popup")
    public String popup() {
        return "/common/popup";
    }

    /**
     * @author K140024
     * @implNote 메인 화면을 반환하는 메서드
     * @since 2024-06-11
     */
    @GetMapping("/index")
    public String indexMain(@PageableDefault(page = 1) final Pageable pageable, final Model model) {

        final String INDEXMAIN = "/common/index";

        // 현재 로그인한 사용자 정보
        final Authentication auth = authentication();

        // 간편인증 등록 여부 확인
        final AppUser existingUser = service.getUserRepo().findByUsername(auth.getName());
        final Authenticator existingAuthUser = service.getAuthRepository().findByUser(existingUser);
        model.addAttribute("username", auth.getName());
        if (existingAuthUser == null) {
            model.addAttribute("simpleauth", false);
            log.info("Not simple auth user");
        } else {
            model.addAttribute("simpleauth", true);
            log.info("simple auth user");
        }

        // 코드
        final Map<String, String> codeList = codeDetailService.getCodeHtmlDetail(INDEXMAIN);
        model.addAttribute("codeList", codeList);

        // 메인화면 공지사항 출력
        final int currentPage = pageable.getPageNumber(); // 현재 페이지 (1-based index)
        final Page<BoardDTO> boardList = boardService.paging(PageRequest.of(currentPage, indexBrdCnt)); // 가장 최근 게시물의 indexBrdCnt 수만큼 가져옴
        model.addAttribute("boardList", boardList);

        // 메인화면 빠른근태신청 출력
        final LocalDate today = LocalDate.now();
        final DayOfWeek dayOfWeek = today.getDayOfWeek();
        final String nowDate = today + "(" + dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.KOREAN) + ")";
        model.addAttribute("nowDate", nowDate);

        final LocalDate tomorrow = LocalDate.now().plusDays(1);
        final DayOfWeek dayOfWeek2 = tomorrow.getDayOfWeek();
        final String tomorrowDate = tomorrow + "(" + dayOfWeek2.getDisplayName(TextStyle.NARROW, Locale.KOREAN) + ")";
        model.addAttribute("tomorrowDate", tomorrowDate);

        // 빠른 근태 신청 리스트
        List<QuickApplDTO> dtmApplList = new ArrayList<>();
        dtmApplList.add(new QuickApplDTO("오늘", nowDate, "DTM01", "연차휴가 1일"));
        dtmApplList.add(new QuickApplDTO("오늘", nowDate, "DTM02", "연차휴가 오전 반차"));
        dtmApplList.add(new QuickApplDTO("오늘", nowDate, "DTM03", "연차휴가 오전 반반차"));
        dtmApplList.add(new QuickApplDTO("내일", tomorrowDate, "DTM01", "연차휴가 1일"));
        dtmApplList.add(new QuickApplDTO("내일", tomorrowDate, "DTM02", "연차휴가 오전 반차"));
        dtmApplList.add(new QuickApplDTO("내일", tomorrowDate, "DTM03", "연차휴가 오전 반반차"));
        model.addAttribute("list", dtmApplList);

        // 배너 리스트
        List<BannerDTO> bannerList = new ArrayList<>();
        bannerList.add(new BannerDTO("최근 공지사항", "시스템 점검 안내", "'24. 7. 6(일) 18:00-19:00", "<a href=\"/dtm/main\"><span class=\"badge text-bg-primary\">바로가기</span></a>"));
        bannerList.add(new BannerDTO("연차 및 저축휴가", "언제 어디서든", "편하게 신청하세요", "<a href=\"/dtm/main\"><span class=\"badge text-bg-primary\">바로가기</span></a>"));
        bannerList.add(new BannerDTO("직원조회", "언제 어디서든", "간편하게 검색하세요", "<a href=\"#\" onclick=\"scrollToDiv()\"><span class=\"badge text-bg-primary\">바로가기</span></a>"));
        model.addAttribute("bannerList", bannerList);

        return INDEXMAIN;
    }

    /**
     * @author K140024
     * @implNote 검색 기능을 수행하는 메서드
     * @since 2024-06-11
     */
    @PostMapping("/search")
    public String search(final Model model, final @RequestParam("text") String text) {

        // 메인화면 직원조회 출력
        final List<IamUserDTO> memberList = memberservice.findBySearchValue(text);
        model.addAttribute("memberList", memberList);

        return "/common/memberlist";
    }
}
