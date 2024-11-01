package epams.domain.com.login.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import epams.domain.com.member.dto.IamUserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author K140024
 * @implNote 로그인 관련 데이터베이스 작업을 처리하는 리포지토리 클래스
 * @since 2024-06-11
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class LoginRepository {

    /**
     * @author K140024
     * @implNote MyBatis SqlSessionTemplate 주입
     * @since 2024-06-11
     */
    private final SqlSessionTemplate sql;

    
    /**
     * @author K140024
     * @implNote 로그인 정보를 확인하고 사용자 정보를 반환하는 메서드
     * @since 2024-06-11
     */
    public IamUserDTO pwLogin(final IamUserDTO iamUserDTO) {
        if(log.isWarnEnabled()){
            log.warn("{} {} 로그인 정보 확인", iamUserDTO.getUsername(), iamUserDTO.getPassword());
        }
        
        IamUserDTO userYn = sql.selectOne("IamUser.findByUserId", iamUserDTO);
        if(userYn == null) {
            log.warn("{} 로그인 계정 또는 권한 없음", iamUserDTO.getUsername());
        }

        return sql.selectOne("IamUser.login", iamUserDTO);
    }

    /**
     * @author K140024
     * @implNote 사용자 ID로 사용자 정보를 찾는 메서드
     * @since 2024-06-11
     */
    public IamUserDTO findByUserId(final IamUserDTO iamUserDTO) {
        return sql.selectOne("IamUser.findByUserId", iamUserDTO);
    }

}
