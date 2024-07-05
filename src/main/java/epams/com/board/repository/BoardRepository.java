package epams.com.board.repository;


import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import epams.com.board.dto.BoardDTO;
import epams.com.board.dto.BoardFileDTO;
import epams.com.board.entity.BoardEntity;
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
    public List<BoardDTO> findAll(final int offset, final int pageSize, final String sortDirection) {
    	final List<BoardEntity> boardEntities = sql.selectList("Board.findAll", Map.of("offset", offset, "limit", pageSize, "sortDirection", sortDirection)); 
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
    public Long save(final BoardDTO boardDTO) {    	
		final BoardEntity entity = boardDTO.toEntity();
		sql.insert("Board.save", entity);
    	return entity.getBLB_SNO();
    }
    
	/***
	 * @author 140024
	 * @implNote 게시물 업데이트
	 * @since 2024-06-09
	 */
    public Long update(final BoardDTO boardDTO) {      
		final BoardEntity entity = boardDTO.toEntity();  
        sql.update("Board.update", entity);
		return entity.getBLB_SNO();
    }    
    
	/***
	 * @author 140024
	 * @implNote 게시물 조회 후 BoardDTO 반환
	 * @since 2024-06-09
	 */
    public BoardDTO findById(final Long seqId) {
    	final BoardEntity boardEntity = sql.selectOne("Board.findById", seqId);
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
}