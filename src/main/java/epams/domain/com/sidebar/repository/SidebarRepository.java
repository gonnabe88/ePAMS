package epams.domain.com.sidebar.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import epams.domain.com.sidebar.dto.UserInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author K140024
 * @implNote 사이드바 기능 구현을 위한 레파지토리
 * @since 2024-04-26
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class SidebarRepository {

    /**
     * *
     * @author 140024
     * @implNote MyBatis DBMS 연결을 위한 템플릿 클래스
     * @since 2024-06-10
     */
    private final SqlSessionTemplate sql;

    /**
     * *
     * @author 140024
     * @implNote 사이드바에 보여줄 사용자 기본정보/근무시간(오늘/내일) 조회
     * @since 2024-06-09
     */
    public UserInfoDTO findByUserNo(final String username) {
        return sql.selectOne("UserInfo.findByUserNo", username);
    }

}
