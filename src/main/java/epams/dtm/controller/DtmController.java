package epams.dtm.controller;

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
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import epams.com.admin.service.CodeHtmlDetailService;
import epams.com.board.dto.BoardDTO;
import epams.com.board.service.BoardService;
import epams.com.member.entity.MemberEntity;
import epams.com.member.service.MemberDetailsService;
import epams.dtm.dto.BasePeriodDTO;
import epams.dtm.dto.DtmApplDTO;
import epams.dtm.service.DtmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/dtm")
public class DtmController<S extends Session> {

	/**
	 * @author K140024
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

	private final BoardService boardService;
	private final MemberDetailsService memberservice;
	private final CodeHtmlDetailService codeHtmlDetailService;
	private final DtmService dtmService;

	@GetMapping("/main")
	public String dtmMain(@PageableDefault(page = 1) Pageable pageable, Model model) {
		final String VIEW = "/dtm/main";
		// 코드
		Map<String, String> codeList = codeHtmlDetailService.getCodeHtmlDetail(VIEW);
		// 리스트의 내용을 출력
		System.out.println(codeList.toString());

		// System.out.println(codeList.get(0).getCD_NM());
		model.addAttribute("codeList", codeList);

		// 메인화면 공지사항 출력
		Page<BoardDTO> boardList = boardService.paging(pageable);
		int blockLimit = 3;
		int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1
																													// 4
																													// 7
																													// 10
																													// ~~
		int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1
				: boardList.getTotalPages();
		model.addAttribute("boardList", boardList);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		// 메인화면 직원조회 출력
		List<MemberEntity> memberList = memberservice.findAll();
		model.addAttribute("memberList", memberList);

		// 메인화면 빠른근태신청 출력
		LocalDate today = LocalDate.now();
		DayOfWeek dayOfWeek = today.getDayOfWeek();
		model.addAttribute("nowDate", today + "(" + dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.KOREAN) + ")");

		LocalDate tomorrow = LocalDate.now().plusDays(1);
		DayOfWeek dayOfWeek2 = tomorrow.getDayOfWeek();
		model.addAttribute("tomorrowDate",
				tomorrow + "(" + dayOfWeek2.getDisplayName(TextStyle.NARROW, Locale.KOREAN) + ")");

		return VIEW;
	}

	@GetMapping("/list")
	public String dtmList(@PageableDefault(page = 1) Pageable pageable, @ModelAttribute final BasePeriodDTO period, Model model) {
		final String DTMMAIN = "/dtm/list";
		// 코드
		Map<String, String> codeList = codeHtmlDetailService.getCodeHtmlDetail(DTMMAIN);
		// 리스트의 내용을 출력
		System.out.println(codeList.toString());

		// System.out.println(codeList.get(0).getCD_NM());
		model.addAttribute("codeList", codeList);

		// 메인화면 근태내용 출력
		final int currentPage = pageable.getPageNumber(); // 현재 페이지 (1-based index)
		final Pageable updatedPageable = PageRequest.of(currentPage, listBrdCnt);
		final Page<DtmApplDTO> dtmApplList = dtmService.findAllByPeriod(updatedPageable, period);
		final int totalPages = dtmApplList.getTotalPages();
		int startPage = Math.max(1, currentPage - (maxPageBtn / 2));
		final int endPage = Math.min(totalPages, startPage + maxPageBtn - 1);

		if (endPage - startPage < maxPageBtn - 1) {
			startPage = Math.max(1, endPage - maxPageBtn + 1);
		}

		model.addAttribute("dtmApplList", dtmApplList);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		return DTMMAIN;
	}

}