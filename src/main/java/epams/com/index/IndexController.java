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
import epams.com.admin.service.CodeService;
import epams.com.board.dto.BoardDTO;
import epams.com.board.service.BoardService;
import epams.com.login.util.webauthn.RegistrationService;
import epams.com.login.util.webauthn.authenticator.Authenticator;
import epams.com.login.util.webauthn.user.AppUser;
import epams.com.member.entity.MemberEntity;
import epams.com.member.entity.SearchMemberEntity;
import epams.com.member.service.MemberDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController<S extends Session> {
	
	/**
    *
    *
    */
	@Value("${kdb.indexBrdCnt}")
	private int indexBrdCnt;
	
	private final CodeService codeService;
	private final BoardService boardService;
	private final MemberDetailsService memberservice;
	private final RegistrationService service;
	private final CodeHtmlDetailService codeHtmlDetailService;
	
	private Authentication Authentication() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || authentication instanceof AnonymousAuthenticationToken) return null;
	    return authentication;
	}
	@GetMapping("/popup")
	public String popup() {
		return "/common/popup";
	}
	@GetMapping("/index")
	public String indexMain(@PageableDefault(page = 1) Pageable pageable, Model model) {
    	
		final String INDEXMAIN = "/common/index";
		
		// 현재 로그인한 사용자 정보
    	Authentication auth = Authentication();
    	
    	// 간편인증 등록 여부 확인
    	AppUser existingUser = service.getUserRepo().findByUsername(auth.getName());
    	List<Authenticator> existingAuthUser = service.getAuthRepository().findAllByUser(existingUser);
    	model.addAttribute("username", auth.getName());
    	if(existingAuthUser.isEmpty()) {
    		model.addAttribute("simpleauth", false);
    		log.info("Not simple auth user");
    	}
		else {
			model.addAttribute("simpleauth", true);
			log.info("simple auth user");
		}			
    	
    	  // 코드
    	  Map<String, String> codeList = codeHtmlDetailService.getCodeHtmlDetail(INDEXMAIN);
    	  model.addAttribute("codeList", codeList);	    

    	  // 메인화면 공지사항 출력	   
          int currentPage = pageable.getPageNumber(); // 현재 페이지 (1-based index)
	      pageable = PageRequest.of(currentPage, indexBrdCnt); // pageable 객체 설정 
	      Page<BoardDTO> boardList = boardService.paging(pageable); // 가장 최근 게시물의 indexBrdCnt 수만큼 가져옴
	      model.addAttribute("boardList", boardList);
	      
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
	      
	        
    	return INDEXMAIN;
    }      
    
    @PostMapping("/search")
	public String search(Model model, @RequestParam("text") String text) {
    	
    	  // 메인화면 직원조회 출력
		  List<SearchMemberEntity> memberList = memberservice.findBySearchValue(text);
	      model.addAttribute("memberList", memberList);	      
	      
    	return "/common/memberlist";
    }    

    

}