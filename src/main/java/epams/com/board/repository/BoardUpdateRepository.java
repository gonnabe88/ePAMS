package epams.com.board.repository;


import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import epams.com.board.dto.BoardDTO;
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
public class BoardUpdateRepository {
	
	/***
	 * @author 140024
	 * @implNote MyBatis DBMS 연결을 위한 템플릿 클래스
	 * @since 2024-06-10
	 */
    private final SqlSessionTemplate sql;

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
	 * @implNote 게시물 조회 시 Hit+1
	 * @since 2024-06-09
	 */
    public void updateHits(final Long seqId) {
        sql.selectOne("Board.updateHits", seqId);
    }
}