package epams.domain.com.board.repository;


import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import epams.domain.com.board.dto.BoardDTO;
import epams.domain.com.board.dto.BoardFileDTO;
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
public class BoardMainRepository {
	
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
	public List<BoardDTO> findAllFaq() {
		return sql.selectList("Board.findAllFaq");
	}

	/***
	 * @author 140024
	 * @implNote paging 조건에 따라 모든 게시물을 조회하여 반환
	 * @since 2024-06-09
	 */
    public List<BoardDTO> findAll(final String boardType, final int offset, final int pageSize, final String sortDirection) {
		log.warn(boardType);
        return sql.selectList("Board.findAll", Map.of("boardType", boardType,"offset", offset, "limit", pageSize, "sortDirection", sortDirection));
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
	 * @implNote 게시물 조회 후 BoardDTO 반환
	 * @since 2024-06-09
	 */
    public BoardDTO findById(final Long seqId) {
        return sql.selectOne("Board.findById", seqId);
    }
    
	/***
	 * @author 140024
	 * @implNote 전체 게시물 수를 반환
	 * @since 2024-06-09
	 */
    public long count(String boardType) {
        return sql.selectOne("Board.count", boardType);
    }
    
}