package epams.domain.com.board.repository;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import epams.domain.com.board.dto.BoardFileDTO;
import lombok.RequiredArgsConstructor;

/***
 * @author 140024
 * @implNote 
 * @since 2024-06-09
 */
@Slf4j
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
    	return (long)sql.insert("BoardFile.saveFile", boardFileDTO);
    }    

	/***
	 * @author 140024
	 * @implNote boardId에 해당하는 모든 첨부파일 조회 후 목록 반환
	 * @since 2024-06-09
	 */
    public List<BoardFileDTO> findFile(final Long boardId) {
		log.warn("boardId : " + boardId);
		List<BoardFileDTO> list = sql.selectList("BoardFile.findFile", boardId);
		log.warn("list : " + list);
        return list;
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
}