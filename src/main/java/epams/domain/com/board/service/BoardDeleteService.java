package epams.domain.com.board.service;

import java.io.File;
import java.util.List;

import epams.domain.com.board.repository.BoardDeleteRepository;
import epams.domain.com.board.repository.BoardFileRepository;
import epams.domain.com.board.repository.BoardMainRepository;
import epams.domain.com.board.repository.BoardUpdateRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import epams.domain.com.board.dto.BoardDTO;
import epams.domain.com.board.dto.BoardFileDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author K140024
 * @implNote 게시판 service
 * @since 2024-04-26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BoardDeleteService {

    /**
     * @author K140024
     * @implNote file 시스템에 저장할 실제 경로 설정값 (application.yml)
     * @since 2024-06-11
     */
    @Value("${kdb.filepath}")
    private String filepath;

    /**
     * @author K140024
     * @implNote BoardMainRepository 주입
     * @since 2024-04-26
     */
    private final BoardMainRepository boardMainRepo;

    /**
     * @author K140024
     * @implNote BoardFileRepository 주입
     * @since 2024-04-26
     */
    private final BoardFileRepository boardFileRepo;
    
    /**
     * @author K140024
     * @implNote BoardUpdateRepository 주입
     * @since 2024-04-26
     */
    private final BoardUpdateRepository boardUpdateRepo;
    
    /**
     * @author K140024
     * @implNote BoardDeleteRepository 주입
     * @since 2024-04-26
     */
    private final BoardDeleteRepository boardDeleteRepo;

    /**
     * @author K140024
     * @implNote 게시물 삭제
     * @since 2024-04-26
     */
    public void delete(final Long seqId) {
        final BoardDTO boardDTO = boardMainRepo.findById(seqId); // 게시글 정보 반환
        final List<BoardFileDTO> boardFiles = boardFileRepo.findFile(seqId); // 첨부파일 정보 반환
        final String FILE_ATTACHED = "1"; // 상수로 리터럴 값을 추출

        boardDeleteRepo.delete(seqId); // 게시글 삭제
        // 첨부파일이 있는 경우 첨부파일 테이블 및 파일 삭제
        if (FILE_ATTACHED.equals(boardDTO.getFileAttached())) {
            boardFileRepo.deleteFile(seqId); // 첨부파일 테이블 삭제
            for (final BoardFileDTO boardFile : boardFiles) {
                final File file = new File(boardFile.getStoredPath() + boardFile.getStoredFileName());
                file.delete(); // 파일 삭제
            }
        }
    }

    /**
     * @author K140024
     * @implNote 첨부파일 삭제
     * @since 2024-06-13
     */
    public String deleteFile(final Long seqId, final Long boardId) {
        final BoardDTO boardDTO = boardMainRepo.findById(boardId); // 게시글 정보 반환
        final BoardFileDTO delBoardFile = boardFileRepo.findByFileId(seqId); // 삭제 대상첨부파일 정보 반환
        final File file = new File(delBoardFile.getStoredPath() + delBoardFile.getStoredFileName());
        final String fileName = delBoardFile.getOriginalFileName();
        final String FILE_NOT_ATTACHED = "0"; // 상수로 리터럴 값을 추출

        boardFileRepo.deleteFile(seqId); // 첨부파일 테이블 삭제
        file.delete(); // 파일 삭제

        final List<BoardFileDTO> boardFiles = boardFileRepo.findFile(boardId); // 첨부파일 정보 반환
        if (boardFiles.isEmpty()) { // 삭제 대상 첨부파일 삭제 후 남은 첨부파일이 없으면
            boardDTO.setFileAttached(FILE_NOT_ATTACHED); // 첨부파일 없는 게시물로 객체 세팅 후
            boardUpdateRepo.update(boardDTO); // 업데이트 FileAttached = 0(첨부없음)
        }

        // 삭제한 파일명 리턴
        return fileName;
    }
}