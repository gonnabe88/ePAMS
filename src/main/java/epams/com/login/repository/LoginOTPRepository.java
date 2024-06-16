package epams.com.login.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import epams.com.login.entity.LoginMFAEntity;
import epams.com.member.entity.MemberEntity;

public interface LoginOTPRepository extends JpaRepository<LoginMFAEntity, Long>{
	
	Optional<LoginMFAEntity> findTop1ByUsernameOrderByIdDesc(String username);

}
