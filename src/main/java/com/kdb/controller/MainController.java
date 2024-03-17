package com.kdb.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class MainController {
    @GetMapping(value = {"/test"})
	public String test() {
    	return "test";
    } 
    @GetMapping({"/", "/login"})
    public String login(){
        return "/login";
    }

    @GetMapping(value = {"/index"})
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