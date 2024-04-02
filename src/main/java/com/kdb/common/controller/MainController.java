package com.kdb.common.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kdb.common.dto.BoardDTO;
import com.kdb.common.service.BoardService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController<S extends Session> {
	
	private final BoardService boardService;
	
	@RequestMapping(value = "/.well-known/acme-challenge/4EjNsQesMlt0nvhuLEe4AsiG01707pslgmYuE809Urg")
    @ResponseBody
    public String applyHttps(HttpServletResponse response) {
	return "4EjNsQesMlt0nvhuLEe4AsiG01707pslgmYuE809Urg.svi_c3vP-7m4wMLvKAh82Wwnhd_C1FTfnaumYutEWwg";
    }

    @GetMapping("/index")
	public String indexMain(@PageableDefault(page = 1) Pageable pageable, Model model) {
//      pageable.getPageNumber();
	      Page<BoardDTO> boardList = boardService.paging(pageable);
	      int blockLimit = 3;
	      int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
	      int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();
	      System.out.println("index");
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
	      
    	return "/common/index";
    }      

}