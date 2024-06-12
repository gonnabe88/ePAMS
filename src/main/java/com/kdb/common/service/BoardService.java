package com.kdb.common.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kdb.common.dto.BoardDTO;
import com.kdb.common.dto.BoardFileDTO;
import com.kdb.common.dto.BoardImageDTO;
import com.kdb.common.repository.BoardFileRepository2;
import com.kdb.common.repository.BoardRepository2;

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
public class BoardService {
    
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
    private final BoardRepository2 boardRepository2;
    
    /**
     * @author K140024
     * @implNote BoardFileRepository2 주입
     * @since 2024-04-26
     */
    private final BoardFileRepository2 boardFileRepository2;
    
    /**
     * @author K140024
     * @implNote 게시물 내 이미지 저장
     * @since 2024-06-11
     */
    public void saveBoardImage(final BoardImageDTO boardImageDTO) {
        boardRepository2.insertBoardImage(boardImageDTO); // 게시물 내 이미지 저장
    }
    
    /**
     * @author K140024
     * @implNote 저장 파일명을 통한 이미지 객체 반환
     * @since 2024-06-11
     */
    public BoardImageDTO getBoardImageBystoredFilename(final String storedFileName) {
        return boardRepository2.findBoardstoredFilename(storedFileName); // 이미지 객체 반환
    }

    /**
     * @author K140024
     * @implNote 게시물 업데이트
     * @since 2024-04-26
     */
    public BoardDTO update(final BoardDTO boardDTO) throws IOException {
        if (boardDTO.getBoardFile() == null) { // 첨부 파일 없는 경우
            boardRepository2.update(boardDTO); // 게시물 업데이트
        } else { // 첨부 파일 있는 경우
            boardDTO.setFileAttached(1); // 첨부파일 여부 1 세팅
            boardRepository2.save(boardDTO); // 게시물 업데이트
            
            for (final MultipartFile boardFile : boardDTO.getBoardFile()) {
                final String originalFilename = boardFile.getOriginalFilename(); // 파일명 세팅
                final String storedFileName = System.currentTimeMillis() + "_" + originalFilename; // 저장 파일명 세팅
                final String savePath = filepath + storedFileName; // 파일 저장경로 세팅
                boardFile.transferTo(new File(savePath)); // 스토리지에 파일 저장
                final BoardFileDTO boardFileDTO = BoardFileDTO.toBoardFileDTO(boardDTO, originalFilename, storedFileName, filepath); // DBMS에 저장할 파일정보 객체 세팅
                boardFileRepository2.saveFile(boardFileDTO); // DBMS에 파일 정보 저장
            }
        }
        return findById(boardDTO.getSeqId()); // 업데이트한 게시물 ID 리턴
    }

    /**
     * @author K140024
     * @implNote 게시물 저장
     * @since 2024-04-26
     */
    public BoardDTO save(final BoardDTO boardDTO) throws IOException {
        // 파일 첨부 여부에 따라 로직 분리
        if (boardDTO.getBoardFile() == null) {  // 첨부 파일 없는 경우
            boardRepository2.save(boardDTO);  // 게시물 신규 저장
        } else { // 첨부 파일 있는 경우
            boardDTO.setFileAttached(1); // 첨부파일 여부 1 세팅
            boardRepository2.save(boardDTO); // 게시물 업데이트
            
            for (final MultipartFile boardFile : boardDTO.getBoardFile()) {
            	final String originalFilename = boardFile.getOriginalFilename();  // 파일명 세팅
            	final String storedFileName = System.currentTimeMillis() + "_" + originalFilename; // 저장 파일명 세팅
            	final String savePath = filepath + storedFileName; // 파일 저장경로 세팅
                boardFile.transferTo(new File(savePath)); // 스토리지에 파일 저장
                final BoardFileDTO boardFileDTO = BoardFileDTO.toBoardFileDTO(boardDTO, originalFilename, storedFileName, filepath); // DBMS에 저장할 파일정보 객체 세팅
                boardFileRepository2.saveFile(boardFileDTO); // DBMS에 파일 정보 저장
            }
        }
        return findById(boardDTO.getSeqId()); // 저장한 게시물 ID 리턴
    }

    /**
     * @author K140024
     * @implNote 게시물 삭제
     * @since 2024-04-26
     */
    public void delete(final Long seqId) {
    	final BoardDTO boardDTO = boardRepository2.findById(seqId); // 게시글 삭제
    	final List<BoardFileDTO> boardFiles = boardFileRepository2.findFile(seqId); // 첨부파일 정보 반환
        
        // 첨부파일이 없는 경우 DB만 삭제
        if (boardDTO.getFileAttached() == 0) {
            log.warn("No files");
            boardFileRepository2.deleteFile(seqId);
        }
        // 첨부파일이 있는 경우 File도 삭제
        else {
            boardFileRepository2.deleteFile(seqId);
            for (final BoardFileDTO boardFile : boardFiles) {
            	final File file = new File(boardFile.getStoredPath() + boardFile.getStoredFileName());
            	final boolean result = file.delete();
                if (log.isWarnEnabled()) {
                    log.warn(result + " Delete files : " + boardFile.getStoredFileName());
                }
            }
        }
    }

    /**
     * @author K140024
     * @implNote 게시물 조회수 업데이트
     * @since 2024-04-26
     */
    @Transactional
    public void updateHits(final Long seqId) {
        boardRepository2.updateHits(seqId);
    }

    /**
     * @author K140024
     * @implNote 게시물 ID로 게시물 조회
     * @since 2024-04-26
     */
    @Transactional
    public BoardDTO findById(final Long seqId) {
        return boardRepository2.findById(seqId);
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
    	final List<BoardDTO> boardDTOs = boardRepository2.findAll(offset, pageSize, "SEQ_ID", "DESC");

        // 전체 데이터 개수 조회
    	final long totalElements = boardRepository2.count(); // 전체 데이터 개수를 조회하는 메소드 필요
        return new PageImpl<>(boardDTOs, PageRequest.of(page, pageSize), totalElements);
    }

    /**
     * @author K140024
     * @implNote 파일 ID로 파일 정보 조회
     * @since 2024-04-26
     */
    public BoardFileDTO findOneFile(final Long seqId) {
        return boardFileRepository2.findByFileId(seqId);
    }

    /**
     * @author K140024
     * @implNote 게시물 ID로 파일 목록 조회
     * @since 2024-04-26
     */
    public List<BoardFileDTO> findFile(final Long seqId) {
        return boardFileRepository2.findFile(seqId);
    }
}
