package epams.com.board.repository;


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
public class BoardSaveRepository {
	
	/***
	 * @author 140024
	 * @implNote MyBatis DBMS 연결을 위한 템플릿 클래스
	 * @since 2024-06-10
	 */
    private final SqlSessionTemplate sql;

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
	 * @implNote 첨부파일 저장 후 ID 반환
	 * @since 2024-06-09
	 */
    public Long saveFile(final BoardFileDTO boardFileDTO) {
    	sql.insert("Board.saveFile", boardFileDTO.toEntity());
    	return boardFileDTO.getSeqId();
    }  
    
}