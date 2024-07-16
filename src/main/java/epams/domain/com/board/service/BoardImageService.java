package epams.domain.com.board.service;

import epams.domain.com.board.repository.BoardImageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import epams.domain.com.board.dto.BoardImageDTO;
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
public class BoardImageService {

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
    private final BoardImageRepository boardImgRepo;

    /**
     * @author K140024
     * @implNote 게시물 내 이미지 저장
     * @since 2024-06-11
     */
    public void saveBoardImage(final BoardImageDTO boardImageDTO) {
    	boardImgRepo.insertBoardImage(boardImageDTO); // 게시물 내 이미지 저장
    }

    /**
     * @author K140024
     * @implNote 저장 파일명을 통한 이미지 객체 반환
     * @since 2024-06-11
     */
    public BoardImageDTO getBoardImageBystoredFilename(final String storedFileName) {
        return boardImgRepo.findBoardstoredFilename(storedFileName); // 이미지 객체 반환
    }

}