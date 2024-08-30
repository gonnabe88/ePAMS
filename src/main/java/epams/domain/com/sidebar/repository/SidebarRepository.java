package epams.domain.com.sidebar.repository;

import epams.domain.com.index.dto.DeptSearchDTO;
import epams.domain.com.index.dto.TeamSearchDTO;
import epams.domain.com.member.dto.IamUserDTO;
import epams.domain.com.member.dto.RoleDTO;
import epams.domain.com.sidebar.dto.UserInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
     * @implNote 특정 사용자 조회
     * @since 2024-06-09
     */
    public UserInfoDTO findByUserNo(final String userNo) {
        return sql.selectOne("UserInfo.findByUserNo", userNo);
    }

}