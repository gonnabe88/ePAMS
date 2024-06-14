package com.kdb.com.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import com.kdb.com.dto.BoardDTO;
import com.kdb.com.dto.BoardFileDTO;
import com.kdb.com.dto.BoardImageDTO;
import com.kdb.com.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author K140024
 * @implNote 게시판 controller
 * @since 2024-04-26
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    /**
     * @author K140024
     * @implNote file 시스템에 저장할 실제 경로 설정값 (application.yml)
     * @since 2024-06-11
     */
    @Value("${kdb.filepath}")
    private String filepath;
    
    /**
     * @author K140024
     * @implNote list 화면에 노출할 게시물 수 설정값 (application.yml)
     * @since 2024-06-11
     */
    @Value("${kdb.listBrdCnt}")
    private int listBrdCnt;
    
    /**
     * @author K140024
     * @implNote 모든 페이지네이션 시 노출할 최대 버튼 수 설정값 (application.yml)
     * @since 2024-06-11
     */
    @Value("${kdb.maxPageBtn}")
    private int maxPageBtn;
    
    /**
     * @author K140024
     * @implNote 게시글 관련 서비스
     * @since 2024-04-26
     */
    private final BoardService boardService;

    /**
     * @author K140024
     * @implNote 현재 인증된 사용자 정보를 가져오는 메소드
     * @since 2024-04-26
     */
    private Authentication authentication() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Authentication result;
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            result = null;
        } else {
            result = authentication;
        }
        return result;
    }    
    
    /**
     * @author K140024
     * @implNote 페이징 처리된 게시글 목록을 보여주는 메소드
     * @since 2024-04-26
     */
    @GetMapping({"/","/list"})
    public String paging(@PageableDefault(page = 1) final Pageable pageable, final Model model) {   
        final int currentPage = pageable.getPageNumber(); // 현재 페이지 (1-based index)
        final Pageable updatedPageable = PageRequest.of(currentPage, listBrdCnt);        
        final Page<BoardDTO> boardList = boardService.paging(updatedPageable);
        final int totalPages = boardList.getTotalPages();
        int startPage = Math.max(1, currentPage - (maxPageBtn / 2));
        final int endPage = Math.min(totalPages, startPage + maxPageBtn - 1);

        if (endPage - startPage < maxPageBtn - 1) {
            startPage = Math.max(1, endPage - maxPageBtn + 1);
        }
                
        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "/common/list";
    }

    /**
     * @author K140024
     * @implNote 게시글 작성 화면을 보여주는 메소드
     * @since 2024-04-26
     */
    @GetMapping("/editor")
    public String editor(final Model model) {    
    	model.addAttribute("board", new BoardDTO());
        return "/common/editor";
    }

    /**
     * @author K140024
     * @implNote 게시글 저장 화면을 보여주는 메소드
     * @since 2024-04-26
     */
    @GetMapping("/save")
    public String saveForm() {
        return "/common/save";
    }

    /**
     * @author K140024
     * @implNote 게시글 저장 처리 메소드
     * @since 2024-04-26
     */
    @PostMapping("/save")
    public ResponseEntity<String> save(@ModelAttribute final BoardDTO boardDTO) throws IOException {
        boardDTO.setBoardWriter(authentication().getName());        
        final Long seqId = boardService.save(boardDTO).getSeqId();
        final String redirectUrl = "/board/" + seqId;
        return ResponseEntity.ok(redirectUrl);
    }

    /**
     * @author K140024
     * @implNote 게시글 수정 처리 메소드
     * @since 2024-04-26
     */
    @PostMapping("/update")
    public String update(@ModelAttribute final BoardDTO boardDTO, final Model model) throws IOException {
        boardDTO.setBoardWriter(authentication().getName());
        final BoardDTO board = boardService.update(boardDTO);
        log.warn("업데이트 내용"+boardDTO.toString());
        model.addAttribute("board", board);
        return "/common/detail";
    }

    /**
     * @author K140024
     * @implNote 게시글 삭제 처리 메소드
     * @since 2024-04-26
     */
    @GetMapping("/delete/{seqId}")
    public ResponseEntity<String> delete(@PathVariable("seqId") final Long seqId) {
        boardService.delete(seqId);
        return ResponseEntity.ok("/board/list");
    }
    
    /**
     * @author K140024
     * @implNote 첨부파일 삭제 처리 메소드
     * @since 2024-04-26
     */
    @DeleteMapping("{boardId}/deleteFile/{seqId}")
    public ResponseEntity<String> deleteFile(@PathVariable("boardId") final Long boardId, @PathVariable("seqId") final Long seqId) {
        log.warn("deleteFile 컨트롤러");
    	return ResponseEntity.ok(boardService.deleteFile(seqId, boardId));
    }
    
    /**
     * @author K140024
     * @implNote 특정 게시물에 첨부된 파일목록 반환
     * @since 2024-06-13
     */
    @GetMapping("{boardId}/fileList")
    public ResponseEntity<List<BoardFileDTO>> getFileList(@PathVariable("boardId") final Long boardId) {
        final List<BoardFileDTO> boardFileList = boardService.findFile(boardId);
        return ResponseEntity.ok(boardFileList);
    }
    
    
    /**
     * @author K140024
     * @implNote 특정 게시글 상세 정보를 보여주는 메소드
     * @since 2024-04-26
     */
    @GetMapping("/{seqId}")
    public String findById(@PathVariable("seqId") final Long seqId, final Model model,
                           @PageableDefault(page = 1) final Pageable pageable) {
        final int FILE_ATTACHED = 1; // 상수로 리터럴 값을 추출

        boardService.updateHits(seqId);
        final BoardDTO boardDTO = boardService.findById(seqId);
        model.addAttribute("board", boardDTO);
        model.addAttribute("page", pageable.getPageNumber());
        log.warn(boardDTO.toString());
        
        // 파일이 첨부된 경우 첨부파일 리스트 반환
        if (boardDTO.getFileAttached() == FILE_ATTACHED) {
            final List<BoardFileDTO> boardFileDTOList = boardService.findFile(seqId);
            model.addAttribute("boardFileList", boardFileDTOList);
        }

        return "/common/detail";
    }

    /**
     * @author K140024
     * @implNote 게시글 수정 화면을 보여주는 메소드
     * @since 2024-04-26
     */
    @GetMapping("/update/{seqId}")
    public String updateForm(@PathVariable("seqId") final Long seqId, final Model model) {
        final int FILE_ATTACHED = 1; // 상수로 리터럴 값을 추출
        final BoardDTO boardDTO = boardService.findById(seqId);
        model.addAttribute("board", boardDTO);
        
        if (boardDTO.getFileAttached() == FILE_ATTACHED) {
            final List<BoardFileDTO> boardFileDTOList = boardService.findFile(seqId);
            model.addAttribute("boardFileList", boardFileDTOList);
        }
        
        return "/common/update";
    }    

    /**
     * @author K140024
     * @implNote 파일 다운로드 처리
     * @since 2024-04-26
     */
    @GetMapping("/{boardId}/download/{fileId}")
    public ResponseEntity<Resource> download(@PathVariable("boardId") Long boardId, @PathVariable("fileId") final Long fileId) throws IOException {
        // 파일정보 가져오기
        final BoardFileDTO boardfile = boardService.findOneFile(fileId);
        final UrlResource resource;
        ResponseEntity<Resource> responseEntity = ResponseEntity.status(500).body(null);
        log.warn("fileID:" + fileId);
        try {
            resource = new UrlResource("file:" + filepath + boardfile.getStoredFileName());
            
            // Header
            final String fileName = boardfile.getOriginalFileName();
            final String encodedFileName = UriUtils.encode(fileName, StandardCharsets.UTF_8);
            final String contentDisp = "attachment; filename=\"" + encodedFileName + "\"";
            responseEntity = ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisp)
                    .body(resource);
        } catch (IOException e) {
            log.error("The given file path is not valid", e);
        }
        
        return responseEntity;
    } 

    /**
     * @author K140024
     * @implNote 이미지 업로드 처리 메소드
     * @since 2024-04-26
     */
    @PostMapping("/upload/image")
    public ResponseEntity<?> uploadImage(@RequestParam("file") final MultipartFile file) {
        final String uploadDir = filepath; // 이미지 저장 경로 설정
        ResponseEntity<?> responseEntity = ResponseEntity.status(500).body("Error uploading file");
        try {
            final String originalFilename = file.getOriginalFilename();
            final String storedFilename = UUID.randomUUID().toString() + "_" + originalFilename;
            final Path path = Paths.get(uploadDir + storedFilename);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            final BoardImageDTO boardImageDTO = new BoardImageDTO();
            boardImageDTO.setOriginalFileName(originalFilename);
            boardImageDTO.setStoredFileName(storedFilename);
            boardService.saveBoardImage(boardImageDTO);

            final String fileUrl = "/board/upload/image/" + storedFilename;

            responseEntity = ResponseEntity.ok().body("{\"location\":\"" + fileUrl + "\"}");
        } catch (IOException e) {
            log.error("Error uploading file", e);
        }
        return responseEntity;
    }

    /**
     * @author K140024
     * @implNote 업로드된 이미지를 가져오는 메소드
     * @since 2024-04-26
     */
    @GetMapping("/upload/image/{storedFileName}")
    public ResponseEntity<Resource> getBoardImageById(@PathVariable("storedFileName") final String storedFileName) {
    	ResponseEntity<Resource> responseEntity =  ResponseEntity.status(500).body(null);
        
        try {
            final Path filePath = Paths.get(filepath + storedFileName);
            final Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                responseEntity = ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + storedFileName + "\"")
                        .body(resource);
            } else {
                responseEntity = ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            log.error("Error reading file", e);
        }
        return responseEntity;
    }
}
