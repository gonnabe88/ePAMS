package com.kdb.com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kdb.com.entity.MemberEntity;
import com.kdb.com.entity.MfaEntity;

public interface MfaRepository extends JpaRepository<MfaEntity, Long>{
	
	Optional<MfaEntity> findTop1ByUsernameOrderByIdDesc(String username);

}
