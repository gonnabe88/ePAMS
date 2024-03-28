package com.kdb.common.controller;

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

import com.kdb.common.config.CustomPasswordEncoder;
import com.kdb.common.entity.MemberEntity;
import com.kdb.example.member.MemberDTO;
import com.kdb.example.member.MemberRepository;
import com.kdb.example.member.MemberRole;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberRepository memberRepository;
    //private final BCryptPasswordEncoder passwordEncoder;
    private CustomPasswordEncoder passwordEncoder; // KDB�뿉�꽌 �궗�슜�븯�뒗 �븫�샇�솕 諛⑹떇�쑝濡� 而ㅼ뒪��

    //�깮�꽦�옄 �궘�젣 �떆 �븘�옒�쓽 NullPointerException 諛쒖깮
    //java.lang.NullPointerException: Cannot invoke "com.kdb.config.CustomPasswordEncoder.encode(java.lang.CharSequence)" because "this.passwordEncoder" is null
    @Autowired
    public MemberController(MemberRepository memberRepository,
                       @Lazy CustomPasswordEncoder passwordEncoder){
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/registry")
    public String registryForm(Model model) {
        model.addAttribute("member", new MemberDTO());
        return "/common/register";
    }

    @PostMapping("/registry")
    public String registry(@ModelAttribute MemberDTO registryRequest) {
        MemberEntity member = MemberEntity.builder()
                .username(registryRequest.getUsername())
                .password(passwordEncoder.encode(registryRequest.getPassword()))
                .role(registryRequest.getRole())
                .build();
        memberRepository.save(member);

        return "redirect:/common/login";
    }

    @ModelAttribute("roles")
    public Map<String, MemberRole> roles() {
        Map<String, MemberRole> map = new LinkedHashMap<>();
        map.put("ADMIN", MemberRole.ROLE_ADMIN);
        map.put("KDB", MemberRole.ROLE_KDB);
        map.put("ITO", MemberRole.ROLE_ITO);
        return map;
    }
}