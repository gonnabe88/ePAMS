package epams.domain.com.index;

import epams.domain.com.admin.service.HtmlLangDetailService;
import epams.domain.com.board.dto.BoardDTO;
import epams.domain.com.board.service.BoardMainService;
import epams.domain.com.holiday.HolidayDTO;
import epams.domain.com.holiday.HolidayService;
import epams.domain.com.index.dto.BannerDTO;
import epams.domain.com.index.dto.QuickApplDTO;
import epams.domain.com.login.util.webauthn.service.RegistrationService;
import epams.domain.com.login.util.webauthn.authenticator.Authenticator;
import epams.domain.com.login.util.webauthn.user.AppUser;
import epams.domain.com.member.dto.IamUserDTO;
import epams.domain.com.member.service.MemberService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

/**
 * @author K140024
 * @implNote 메인화면(index) Controller
 * @since 2024-06-20
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/index")
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
    private final HtmlLangDetailService langDetailService;

    /**
     * @author K140024
     * @implNote 휴일 서비스 주입
     * @since 2024-10-13
     */
    private final HolidayService holidayService;

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
     * @implNote 메인 화면을 반환하는 메서드
     * @since 2024-06-11
     */
    @GetMapping({"", "/"})
    public String indexMain(@PageableDefault(page = 1) final Pageable pageable, final Model model) {

        final String INDEXMAIN = "common/index";

        // 현재 로그인한 사용자 정보
        final Authentication auth = authentication();

        try {
            // 간편인증 등록 여부 확인
            final AppUser existingUser = service.getUserRepo().findByUsername(auth.getName());
            final Authenticator existingAuthUser = service.getAuthRepository().findByUser(existingUser);

            // 간편인증 사용 여부를 모델에 추가
            if (existingAuthUser == null) {
                model.addAttribute("simpleauth", false);
                log.info("Not simple auth user");
            } else {
                model.addAttribute("simpleauth", true);
                log.info("simple auth user");
            }

            // 사용자 정보를 모델에 추가
            model.addAttribute("username", auth.getName());
        } catch (CustomGeneralRuntimeException e) {
            // 런타임 예외 처리
            e.printStackTrace();
        } catch (Exception e) {
            // 기타 예외 처리
            e.printStackTrace();
        }

        try {
            // 언어목록
            final Map<String, String> langList = langDetailService.getCodeHtmlDetail(INDEXMAIN);
            model.addAttribute("langList", langList);
        } catch (CustomGeneralRuntimeException e) {
            // 런타임 예외 처리
            e.printStackTrace();
        } catch (Exception e) {
            // 기타 예외 처리
            e.printStackTrace();
        }

        try {
            // 공지사항 출력
            final int currentPage = pageable.getPageNumber(); // 현재 페이지 (1-based index)
            final Page<BoardDTO> boardList = boardService.paging(PageRequest.of(currentPage, indexBrdCnt), "10"); // 가장 최근 게시물의 indexBrdCnt 수만큼 가져옴
            model.addAttribute("boardList", boardList);
        } catch (CustomGeneralRuntimeException e) {
            // 런타임 예외 처리
            e.printStackTrace();
        } catch (Exception e) {
            // 기타 예외 처리
            e.printStackTrace();
        }

        try {
            // 빠른근태신청 날짜 세팅 (오늘)
            final LocalDate today = LocalDate.now();
            final DayOfWeek dayOfWeek = today.getDayOfWeek();
            final String nowDateStr = today + "(" + dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.KOREAN) + ")";
            String holidayYn = "N";
            //휴일정보 받아오기
            List<String> holidays=holidayService.findholiYmd();
            //오늘
            HolidayDTO todayDTO=new HolidayDTO(today,null, null, null, null);
            System.err.println("날짜 : "+todayDTO.getHoliYmd());
            //오늘 휴일이면 비활성화
            if(holidays.contains(todayDTO)){
                holidayYn = "Y";
            }
            model.addAttribute("nowDateStr", nowDateStr);

            // 빠른근태신청 날짜 세팅 (내일)
            final LocalDate tomorrow = LocalDate.now().plusDays(1);
            final DayOfWeek dayOfWeek2 = tomorrow.getDayOfWeek();
            final String tomorrowDateStr = tomorrow + "(" + dayOfWeek2.getDisplayName(TextStyle.NARROW, Locale.KOREAN) + ")";
            String holidayYn2 = "N";
            //내일
            HolidayDTO tomorrowDTO=new HolidayDTO(tomorrow,null, null, null, null);
            //내일 휴일이면 비활성화
            if(holidays.contains(tomorrowDTO)){
                holidayYn2 = "Y";
            }
            model.addAttribute("tomorrowDateStr", tomorrowDateStr);

            // 빠른 근태 신청 리스트
            List<QuickApplDTO> dtmApplList = List.of(
                new QuickApplDTO("오늘", nowDateStr, nowDateStr, today, today,"1A", "1A1", "연차휴가", "연차휴가 1일", holidayYn),
                new QuickApplDTO("오늘", nowDateStr, nowDateStr, today, today, "1A", "1A5", "연차휴가", "연차휴가 오전 반차", holidayYn),
                new QuickApplDTO("오늘", nowDateStr, nowDateStr, today, today, "1A", "1AG", "연차휴가", "연차휴가 오전 반반차", holidayYn),
                new QuickApplDTO("내일", tomorrowDateStr, tomorrowDateStr, tomorrow, tomorrow, "1A", "1A1", "연차휴가", "연차휴가 1일", holidayYn2),
                new QuickApplDTO("내일", tomorrowDateStr, tomorrowDateStr, tomorrow, tomorrow, "1A", "1A5", "연차휴가", "연차휴가 오전 반차", holidayYn2),
                new QuickApplDTO("내일", tomorrowDateStr, tomorrowDateStr, tomorrow, tomorrow, "1A", "1AG", "연차휴가", "연차휴가 오전 반반차", holidayYn2)
            );
            model.addAttribute("list", dtmApplList);
            log.info("dtmApplList : {}", dtmApplList);
        } catch (CustomGeneralRuntimeException e) {
            // 런타임 예외 처리
            e.printStackTrace();
        } catch (Exception e) {
            // 기타 예외 처리
            e.printStackTrace();
        }

        try {
            // 배너 리스트
            List<BannerDTO> bannerList = new ArrayList<>();
            bannerList.add(new BannerDTO("", "", "", ""));
            bannerList.add(new BannerDTO("직원조회", "언제 어디서든", "간편하게 검색하세요", "<a href=\"#\" id=\"scrollToSearchDiv2\"><span class=\"badge text-bg-primary\">바로가기</span></a>"));
            model.addAttribute("bannerList", bannerList);
        } catch (CustomGeneralRuntimeException e) {
            // 런타임 예외 처리
            e.printStackTrace();
        } catch (Exception e) {
            // 기타 예외 처리
            e.printStackTrace();
        }

        return INDEXMAIN;
    }

    /**
     * @author K140024
     * @implNote 검색 기능을 수행하는 메서드
     * @since 2024-06-11
     */
    @PostMapping("/searchMember")
    public String search(final Model model, final @RequestParam("text") String text) {

        // 메인화면 직원조회 출력
        final List<IamUserDTO> memberList = memberservice.findBySearchValue(text);
        model.addAttribute("memberList", memberList);

        return "common/indexMemberList";
    }
}
