package com.kdb.com.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kdb.com.dto.CodeDTO;
import com.kdb.com.dto.CodeHtmlDetailDTO;
import com.kdb.com.entity.CodeEntity;

import lombok.RequiredArgsConstructor;

/***
 * @author 140024
 * @implNote Code 정보를 DBMS에서 가져오기 위한 레파지토리 
 * @since 2024-06-14
 */
@Repository
@RequiredArgsConstructor
public class CodeHtmlDetailRepository {

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
    public List<CodeHtmlDetailDTO> findAllByHtml(final String html) {
    	final List<CodeHtmlDetailDTO> codeHtmlDetails = sql.selectList("JoinCodeHtmlDetail.findAllByHtml", html);
        return codeHtmlDetails;
    }   
    
}