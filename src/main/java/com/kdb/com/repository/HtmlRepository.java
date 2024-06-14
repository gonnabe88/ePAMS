package com.kdb.com.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kdb.com.dto.CodeDTO;
import com.kdb.com.dto.HtmlDTO;
import com.kdb.com.entity.HtmlEntity;

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
	 * @since 2024-06-09
	 */
    public List<HtmlDTO> findAll() {
    	final List<HtmlEntity> htmlEntitys = sql.selectList("Html.findAll");
        return HtmlDTO.toDTOList(htmlEntitys);
    }
    
	/***
	 * @author 140024
	 * @implNote 기본키 검색
	 * @since 2024-06-09
	 */
    public Long countById(HtmlDTO htmlDTO) {
        return sql.selectOne("Html.countById", htmlDTO.toEntity());
    }
    
	/***
	 * @author 140024
	 * @implNote 신규 데이터 입력
	 * @since 2024-06-09
	 */
    public void insert(HtmlDTO htmlDTO) {
        sql.insert("Html.insert", htmlDTO.toEntity());
    }
    
	/***
	 * @author 140024
	 * @implNote 기존 데이터 삭제
	 * @since 2024-06-09
	 */
    public void delete(HtmlDTO htmlDTO) {
        sql.delete("Html.delete", htmlDTO.toEntity());
    }
    
	/***
	 * @author 140024
	 * @implNote 데이터 업데이트(기본키 제외)
	 * @since 2024-06-09
	 */
    public void update(HtmlDTO htmlDTO) {
        sql.update("Html.update", htmlDTO.toEntity());
    }
    
}