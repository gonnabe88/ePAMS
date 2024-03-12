package com.kdb.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kdb.config.CustomPasswordEncoder;
import com.kdb.dto.MemberDTO;
import com.kdb.member.Member;
import com.kdb.member.MemberRepository;
import com.kdb.member.MemberRole;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller

public class MemberController {

    private final MemberRepository memberRepository;
    //private final BCryptPasswordEncoder passwordEncoder;
    private CustomPasswordEncoder passwordEncoder; // KDB에서 사용하는 암호화 방식으로 커스텀

    //생성자 삭제 시 아래의 NullPointerException 발생
    //java.lang.NullPointerException: Cannot invoke "com.kdb.config.CustomPasswordEncoder.encode(java.lang.CharSequence)" because "this.passwordEncoder" is null
    @Autowired
    public MemberController(MemberRepository memberRepository,
                       @Lazy CustomPasswordEncoder passwordEncoder){
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = {"/index"})
	public String indexMain() {
    	return "index";
    }    	

    @GetMapping("/registry")
    public String registryForm(Model model) {
        model.addAttribute("member", new MemberDTO());
        return "registration";
    }

    @PostMapping("/registry")
    public String registry(@ModelAttribute MemberDTO registryRequest) {
        Member member = Member.builder()
                .username(registryRequest.getUsername())
                .password(passwordEncoder.encode(registryRequest.getPassword()))
                .role(registryRequest.getRole())
                .build();
        memberRepository.save(member);

        return "redirect:/login";
    }

    @ModelAttribute("roles")
    public Map<String, MemberRole> roles() {
        Map<String, MemberRole> map = new LinkedHashMap<>();
        map.put("관리자", MemberRole.ROLE_ADMIN);
        map.put("매니저", MemberRole.ROLE_MANAGER);
        map.put("일반 사용자", MemberRole.ROLE_MEMBER);
        return map;
    }
}