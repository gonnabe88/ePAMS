package epams.com.board.repository;


import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import epams.com.board.dto.BoardDTO;
import epams.com.board.dto.BoardFileDTO;
import epams.com.board.dto.BoardImageDTO;
import epams.com.board.entity.BoardEntity;
import epams.com.board.entity.BoardImageEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/***
 * @author 140024
 * @implNote 
 * @since 2024-06-09
 */
@Slf4j
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
    public List<BoardDTO> findAll(final int offset, final int pageSize, final String sortColumn, final String sortDirection) {
    	List<BoardEntity> boardEntities = sql.selectList("Board.findAll", Map.of("offset", offset, "limit", pageSize, "sortColumn", sortColumn, "sortDirection", sortDirection)); 
        return BoardDTO.toDTOList(boardEntities);
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
    public Long save(final BoardEntity boardEntity) {
    	sql.insert("Board.save", boardEntity);
    	return boardEntity.getSEQ_ID();
    }
    
	/***
	 * @author 140024
	 * @implNote 게시물 업데이트
	 * @since 2024-06-09
	 */
    public Long update(final BoardEntity boardEntity) {
        sql.update("Board.update", boardEntity);
        return boardEntity.getSEQ_ID();
    }    
    
	/***
	 * @author 140024
	 * @implNote 게시물 조회 후 BoardDTO 반환
	 * @since 2024-06-09
	 */
    public BoardDTO findById(final Long seqId) {
    	log.warn("Board.findById : "+seqId);
    	BoardEntity boardEntity = sql.selectOne("Board.findById", seqId);
        return BoardDTO.toDTO(boardEntity);
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
    	sql.insert("Board.saveFile", boardFileDTO.toEntity());
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
    	sql.insert("BoardImage.insertBoardImage", boardImageDTO.toEntity());
    }
    
	/***
	 * @author 140024
	 * @implNote 게시물에 삽입된 이미지 찾기
	 * @since 2024-06-09
	 */
    public BoardImageDTO findBoardstoredFilename(final String storedFileName) {
    	BoardImageEntity boardImageEntity = sql.selectOne("BoardImage.selectBoardImage", storedFileName);
    	return BoardImageDTO.toDTO(boardImageEntity);
    }
    
    
}