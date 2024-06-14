package com.kdb.com.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

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
public class BoardFileRepository {
	
	/***
	 * @author 140024
	 * @implNote MyBatis DBMS 연결을 위한 템플릿 클래스
	 * @since 2024-06-10
	 */
    private final SqlSessionTemplate sql;

    
	/***
	 * @author 140024
	 * @implNote 첨부파일 저장 후 ID 반환
	 * @since 2024-06-09
	 */
    public Long saveFile(final BoardFileDTO boardFileDTO) {
    	sql.insert("BoardFile.saveFile", boardFileDTO);
    	return boardFileDTO.getSeqId();
    }    

	/***
	 * @author 140024
	 * @implNote boardId에 해당하는 모든 첨부파일 조회 후 목록 반환
	 * @since 2024-06-09
	 */
    public List<BoardFileDTO> findFile(final Long boardId) {
        return sql.selectList("BoardFile.findFile", boardId);
    }
    
	/***
	 * @author 140024
	 * @implNote 특정 첨부파일 조회 후 목록 반환
	 * @since 2024-06-09
	 */
    public BoardFileDTO findByFileId(final Long seqId) {
        return sql.selectOne("BoardFile.findByFileId", seqId);
    }

	/***
	 * @author 140024
	 * @implNote 특정 게시물에 해당하는 첨부파일 삭제
	 * @since 2024-06-09
	 */
    public void deleteFile(final Long seqId) {
        sql.delete("BoardFile.deleteFile", seqId);
    }

	/***
	 * @author 140024
	 * @implNote 게시물에 삽입된 이미지 추가
	 * @since 2024-06-09
	 */
    public void insertBoardImage(final BoardImageDTO boardImageDTO) {
    	sql.insert("BoardImage.insertBoardImage", boardImageDTO);
    }
    
	/***
	 * @author 140024
	 * @implNote 게시물에 삽입된 이미지 찾기
	 * @since 2024-06-09
	 */
    public BoardImageDTO findBoardstoredFilename(final String storedFileName) {
    	return sql.selectOne("BoardImage.selectBoardImage", storedFileName);
    }
    
    
}