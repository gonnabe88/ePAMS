package epams.domain.com.admin.repository;

import java.util.List;

import epams.domain.com.admin.dto.HtmlLangDetailDTO;
import epams.domain.com.admin.dto.HtmlLangLangMapDTO;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

/***
 * @author 140024
 * @implNote Code 정보를 DBMS에서 가져오기 위한 레파지토리 
 * @since 2024-06-14
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class HtmlLangMapRepository {

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
    public List<HtmlLangDetailDTO> findAllByHtml(final String html) {
        return sql.selectList("HtmlLangDetail.findAllByHtml", html);
    }   
    
    /***
	 * @author 140024
	 * @implNote paging 조건에 따라 모든 게시물을 조회하여 반환
	 * @since 2024-06-09
	 */
    public List<HtmlLangLangMapDTO> findAll() {
        return sql.selectList("HtmlLangMap.findAll");
    }
    
	/***
	 * @author 140024
	 * @implNote 기본키 검색
	 * @since 2024-06-09
	 */
    public Long countById(final HtmlLangLangMapDTO htmlLangMapDTO) {
        return sql.selectOne("HtmlLangMap.countById", htmlLangMapDTO);
    }
    
	/***
	 * @author 140024
	 * @implNote 신규 데이터 입력
	 * @since 2024-06-09
	 */
    public void insert(final HtmlLangLangMapDTO htmlLangMapDTO) {
        sql.insert("HtmlLangMap.insert", htmlLangMapDTO);
    }
    
	/***
	 * @author 140024
	 * @implNote 기존 데이터 삭제
	 * @since 2024-06-09
	 */
    public void delete(final HtmlLangLangMapDTO htmlLangMapDTO) {
        sql.delete("HtmlLangMap.delete", htmlLangMapDTO);
    }
    
	/***
	 * @author 140024
	 * @implNote 데이터 업데이트(기본키 제외)
	 * @since 2024-06-09
	 */
    public void update(final HtmlLangLangMapDTO htmlLangMapDTO) {
        sql.update("HtmlLangMap.update", htmlLangMapDTO);
    }
    
}