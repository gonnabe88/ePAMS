package epams.domain.com.member.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
     * @implNote 특정 사용자 정보 조회
     * @since 2024-09-05
     */
    public IamUserDTO findUserByUserNo(String username) {
        return sql.selectOne("IamUser.findUserByUserNo", username);
    }

    /**
     * *
     * @author 140024
     * @implNote 직원 휴대폰 번호 조회
     * @since 2024-09-05
     */
    public String findPhoneNo(String username) {
        return sql.selectOne("IamUser.findPhoneNo", username);
    }

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
        return sql.selectList("IamUser.findAllMember");
    }

    /**
     * *
     * @author 140024
     * @implNote 특정 키워드 통합 조회
     * @since 2024-06-09
     */
    public List<IamUserDTO> findBySearchValue(final String searchValue) {
        return sql.selectList("IamUser.findByUserNo", searchValue);
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
    public List<RoleDTO> findOneRoleByUsername(final IamUserDTO iamUserDTO) {
        if (log.isWarnEnabled()) {
            log.warn("{} 사용자의 역할을 조회합니다.", iamUserDTO.getUsername());
        }
        
        List<RoleDTO> roleDTOs = sql.selectList("Role.findRoleByUsername", iamUserDTO);
        
        if (roleDTOs == null || roleDTOs.isEmpty()) {
            if (log.isWarnEnabled()) {
                log.warn("{} 사용자의 역할이 존재하지 않습니다. 기본 역할을 부여합니다.", iamUserDTO.getUsername());
            }
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setRoleId("HURXE001AA");
            roleDTO.setUsername(iamUserDTO.getUsername());
            roleDTOs = new ArrayList<>();
            roleDTOs.add(roleDTO);

        } else {
            if (log.isWarnEnabled()) {
                log.warn("{} 사용자의 역할은 {}입니다.", iamUserDTO.getUsername(), roleDTOs.stream()
                        .map(RoleDTO::getRoleId).collect(Collectors.joining(", ")));
            }
        }
        
        return roleDTOs;
    }

}
