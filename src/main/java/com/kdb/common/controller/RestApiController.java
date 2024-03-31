package com.kdb.common.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kdb.common.dto.MemberDTO;
import com.kdb.common.repository.LoginRepository;
import com.kdb.common.service.RestApiService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class RestApiController {
	
	private final LoginRepository loginRepository;
	private final RestApiService restapiservice;
	
	/*
	 * @GetMapping("/updateuuid") public Map<String, String> updateUUID() { return
	 * restapiservice.updateUUID(); }
	 */
	
    @PostMapping("/mfa")
    public Map<String, String> mfa(@ModelAttribute MemberDTO memberDTO) throws Exception{
    	log.info("mfa reqeust from {}, {}, {}, {}", memberDTO.getUsername(), memberDTO.getMFA(), memberDTO.getOTP(), memberDTO.getPassword());
		return restapiservice.requestMFA(memberDTO);
    }
}
