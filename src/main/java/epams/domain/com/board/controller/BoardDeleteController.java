package epams.domain.com.board.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import epams.domain.com.board.service.BoardDeleteService;
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
public class BoardDeleteController {

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
    private final BoardDeleteService boardDelService;

    /**
     * @author K140024
     * @implNote 게시글 삭제 처리 메소드
     * @since 2024-04-26
     */
    @GetMapping("/delete/{seqId}")
    public ResponseEntity<String> delete(@PathVariable("seqId") final Long seqId) {
        boardDelService.delete(seqId);
        return ResponseEntity.ok("board/list");
    }
    
    /**
     * @author K140024
     * @implNote 첨부파일 삭제 처리 메소드
     * @since 2024-04-26
     */
    @DeleteMapping("{boardId}/deleteFile/{seqId}")
    public ResponseEntity<String> deleteFile(@PathVariable("boardId") final Long boardId, @PathVariable("seqId") final Long seqId) {
        log.warn("deleteFile 컨트롤러");
    	return ResponseEntity.ok(boardDelService.deleteFile(seqId, boardId));
    }
}