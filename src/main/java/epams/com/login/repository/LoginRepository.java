package epams.com.login.repository;

import epams.com.member.entity.IamUserEntity;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import epams.com.member.dto.IamUserDTO;
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
    public IamUserDTO login(final IamUserDTO iamUserDTO) {
        final IamUserEntity iamUserEntity = sql.selectOne("IamUser.login", iamUserDTO.toEntity());
        return IamUserDTO.toDTO(iamUserEntity);
    }

    /**
     * @author K140024
     * @implNote 사용자 ID로 사용자 정보를 찾는 메서드
     * @since 2024-06-11
     */
    public IamUserDTO findByUserId(final IamUserDTO iamUserDTO) {
        final IamUserEntity iamUserEntity = sql.selectOne("IamUser.findByUserId", iamUserDTO.toEntity());
        return IamUserDTO.toDTO(iamUserEntity);
    }

}
