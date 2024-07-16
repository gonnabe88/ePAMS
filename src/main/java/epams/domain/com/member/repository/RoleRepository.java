package epams.domain.com.member.repository;

import epams.domain.com.member.dto.RoleDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * *
 *
 * @author 140024
 * @implNote 사용자 역할 레파지토리
 * @since 2024-06-10
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class RoleRepository {

    /**
     * *
     *
     * @author 140024
     * @implNote MyBatis DBMS 연결을 위한 템플릿 클래스
     * @since 2024-06-10
     */
    private final SqlSessionTemplate sql;

    /**
     * *
     *
     * @author 140024
     * @implNote 모든 내용 조회
     * @since 2024-06-09
     */
    public List<RoleDTO> findAll() {
        return sql.selectList("Role.findAll");
    }

    /***
     * @author 140024
     * @implNote 기본키 검색
     * @since 2024-06-09
     */
    public Long countById(final RoleDTO roleDTO) {
        return sql.selectOne("Role.countById", roleDTO);
    }

    /***
     * @author 140024
     * @implNote 신규 데이터 입력
     * @since 2024-06-09
     */
    public void insert(final RoleDTO roleDTO) {
        sql.insert("Role.insert", roleDTO);
    }

    /***
     * @author 140024
     * @implNote 기존 데이터 삭제
     * @since 2024-06-09
     */
    public void delete(final RoleDTO roleDTO) {
        sql.delete("Role.delete", roleDTO);
    }

    /***
     * @author 140024
     * @implNote 데이터 업데이트(기본키 제외)
     * @since 2024-06-09
     */
    public void update(final RoleDTO roleDTO) {
        sql.update("Role.update", roleDTO);
    }


}
