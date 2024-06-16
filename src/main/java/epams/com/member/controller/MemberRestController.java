package epams.com.member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import epams.com.login.service.MFALoginService;
import epams.com.member.dto.MemberDTO;
import epams.com.member.entity.MemberEntity;
import epams.com.member.entity.SearchMemberEntity;
import epams.com.member.service.MemberDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class MemberRestController {
	
	private final MFALoginService restapiservice;
	private final MemberDetailsService memberservice;

    
    @GetMapping("/search")
    public List<SearchMemberEntity> search(Model model, @RequestParam("text") String text) throws Exception{
    	List<SearchMemberEntity> memberList = memberservice.findBySearchValue(text);
		return memberList;
    }
    
}
