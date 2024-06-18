package epams.com.login.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import epams.com.login.dto.LoginOTPDTO;
import epams.com.login.entity.LoginOTPEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/***
 * @author 140024
 * @implNote Login OTP 정보를 DBMS와 주고받기 위한 레파지토리 
 * @since 2024-06-14
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class LoginOTPRepository {
	
	/***
	 * @author 140024
	 * @implNote MyBatis DBMS 연결을 위한 템플릿 클래스
	 * @since 2024-06-10
	 */
    private final SqlSessionTemplate sql;

    
	/***
	 * @author 140024
	 * @implNote paging 조건에 따라 모든 게시물을 조회하여 반환
	 * @since 2024-06-09
	 */
    public List<LoginOTPDTO> findAll() {
    	final List<LoginOTPEntity> codeEntities = sql.selectList("LoginOTP.findAll");
        return LoginOTPDTO.toDTOList(codeEntities);
    }
    
	/***
	 * @author 140024
	 * @implNote 기본키 검색
	 * @since 2024-06-09
	 */
    public Long countById(LoginOTPDTO loginOTP) {
        return sql.selectOne("LoginOTP.countById", LoginOTPDTO.toEntity(loginOTP));
    }
    
	/***
	 * @author 140024
	 * @implNote 신규 데이터 입력
	 * @since 2024-06-09
	 */
    public void insert(LoginOTPDTO loginOTP) {
        sql.insert("LoginOTP.insert", LoginOTPDTO.toEntity(loginOTP));
    }
    
	/***
	 * @author 140024
	 * @implNote 기존 데이터 삭제
	 * @since 2024-06-09
	 */
    public void delete(LoginOTPDTO loginOTP) {
        sql.delete("LoginOTP.delete", LoginOTPDTO.toEntity(loginOTP));
    }
    
	/***
	 * @author 140024
	 * @implNote 데이터 업데이트(기본키 제외)
	 * @since 2024-06-09
	 */
    public void update(LoginOTPDTO loginOTP) {
        sql.update("LoginOTP.update", LoginOTPDTO.toEntity(loginOTP));
    }
    
	/***
	 * @author 140024
	 * @implNote 데이터 업데이트(기본키 제외)
	 * @since 2024-06-09
	 */
    public LoginOTPDTO findValidOneByUsername(LoginOTPDTO loginOTP) {
    	
    	LoginOTPEntity loginOTPEntity = sql.selectOne("LoginOTP.findValidOneByUsername", LoginOTPDTO.toEntity(loginOTP));
    	return LoginOTPDTO.toDTO(loginOTPEntity);
    }

}