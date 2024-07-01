package epams.com.index;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

import epams.com.admin.service.CodeHtmlDetailService;
import epams.com.board.dto.BoardDTO;
import epams.com.board.service.BoardService;
import epams.com.login.util.webauthn.RegistrationService;
import epams.com.login.util.webauthn.authenticator.Authenticator;
import epams.com.login.util.webauthn.user.AppUser;
import epams.com.member.dto.IamUserDTO;
import epams.com.member.entity.TempUserEntity;
import epams.com.member.service.MemberDetailsService;
import epams.com.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
    private final BoardService boardService;

    /**
     * @author K140024
     * @implNote 회원 서비스 주입
     * @since 2024-06-11
     */
    private final MemberDetailsService memberservice;
    private final MemberService memberservice2;

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
    private final CodeHtmlDetailService codeHtmlDetailService;

    /**
     * @author K140024
     * @implNote 현재 인증된 사용자 정보를 반환하는 메서드
     * @since 2024-06-11
     */
    private Authentication Authentication() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) return null;
        return authentication;
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
    public String indexMain(@PageableDefault(page = 1) Pageable pageable, Model model) {

        final String INDEXMAIN = "/common/index";

        // 현재 로그인한 사용자 정보
        final Authentication auth = Authentication();

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
        final Map<String, String> codeList = codeHtmlDetailService.getCodeHtmlDetail(INDEXMAIN);
        model.addAttribute("codeList", codeList);

        // 메인화면 공지사항 출력
        final int currentPage = pageable.getPageNumber(); // 현재 페이지 (1-based index)
        pageable = PageRequest.of(currentPage, indexBrdCnt); // pageable 객체 설정
        final Page<BoardDTO> boardList = boardService.paging(pageable); // 가장 최근 게시물의 indexBrdCnt 수만큼 가져옴
        model.addAttribute("boardList", boardList);

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

        return INDEXMAIN;
    }

    /**
     * @author K140024
     * @implNote 검색 기능을 수행하는 메서드
     * @since 2024-06-11
     */
    @PostMapping("/search")
    public String search(Model model, @RequestParam("text") String text) {

        // 메인화면 직원조회 출력
        //final List<SearchMemberEntity> memberList = memberservice.findBySearchValue(text);
        final List<IamUserDTO> memberList = memberservice2.findBySearchValue(text);
        model.addAttribute("memberList", memberList);

        return "/common/memberlist";
    }
}
