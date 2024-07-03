package epams.com.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import epams.com.member.entity.TempUserEntity;

/**
 * @author K140024
 * @implNote TempUserEntity에 대한 JPA 리포지토리 인터페이스
 * @since 2024-06-11
 */
@Repository
public interface MemberJpaRepository extends JpaRepository<TempUserEntity, String> {
    
    /**
     * 주어진 사용자 이름으로 TempUserEntity를 조회합니다.
     * 
     * @param username 사용자 이름
     * @return Optional<TempUserEntity> 주어진 사용자 이름으로 조회된 TempUserEntity
     * @since 2024-06-11
     */
    Optional<TempUserEntity> findByUsername(String username);
}