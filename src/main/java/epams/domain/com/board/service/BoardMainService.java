package epams.domain.com.board.service;

import java.util.List;

import epams.domain.com.board.repository.BoardFileRepository;
import epams.domain.com.board.repository.BoardMainRepository;
import epams.domain.com.board.repository.BoardUpdateRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class BoardMainService {

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
     * @implNote 게시물 조회수 업데이트
     * @since 2024-04-26
     */
    @Transactional
    public void updateHits(final Long seqId) {
    	boardUpdateRepo.updateHits(seqId);
    }

    /**
     * @author K140024
     * @implNote 페이지네이션으로 게시물 목록 조회
     * @since 2024-04-26
     */
    public List<BoardDTO> findAllFaq() {
        final List<BoardDTO> boardDTOs = boardMainRepo.findAllFaq();
        return boardDTOs;
    }

    /**
     * @author K140024
     * @implNote 페이지네이션으로 게시물 목록 조회
     * @since 2024-04-26
     */
    public Page<BoardDTO> paging(final Pageable pageable) {
        final int page = pageable.getPageNumber() - 1; // 페이지 번호
        final int pageSize = pageable.getPageSize(); // 페이지 크기
        final int offset = page * pageSize; // 오프셋 계산
        // 한 페이지당 3개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬
        final List<BoardDTO> boardDTOs = boardMainRepo.findAll(offset, pageSize, "DESC");
        // 전체 데이터 개수 조회
        final long totalElements = boardMainRepo.count(); // 전체 데이터 개수를 조회하는 메소드 필요
        return new PageImpl<>(boardDTOs, PageRequest.of(page, pageSize), totalElements);
    }

    /**
     * @author K140024
     * @implNote 파일 ID로 파일 정보 조회
     * @since 2024-04-26
     */
    public BoardFileDTO findOneFile(final Long seqId) {
        return boardFileRepo.findByFileId(seqId);
    }
    
    /**
     * @author K140024
     * @implNote 게시물 ID로 파일 목록 조회
     * @since 2024-04-26
     */
    public List<BoardFileDTO> findFile(final Long seqId) {
        return boardFileRepo.findFile(seqId);
    }
    
    /**
     * @author K140024
     * @implNote 게시물 ID로 게시물 조회
     * @since 2024-04-26
     */
    @Transactional
    public BoardDTO findById(final Long seqId) {
        return boardMainRepo.findById(seqId);
    }

}