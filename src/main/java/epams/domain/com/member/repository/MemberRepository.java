package epams.domain.com.member.repository;

import java.util.List;

import epams.domain.com.index.dto.DeptSearchDTO;
import epams.domain.com.index.dto.TeamSearchDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import epams.domain.com.member.dto.IamUserDTO;
import epams.domain.com.member.dto.RoleDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * *
 * @author 140024
 * @implNote 사용자 레포지터리
 * @since 2024-06-10
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberRepository {

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
     * @implNote 모든 내용 조회
     * @since 2024-06-09
     */
    public List<IamUserDTO> findAll() {
        return sql.selectList("IamUser.findAll");
    }

    /**
     * *
     * @author 140024
     * @implNote 모든 부서/팀 조회
     * @since 2024-06-09
     */
    public List<DeptSearchDTO> findAllDept() {
        return sql.selectList("IamOrg.findAllDept");
    }

    /**
     * *
     * @author 140024
     * @implNote 모든 부서/팀 조회
     * @since 2024-06-09
     */
    public List<TeamSearchDTO> findAllTeam() {
        return sql.selectList("IamUser.findAllTeam");
    }

    /**
     * *
     * @author 140024
     * @implNote 특정 키워드 통합 조회
     * @since 2024-06-09
     */
    public List<IamUserDTO> findBySearchValue(final String searchValue) {
        return sql.selectList("IamUser.findBySearchValue", searchValue);
    }

    /**
     * *
     * @author 140024
     * @implNote 특정 사용자 조회
     * @since 2024-06-09
     */
    public IamUserDTO findByUsername(final IamUserDTO iamUserDTO) {
        return sql.selectOne("IamUser.findByUsername", iamUserDTO);
    }
    
    /***
     * @author 140024
     * @implNote 신규 데이터 입력
     * @since 2024-06-09
     */
    public void insert(final IamUserDTO iamUserDTO) {
        sql.insert("IamUser.insert", iamUserDTO);
    }

    /**
     * @author 140024
     * @implNote 사용자의 역할 조회
     * @since 2024-06-10
     */
    public RoleDTO findOneRoleByUsername(final IamUserDTO iamUserDTO) {
        if (log.isWarnEnabled()) {
            log.warn("{} 사용자의 역할을 조회합니다.", iamUserDTO.getUsername());
        }
        
        RoleDTO roleDTO = sql.selectOne("Role.findOneRoleByUsername", iamUserDTO);
        
        if (roleDTO == null) {
            if (log.isWarnEnabled()) {
                log.warn("{} 사용자의 역할이 존재하지 않습니다. 기본 역할을 부여합니다.", iamUserDTO.getUsername());
            }
            roleDTO = new RoleDTO();
            roleDTO.setRoleId("ROLE_NORMAL");
            roleDTO.setUsername(iamUserDTO.getUsername());
        } else {
            if (log.isWarnEnabled()) {
                log.warn("{} 사용자의 역할은 {}입니다.", roleDTO.getUsername(), roleDTO.getRoleId());
            }
        }
        
        return roleDTO;
    }

}
