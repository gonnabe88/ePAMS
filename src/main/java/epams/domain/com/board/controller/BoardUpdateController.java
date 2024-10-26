package epams.domain.com.board.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
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

import epams.domain.com.board.dto.BoardDTO;
import epams.domain.com.board.dto.BoardFileDTO;
import epams.domain.com.board.service.BoardMainService;
import epams.domain.com.board.service.BoardUpdateService;
import epams.framework.exception.CustomGeneralRuntimeException;
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
public class BoardUpdateController {

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
    private final BoardMainService boardMainServ;
    
    /**
     * @author K140024
     * @implNote 게시글 관련 서비스
     * @since 2024-04-26
     */
    private final BoardUpdateService boardUpdateServ;

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
     * @implNote 게시글 수정 처리 메소드
     * @since 2024-04-26
     */
    @PostMapping("/update")
    public String update(@ModelAttribute final BoardDTO boardDTO, final Model model) throws IOException {
    	Authentication tempNullableVar = authentication();
    	if(tempNullableVar != null) {
    		boardDTO.setBoardWriter(tempNullableVar.getName());
    	} else {
    		throw new CustomGeneralRuntimeException("로그인이 만료되었습니다. 로그인 후 사용해주세요");
    	}
        final BoardDTO board = boardUpdateServ.update(boardDTO);
        model.addAttribute("board", board);
        return "common/boardDetail";
    }

    /**
     * @author K140024
     * @implNote 게시글 수정 화면을 보여주는 메소드
     * @since 2024-04-26
     */
    @GetMapping("/update/{seqId}")
    public String updateForm(@PathVariable("seqId") final Long seqId, final Model model) {
        final String FILE_ATTACHED = "1"; // 상수로 리터럴 값을 추출
        final BoardDTO boardDTO = boardMainServ.findById(seqId);
        model.addAttribute("board", boardDTO);
        
        if (FILE_ATTACHED.equals(boardDTO.getFileAttached())) {
            final List<BoardFileDTO> boardFileDTOList = boardMainServ.findFile(seqId);
            model.addAttribute("boardFileList", boardFileDTOList);
        }
        
        return "common/boardUpdate";
    }    

}