package epams.com.member.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import epams.com.member.dto.IamUserDTO;
import epams.com.member.dto.RoleDTO;
import epams.com.member.entity.IamUserEntity;
import epams.com.member.entity.RoleEntity;
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
        final List<IamUserEntity> iamUserEntities = sql.selectList("IamUser.findAll");
        return IamUserDTO.toDTOs(iamUserEntities);
    }

    /**
     * *
     * @author 140024
     * @implNote 특정 키워드 통합 조회
     * @since 2024-06-09
     */
    public List<IamUserDTO> findBySearchValue(final String searchValue) {
        final List<IamUserEntity> iamUserEntities = sql.selectList("IamUser.findBySearchValue", searchValue);
        return IamUserDTO.toDTOs(iamUserEntities);
    }
    
    

    /**
     * *
     * @author 140024
     * @implNote 특정 사용자 조회
     * @since 2024-06-09
     */
    public IamUserDTO findByUsername(final IamUserDTO iamUserDTO) {
        final IamUserEntity iamUserEntity = sql.selectOne("IamUser.findByUsername", iamUserDTO.toEntity());
        return IamUserDTO.toDTO(iamUserEntity);
    }
    
    /***
     * @author 140024
     * @implNote 신규 데이터 입력
     * @since 2024-06-09
     */
    public void insert(final IamUserDTO iamUserDTO) {
        sql.insert("IamUser.insert", iamUserDTO.toEntity());
    }

    /**
     * *
     * @author 140024
     * @implNote 사용자의 역할 조회
     * @since 2024-06-10
     */
    public RoleDTO findOneRoleByUsername(final IamUserDTO iamUserDTO) {
        RoleEntity roleEntity = new RoleEntity();
        try {
            roleEntity = sql.selectOne("Role.findOneRoleByUsername", iamUserDTO.toEntity());
        } catch (RuntimeException npe) {
            roleEntity.setROLE_ID("ROLE_NORMAL");
            roleEntity.setENO(iamUserDTO.getUsername());
        } 
        return RoleDTO.toDTO(roleEntity);
    }

}
