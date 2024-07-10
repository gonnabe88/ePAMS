package epams.com.board.repository;


import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import epams.com.board.dto.BoardImageDTO;
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
public class BoardImageRepository {
	
	/***
	 * @author 140024
	 * @implNote MyBatis DBMS 연결을 위한 템플릿 클래스
	 * @since 2024-06-10
	 */
    private final SqlSessionTemplate sql;

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