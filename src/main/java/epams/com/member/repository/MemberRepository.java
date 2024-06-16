package epams.com.member.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import epams.com.member.entity.MemberEntity;
import epams.com.member.entity.SearchMemberEntity;
import jakarta.transaction.Transactional;

public interface MemberRepository extends JpaRepository<MemberEntity, String> {
    Optional<MemberEntity> findByUsername(String username);
    
    @Transactional
    @Query(value = "SELECT * FROM member s WHERE s.username LIKE %:searchValue% OR s.responsibility LIKE %:searchValue% ",  nativeQuery = true)
    List<SearchMemberEntity> findBySearchValue(@Param("searchValue") String searchValue);
    
    @Transactional
    @Query(value = "SELECT * FROM member s",  nativeQuery = true)
    List<MemberEntity> findAll();
    
    
}