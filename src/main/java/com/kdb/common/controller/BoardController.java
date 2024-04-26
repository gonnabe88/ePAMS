package com.kdb.common.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;

import com.kdb.common.dto.BoardDTO;
import com.kdb.common.dto.CommentDTO;
import com.kdb.common.service.BoardService;
import com.kdb.common.service.CommentService;
import com.kdb.example.board.BoardFileDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author K140024
 * @since 2024-04-26
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    /**
    *
    *
    */
    private final BoardService boardService;
    
    /**
    *
    *
    */
    private final CommentService commentService; 
    
	private Authentication authentication() {
	    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || authentication instanceof AnonymousAuthenticationToken) return null;
	    return authentication;
	}

	
    @GetMapping("/{boardid}/download/{fileid}")
    public ResponseEntity<Resource> download(@PathVariable("boardid") Long boardid, @PathVariable("fileid") Long fileid) {
        
    	log.warn("download id : " + fileid);
    	
    	//파일정보 가져오기
    	BoardFileDTO boardfile = boardService.findOneFile(fileid);
    	UrlResource resource;
        try{
            resource = new UrlResource("file:C:/epams/"+ boardfile.getStoredFileName());
        }catch (MalformedURLException e){
            log.error("the given File path is not valid");
            e.getStackTrace();
            throw new RuntimeException("the given URL path is not valid");
        }
        //Header
        String originalFileName = boardfile.getOriginalFileName();
        String encodedOriginalFileName = UriUtils.encode(originalFileName, StandardCharsets.UTF_8);

        String contentDisposition = "attachment; filename=\"" + encodedOriginalFileName + "\"";

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }
	
    @GetMapping("/")
    public String list(Model model) {
        // DB에서 전체 게시글 데이터를 가져와서 list.html에 보여준다.
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "/common/list";
    }
    
  /*  @GetMapping("/list")
    public String findAll(Model model) {
        // DB에서 전체 게시글 데이터를 가져와서 list.html에 보여준다.
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "list";
    }*/
    
    
    @GetMapping("/editor")
    public String editor() {    	
        return "/common/editor";
    }

    @GetMapping("/save")
    public String saveForm() {
        return "/common/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO, Model model) throws IOException {
    	log.warn(boardDTO.toString());        
        boardDTO.setBoardWriter(authentication().getName());
        boardService.save(boardDTO);
        //return "redirect:/board/list"; 
        
        return "redirect:/board/list";

    }
    
    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model) throws IOException {
    	log.warn(boardDTO.toString());
    	boardDTO.setBoardWriter(authentication().getName());
        BoardDTO board = boardService.update(boardDTO);
        model.addAttribute("board", board);
        return "/common/detail";
//        return "redirect:/board/" + boardDTO.getId();
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model,
                           @PageableDefault(page=1) Pageable pageable) {
        /*
            해당 게시글의 조회수를 하나 올리고
            게시글 데이터를 가져와서 detail.html에 출력
         */
    	System.out.println("ID");
    	System.out.println("ID :"+id+"model :"+model);
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        
        /* 댓글 목록 가져오기 */
        List<CommentDTO> commentDTOList = commentService.findAll(id);
        model.addAttribute("commentList", commentDTOList);
        model.addAttribute("board", boardDTO);
        model.addAttribute("page", pageable.getPageNumber());
        
        //첨부파일 가져오기
        if (boardDTO.getFileAttached() == 1) {
            List<BoardFileDTO> boardFileDTOList = boardService.findFile(id);
            model.addAttribute("boardFileList", boardFileDTOList);
        }
        
        return "/common/detail";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable("id") Long id, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        
        //첨부파일 가져오기
        if (boardDTO.getFileAttached() == 1) {
            List<BoardFileDTO> boardFileDTOList = boardService.findFile(id);
            model.addAttribute("boardFileList", boardFileDTOList);
        }
        
        return "/common/update";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        boardService.delete(id);
        return "redirect:/board/list";
    }

    
    // /board/paging?page=1
    @GetMapping("/list")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {
//        pageable.getPageNumber();
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
        return "/common/list";
    }
}