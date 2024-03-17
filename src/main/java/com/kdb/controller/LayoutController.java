package com.kdb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LayoutController {

    @GetMapping("/layout")
	public String layout() {
    	return "/common/layout";
    } 
    
    @GetMapping("/head")
	public String head() {
    	return "/common/head";
    } 

    @GetMapping("/header")
	public String header() {
    	return "/common/header";
    }   
    @GetMapping("/body")
	public String body() {
    	return "/common/body";
    }   

}
