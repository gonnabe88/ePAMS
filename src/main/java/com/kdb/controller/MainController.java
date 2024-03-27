package com.kdb.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kdb.Board.BoardDTO;
import com.kdb.Board.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController<S extends Session> {
	
	private final BoardService boardService;
	
	
    @GetMapping(value = {"/test"})
	public String test() {
    	return "test";
    } 

    @GetMapping("/index")
	public String indexMain(@PageableDefault(page = 1) Pageable pageable, Model model) {
//      pageable.getPageNumber();
	      Page<BoardDTO> boardList = boardService.paging(pageable);
	      int blockLimit = 3;
	      int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
	      int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();
	
	      // page 갯수 20개
	      // 현재 사용자가 3페이지
	      // 1 2 3
	      // 현재 사용자가 7페이지
	      // 7 8 9
	      // 보여지는 페이지 갯수 3개
	      // 총 페이지 갯수 8개
	
	      model.addAttribute("boardList", boardList);
	      model.addAttribute("startPage", startPage);
	      model.addAttribute("endPage", endPage);
	      
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