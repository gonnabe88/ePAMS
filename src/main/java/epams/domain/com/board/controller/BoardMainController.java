package epams.domain.com.board.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriUtils;

import epams.domain.com.board.dto.BoardDTO;
import epams.domain.com.board.dto.BoardFileDTO;
import epams.domain.com.board.service.BoardMainService;
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
public class BoardMainController {

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
    private final BoardMainService boardService;

    /**
     * @author K140024
     * @implNote 페이징 처리된 게시글 목록을 보여주는 메소드
     * @since 2024-04-26
     */
    @GetMapping({"/faq","/faq/list"})
    public String findAllFaq(final Model model) {

        final List<BoardDTO> boardList = boardService.findAllFaq();

        model.addAttribute("boardList", boardList);
        return "common/faqList";
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
        return "common/boardList";
    }

    /**
     * @author K140024
     * @implNote 게시글 작성 화면을 보여주는 메소드
     * @since 2024-04-26
     */
    @GetMapping("/editor")
    public String editor(final Model model) {    
    	model.addAttribute("board", new BoardDTO());
        return "common/boardEditor";
    }

    /**
     * @author K140024
     * @implNote 게시글 작성 화면을 보여주는 메소드
     * @since 2024-04-26
     */
    @GetMapping("/faq/editor")
    public String faqEditor(final Model model) {
        model.addAttribute("board", new BoardDTO());
        return "common/faqEditor";
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
        final String FILE_ATTACHED = "1"; // 상수로 리터럴 값을 추출

        boardService.updateHits(seqId);
        final BoardDTO boardDTO = boardService.findById(seqId);
        model.addAttribute("board", boardDTO);
        model.addAttribute("page", pageable.getPageNumber());
        
        // 파일이 첨부된 경우 첨부파일 리스트 반환
        if (FILE_ATTACHED.equals(boardDTO.getFileAttached())) {
            log.warn("File attached");
            final List<BoardFileDTO> boardFileDTOList = boardService.findFile(seqId);
            log.warn("File list: {}", boardFileDTOList);
            model.addAttribute("boardFileList", boardFileDTOList);
        }

        return "common/boardDetail";
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
}