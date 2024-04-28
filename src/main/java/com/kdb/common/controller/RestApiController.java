package com.kdb.common.controller;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kdb.common.dto.MemberDTO;
import com.kdb.common.entity.SearchMemberEntity;
import com.kdb.common.service.MemberDetailsService;
import com.kdb.common.service.RestApiService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class RestApiController {
	
	private final RestApiService restapiservice;
	private final MemberDetailsService memberservice;
	
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
}
