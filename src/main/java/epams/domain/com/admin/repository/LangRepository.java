package epams.domain.com.admin.repository;

import java.util.List;
import epams.domain.com.admin.dto.LangDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

/***
 * @author 140024
 * @implNote Lang 정보를 DBMS에서 가져오기 위한 레파지토리 
 * @since 2024-06-14
 */
@Repository
@RequiredArgsConstructor
public class LangRepository {

	/***
	 * @author 140024
	 * @implNote MyBatis DBMS 연결을 위한 템플릿 클래스
	 * @since 2024-06-10
	 */
    private final SqlSessionTemplate sql;
    
	/***
	 * @author 140024
	 * @implNote 모든 내용 조회
	 * @since 2024-06-09
	 */
    public List<LangDTO> findAll() {
        return sql.selectList("Lang.findAll");
    }

	/***
	 * @author 140024
	 * @implNote 기본키 검색
	 * @since 2024-06-09
	 */
    public Long countById(final LangDTO langDTO) {
        return sql.selectOne("Lang.countById", langDTO);
    }
    
	/***
	 * @author 140024
	 * @implNote 신규 데이터 입력
	 * @since 2024-06-09
	 */
    public void insert(final LangDTO langDTO) {
        sql.insert("Lang.insert", langDTO);
    }
    
	/***
	 * @author 140024
	 * @implNote 기존 데이터 삭제
	 * @since 2024-06-09
	 */
    public void delete(final LangDTO langDTO) {
        sql.delete("Lang.delete", langDTO);
    }
    
	/***
	 * @author 140024
	 * @implNote 데이터 업데이트(기본키 제외)
	 * @since 2024-06-09
	 */
    public void update(final LangDTO langDTO) {
        sql.update("Lang.update", langDTO);
    }

	public String findLangById(final String code) {    	
    	
    	return sql.selectOne("Lang.findLangById", code);
    }
    
}