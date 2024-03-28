package com.kdb.common.repositogy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kdb.common.entity.BoardEntity;
import com.kdb.common.entity.CommentEntity;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    // select * from comment_table where board_id=? order by id desc;
    List<CommentEntity> findAllByBoardEntityOrderByIdDesc(BoardEntity boardEntity);
}