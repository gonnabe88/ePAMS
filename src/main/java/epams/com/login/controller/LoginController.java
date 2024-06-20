package epams.com.login.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import epams.com.login.service.LoginService;
import epams.com.login.service.MFALoginService;
import epams.com.login.util.webauthn.RegistrationService;
import epams.com.member.dto.MemberDTO;
import epams.com.member.entity.MemberEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {
	
	private final LoginService loginService;
	private final RegistrationService service;
	private final MFALoginService restapiservice;

	private Authentication Authentication() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || authentication instanceof AnonymousAuthenticationToken) return null;
	    return authentication;
	}
	
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws Exception{
    	HttpSession session = request.getSession(false);
    	session.invalidate();
    	return "redirect:/login";
    }   
    
    @GetMapping({"/", "/login"})
    public String login(HttpServletResponse response, @CookieValue(value="idChk", required=false) String username, @RequestParam(value = "isChecked", defaultValue = "false") boolean isChecked, Model model)  throws Exception{    	
    	Authentication auth = Authentication();
    	int existingUser = service.getWebauthUserRepository().countByUsername(username);
    	int existingAuthUser = service.getWebauthDetailRepository().countByUser(username);
    	if(existingAuthUser == 0) {
    		model.addAttribute("isChecked", "false");
    		log.info("Not simple auth user");
    	}
		else {
			model.addAttribute("isChecked", "true");
			log.info("simple auth user");
		}			
    	
    	if(auth != null && auth.isAuthenticated()) return "redirect:/index";
    	return "/common/login";	    	
    }
    
    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> pwlogin(HttpServletResponse response, @ModelAttribute MemberDTO memberDTO, Model model)  throws Exception{    	
    	int existingUser = service.getWebauthUserRepository().countByUsername(memberDTO.getUsername());
    	Map<String, Object> res = new HashMap<>();
    	if(loginService.pwLogin(memberDTO)) {
    		// 로그인 성공 시 인증번호 생성
    		
    		restapiservice.requestMFA(memberDTO);
            // 로그인 성공 시 추가 인증 단계로 넘어가기 위해 성공 여부를 반환
    		res.put("result", true);
    		if(existingUser == 0)
    			res.put("simpleauth", false);
    		else
    			res.put("simpleauth", true);
    	}
    	else {
    		// 로그인 실패 시 실패 여부를 반환
    		res.put("result", false);	    	
    	}
        return res;    	
    }
    
}
