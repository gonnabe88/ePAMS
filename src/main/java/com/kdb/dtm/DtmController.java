package com.kdb.dtm;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kdb.com.dto.BoardDTO;
import com.kdb.com.dto.HtmlDTO;
import com.kdb.com.entity.MemberEntity;
import com.kdb.com.service.BoardService;
import com.kdb.com.service.CodeHtmlDetailService;
import com.kdb.com.service.CodeService;
import com.kdb.com.service.MemberDetailsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/dtm")
public class DtmController<S extends Session> {
	
	private final CodeService codeService;
	private final BoardService boardService;
	private final MemberDetailsService memberservice;
	private final CodeHtmlDetailService codeHtmlDetailService;

    @GetMapping("/main")
	public String dtmMain(@PageableDefault(page = 1) Pageable pageable, Model model) {
    	  final String DTMMAIN = "/dtm/main";
    	  // 코드
    	  Map<String, String> codeList = codeHtmlDetailService.getCodeHtmlMap(DTMMAIN);
          // 리스트의 내용을 출력
          System.out.println(codeList.toString());
    	  
    	  //System.out.println(codeList.get(0).getCD_NM());
    	  model.addAttribute("codeList", codeList);	    

    	  // 메인화면 공지사항 출력
	      Page<BoardDTO> boardList = boardService.paging(pageable);
	      int blockLimit = 3;
	      int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
	      int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();
	      model.addAttribute("boardList", boardList);
	      model.addAttribute("startPage", startPage);
	      model.addAttribute("endPage", endPage);
	      
	      // 메인화면 직원조회 출력
	      List<MemberEntity> memberList = memberservice.findAll();
	      model.addAttribute("memberList", memberList);	      
	      
	      // 메인화면 빠른근태신청 출력
	      LocalDate today = LocalDate.now();
	      DayOfWeek dayOfWeek = today.getDayOfWeek();
	      model.addAttribute("nowDate", today+"("+dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.KOREAN)+")");
	      
	      LocalDate tomorrow = LocalDate.now().plusDays(1);
	      DayOfWeek dayOfWeek2 = tomorrow.getDayOfWeek();
	      model.addAttribute("tomorrowDate", tomorrow+"("+dayOfWeek2.getDisplayName(TextStyle.NARROW, Locale.KOREAN)+")");
	      
	        
    	return DTMMAIN;
    }      
}