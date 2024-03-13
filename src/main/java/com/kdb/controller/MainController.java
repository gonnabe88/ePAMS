package com.kdb.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class MainController {

    @GetMapping({"/", "/login"})
    public String login(){
        return "/login";
    }

    @GetMapping("/main")
    public String main(){
        return "main";
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