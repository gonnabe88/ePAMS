package com.kdb.common.repositogy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kdb.common.entity.BoardFileEntity;

public interface BoardFileRepository extends JpaRepository<BoardFileEntity, Long> {
}