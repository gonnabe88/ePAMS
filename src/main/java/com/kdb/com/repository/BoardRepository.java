package com.kdb.com.repository;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kdb.com.dto.BoardDTO;
import com.kdb.com.dto.BoardFileDTO;
import com.kdb.com.dto.BoardImageDTO;

import lombok.RequiredArgsConstructor;

/***
 * @author 140024
 * @implNote 
 * @since 2024-06-09
 */
@Repository
@RequiredArgsConstructor
public class BoardRepository {
	
	/***
	 * @author 140024
	 * @implNote MyBatis DBMS 연결을 위한 템플릿 클래스
	 * @since 2024-06-10
	 */
    private final SqlSessionTemplate sql;

	/***
	 * @author 140024
	 * @implNote paging 조건에 따라 모든 게시물을 조회하여 반환
	 * @since 2024-06-09
	 */
    public List<BoardDTO> findAll(
    		final int offset, 
    		final int pageSize, 
    		final String sortColumn, 
    		final String sortDirection) {
        return sql.selectList("Board.findAll", Map.of("offset", offset, "limit", pageSize, "sortColumn", sortColumn, "sortDirection", sortDirection));
    }    
    
	/***
	 * @author 140024
	 * @implNote 전체 게시물 수를 반환
	 * @since 2024-06-09
	 */
    public long count() {
        return sql.selectOne("Board.count");
    }
    
	/***
	 * @author 140024
	 * @implNote 게시물 저장 후 ID 반환
	 * @since 2024-06-09
	 */
    public Long save(final BoardDTO boardDTO) {
    	sql.insert("Board.save", boardDTO);
    	return boardDTO.getSeqId();
    }
    
	/***
	 * @author 140024
	 * @implNote 게시물 업데이트
	 * @since 2024-06-09
	 */
    public Long update(final BoardDTO boardDTO) {
        sql.update("Board.update", boardDTO);
        return boardDTO.getSeqId();
    }    
    
	/***
	 * @author 140024
	 * @implNote 게시물 조회 후 BoardDTO 반환
	 * @since 2024-06-09
	 */
    public BoardDTO findById(final Long seqId) {
        return sql.selectOne("Board.findById", seqId);
    }
    
	/***
	 * @author 140024
	 * @implNote 게시물 조회 시 Hit+1
	 * @since 2024-06-09
	 */
    public void updateHits(final Long seqId) {
        sql.selectOne("Board.updateHits", seqId);
    }
    
	/***
	 * @author 140024
	 * @implNote 첨부파일 저장 후 ID 반환
	 * @since 2024-06-09
	 */
    public Long saveFile(final BoardFileDTO boardFileDTO) {
    	sql.insert("Board.saveFile", boardFileDTO);
    	return boardFileDTO.getSeqId();
    }    

    
	/***
	 * @author 140024
	 * @implNote 특정 첨부파일 조회 후 목록 반환
	 * @since 2024-06-09
	 */
    public BoardFileDTO findByFileId(final Long seqId) {
        return sql.selectOne("Board.findByFileId", seqId);
    }
    
	/***
	 * @author 140024
	 * @implNote 특정 게시물에 해당하는 첨부파일 삭제
	 * @since 2024-06-09
	 */
    public void delete(final Long seqId) {
        sql.delete("Board.delete", seqId);
    }

	/***
	 * @author 140024
	 * @implNote 게시물에 삽입된 이미지 추가
	 * @since 2024-06-09
	 */
    public void insertBoardImage(final BoardImageDTO boardImageDTO) {
    	sql.insert("Board.insertBoardImage", boardImageDTO);
    }
    
	/***
	 * @author 140024
	 * @implNote 게시물에 삽입된 이미지 찾기
	 * @since 2024-06-09
	 */
    public BoardImageDTO findBoardstoredFilename(final String storedFileName) {
    	return sql.selectOne("Board.selectBoardImage", storedFileName);
    }
    
    
}