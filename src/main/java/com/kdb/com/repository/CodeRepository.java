package com.kdb.com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kdb.com.entity.CodeEntity;
import com.kdb.com.entity.MemberEntity;

import jakarta.transaction.Transactional;

public interface CodeRepository extends JpaRepository<CodeEntity, Long> {
	
    @Transactional
    @Query(value = "SELECT * FROM COMMON_CODE C WHERE CD_HTML=%:html%",  nativeQuery = true)
    List<CodeEntity> findByHtml(@Param("html") String html);
    
}