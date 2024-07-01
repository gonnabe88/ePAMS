package epams.com.member.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import epams.com.member.entity.TempUserEntity;
import epams.com.member.entity.SearchMemberEntity;
import jakarta.transaction.Transactional;

public interface MemberJpaRepository extends JpaRepository<TempUserEntity, String> {
	
    Optional<TempUserEntity> findByUsername(String username);
    
}