package epams.domain.com.admin.repository;

import java.util.List;

import epams.domain.com.admin.dto.HtmlDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

/***
 * @author 140024
 * @implNote Code 정보를 DBMS에서 가져오기 위한 레파지토리 
 * @since 2024-06-14
 */
@Repository
@RequiredArgsConstructor
public class HtmlRepository {

	/***
	 * @author 140024
	 * @implNote MyBatis DBMS 연결을 위한 템플릿 클래스
	 * @since 2024-06-10
	 */
    private final SqlSessionTemplate sql;

    
	/***
	 * @author 140024
	 * @implNote paging 조건에 따라 모든 게시물을 조회하여 반환
	 * @since 2024-06-09final 
	 */
    public List<HtmlDTO> findAll() {
        return sql.selectList("Html.findAll");
    }
    
	/***
	 * @author 140024
	 * @implNote 기본키 검색
	 * @since 2024-06-09
	 */
    public Long countById(final HtmlDTO htmlDTO) {
        return sql.selectOne("Html.countById", htmlDTO);
    }
    
	/***
	 * @author 140024
	 * @implNote 신규 데이터 입력
	 * @since 2024-06-09
	 */
    public void insert(final HtmlDTO htmlDTO) {
        sql.insert("Html.insert", htmlDTO);
    }
    
	/***
	 * @author 140024
	 * @implNote 기존 데이터 삭제
	 * @since 2024-06-09
	 */
    public void delete(final HtmlDTO htmlDTO) {
        sql.delete("Html.delete", htmlDTO);
    }
    
	/***
	 * @author 140024
	 * @implNote 데이터 업데이트(기본키 제외)
	 * @since 2024-06-09
	 */
    public void update(final HtmlDTO htmlDTO) {
        sql.update("Html.update", htmlDTO);
    }
    
	/***
	 * @author 140024
	 * @implNote 데이터 입력 및 업데이트
	 * @since 2024-06-09
	 */
    public void insertUpdate(final HtmlDTO htmlDTO) {
        sql.insert("Html.insertUpdate", htmlDTO);
    }
    
    
}