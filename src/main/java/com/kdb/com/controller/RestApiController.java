package com.kdb.com.controller;

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

import com.kdb.com.dto.MemberDTO;
import com.kdb.com.entity.LogLoginEntity;
import com.kdb.com.entity.MemberEntity;
import com.kdb.com.entity.SearchMemberEntity;
import com.kdb.com.service.LogService;
import com.kdb.com.service.MemberDetailsService;
import com.kdb.com.service.RestApiService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class RestApiController {
	
	private final RestApiService restapiservice;
	private final MemberDetailsService memberservice;
	private final LogService logservice;
	
    @GetMapping("/login")
    public ResponseEntity<List<LogLoginEntity>> searchLogLoginLAll(Model model) throws Exception{
    	List<LogLoginEntity> logLoginList = logservice.searchLogLoginAll();
    	Map<String, Object> data = new HashMap<>();
    	data.put("data", logLoginList);
    	// 마지막 페이지 및 데이터 설정
        Map<String, Object> response = new HashMap<>();
        //response.put("last_page", 10);
        response.put("data", logLoginList);
        
        return ResponseEntity.ok(logLoginList);
    }
	
    @PostMapping("/mfa")
    public Map<String, String> mfa(@ModelAttribute MemberDTO memberDTO, @CookieValue(value="UUIDChk", required=false) String UUID) throws Exception{
    	log.warn("rest login");
    	memberDTO.setUUID(UUID);
		return restapiservice.requestMFA(memberDTO);
    }
    
    @GetMapping("/search")
    public List<SearchMemberEntity> search(Model model, @RequestParam("text") String text) throws Exception{
    	List<SearchMemberEntity> memberList = memberservice.findBySearchValue(text);
		return memberList;
    }
    
    @GetMapping("/member")
    public ResponseEntity<List<MemberEntity>> searchAll(Model model) throws Exception{
    	List<MemberEntity> memberList = memberservice.findAll();
    	Map<String, Object> data = new HashMap<>();
    	data.put("data", memberList);
    	
    	// 마지막 페이지 및 데이터 설정
        Map<String, Object> response = new HashMap<>();
        //response.put("last_page", 10);
        response.put("data", memberList);
        
        return ResponseEntity.ok(memberList);
    }
    
    @PostMapping("/member/save")
    public ResponseEntity<Map<String, String>> saveMembers(@RequestBody Map<String, List<MemberEntity>> payload) {
    	
        List<MemberEntity> added = payload.get("added");
        List<MemberEntity> changed = payload.get("changed");
        List<MemberEntity> deleted = payload.get("deleted");

        log.warn("added : "+ added.toString());
        log.warn("changed : "+ changed.toString());
        log.warn("deleted : "+ deleted.toString());
        
        memberservice.saveMembers(added, changed, deleted);

        return ResponseEntity.ok(Map.of("message", "Data saved successfully!"));
    }
    
}
