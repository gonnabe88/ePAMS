package com.kdb.common.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kdb.common.dto.BoardDTO;
import com.kdb.common.entity.BoardEntity;
import com.kdb.common.dto.BoardFileDTO;

import lombok.RequiredArgsConstructor;


@Repository
@RequiredArgsConstructor
public class BoardRepository2 {
	
    private final SqlSessionTemplate sql;
    public List<BoardFileDTO> findFile(Long id) {
        return sql.selectList("Board.findFile", id);
    }    
    public BoardDTO findById(Long id) {
        return sql.selectOne("Board.findById", id);
    }
    public BoardFileDTO findByFileId(Long id) {
        return sql.selectOne("Board.findByFileId", id);
    }
    public void initFile(Long id) {
        sql.delete("Board.initFile", id);
    }
    public void update(BoardEntity boardEntity) {
        sql.update("Board.update", boardEntity);
    }
    
    
}