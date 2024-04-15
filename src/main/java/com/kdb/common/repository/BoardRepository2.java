package com.kdb.common.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kdb.example.board.BoardFileDTO;

import lombok.RequiredArgsConstructor;


@Repository
@RequiredArgsConstructor
public class BoardRepository2 {
	
    private final SqlSessionTemplate sql;
    public List<BoardFileDTO> findFile(Long id) {
        return sql.selectList("Board.findFile", id);
    }
    
}