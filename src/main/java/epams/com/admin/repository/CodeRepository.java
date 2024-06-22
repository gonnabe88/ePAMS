package epams.com.admin.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import epams.com.admin.dto.CodeDTO;
import epams.com.admin.entity.CodeEntity;
import lombok.RequiredArgsConstructor;

/***
 * @author 140024
 * @implNote Code 정보를 DBMS에서 가져오기 위한 레파지토리 
 * @since 2024-06-14
 */
@Repository
@RequiredArgsConstructor
public class CodeRepository {

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
    public List<CodeDTO> findAll() {
    	final List<CodeEntity> codeEntities = sql.selectList("Code.findAll");
        return CodeDTO.toDTOList(codeEntities);
    }
    
	/***
	 * @author 140024
	 * @implNote 기본키 검색
	 * @since 2024-06-09
	 */
    public Long countById(final CodeDTO CodeDTO) {
        return sql.selectOne("Code.countById", CodeDTO.toEntity());
    }
    
	/***
	 * @author 140024
	 * @implNote 신규 데이터 입력
	 * @since 2024-06-09
	 */
    public void insert(final CodeDTO CodeDTO) {
        sql.insert("Code.insert", CodeDTO.toEntity());
    }
    
	/***
	 * @author 140024
	 * @implNote 기존 데이터 삭제
	 * @since 2024-06-09
	 */
    public void delete(final CodeDTO CodeDTO) {
        sql.delete("Code.delete", CodeDTO.toEntity());
    }
    
	/***
	 * @author 140024
	 * @implNote 데이터 업데이트(기본키 제외)
	 * @since 2024-06-09
	 */
    public void update(final CodeDTO CodeDTO) {
        sql.update("Code.update", CodeDTO.toEntity());
    }
    
}