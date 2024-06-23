package epams.com.login.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import epams.com.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;

/**
 * @author K140024
 * @implNote 로그인 관련 데이터베이스 작업을 처리하는 리포지토리 클래스
 * @since 2024-06-11
 */
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
    public MemberDTO login(final MemberDTO memberDTO) {
        return sql.selectOne("Member.login", memberDTO);
    }

    /**
     * @author K140024
     * @implNote 사용자 ID로 사용자 정보를 찾는 메서드
     * @since 2024-06-11
     */
    public MemberDTO findByUserId(final String userId) {
        return sql.selectOne("Member.findByUserId", userId);
    }

    /**
     * @author K140024
     * @implNote 인증 정보를 확인하고 사용자 정보를 반환하는 메서드
     * @since 2024-06-11
     */
    public MemberDTO auth(final MemberDTO memberDTO) {
        return sql.selectOne("Member.auth", memberDTO);
    }

    /**
     * @author K140024
     * @implNote 사용자 이름으로 UUID를 찾는 메서드
     * @since 2024-06-11
     */
    public String findUuid(final String username) {
        return sql.selectOne("Member.findUuid", username);
    }

    /**
     * @author K140024
     * @implNote UUID를 업데이트하는 메서드
     * @since 2024-06-11
     */
    public void updateUuid(final MemberDTO memberDTO) {
        sql.update("Member.updateUuid", memberDTO);
    }
}
