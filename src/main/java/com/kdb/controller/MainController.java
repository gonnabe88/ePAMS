package com.kdb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MainController<S extends Session> {
	
	
    @GetMapping(value = {"/test"})
	public String test() {
    	return "test";
    } 

    @GetMapping("/index")
	public String indexMain() {
    	return "index";
    }      

    @GetMapping("/admin")
    public String admin(){
        return "adminPage";
    }

    @GetMapping("/manager")
    public String manager(){
        return "managerPage";
    }

    @GetMapping("/forbidden")
    public String forbiden(){
        return "forbidden";
    }
}