package epams.domain.com.board.service;

import java.io.File;
import java.io.IOException;

import epams.domain.com.board.repository.BoardFileRepository;
import epams.domain.com.board.repository.BoardMainRepository;
import epams.domain.com.board.repository.BoardSaveRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
public class BoardSaveService {

    /**
     * @author K140024
     * @implNote file 시스템에 저장할 실제 경로 설정값 (application.yml)
     * @since 2024-06-11
     */
    @Value("${kdb.filepath}")
    private String filepath;

    /**
     * @author K140024
     * @implNote BoardRepository2 주입
     * @since 2024-04-26
     */
    private final BoardSaveRepository boardSaveRepo;

    /**
     * @author K140024
     * @implNote BoardMainRepository 주입
     * @since 2024-04-26
     */
    private final BoardMainRepository boardMainRepo;
    
    /**
     * @author K140024
     * @implNote BoardFileRepository2 주입
     * @since 2024-04-26
     */
    private final BoardFileRepository boardFileRepo;

    /**
     * @author K140024
     * @implNote 게시물 저장
     * @since 2024-04-26
     */
    public BoardDTO save(final BoardDTO newBoardDTO) throws IOException {
        final int FILE_ATTACHED = 1; // 상수로 리터럴 값을 추출 (취약점 조치건)

        if (newBoardDTO.getBoardFile() != null) { // 새로운 첨부파일이 있는 경우
            newBoardDTO.setFileAttached(FILE_ATTACHED); // 첨부파일 여부 1 세팅
            newBoardDTO.setSeqId(boardSaveRepo.save(newBoardDTO)); // 게시글 저장 & 채번된 게시글 ID 세팅
            for (final MultipartFile boardFile : newBoardDTO.getBoardFile()) {
                final String originalFilename = boardFile.getOriginalFilename(); // 파일명 세팅
                final String storedFileName = System.currentTimeMillis() + "_" + originalFilename; // 저장 파일명 세팅
                final String savePath = filepath + storedFileName; // 파일 저장경로 세팅
                boardFile.transferTo(new File(savePath)); // 스토리지에 파일 저장
                final BoardFileDTO boardFileDTO = BoardFileDTO.toBoardFileDTO(newBoardDTO, originalFilename,
                        storedFileName, filepath); // DBMS에 저장할 파일정보 객체 세팅
                boardFileRepo.saveFile(boardFileDTO); // DBMS에 파일 정보 저장
            }
        } else {
            newBoardDTO.setSeqId(boardSaveRepo.save(newBoardDTO)); // 첨부파일이 없는 경우
        }
        return boardMainRepo.findById(newBoardDTO.getSeqId()); // 저장한 게시물 객체 리턴
    }
}