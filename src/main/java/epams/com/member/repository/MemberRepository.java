package epams.com.member.repository;

import java.util.List;

import epams.com.admin.dto.CodeDTO;
import epams.com.member.dto.RoleDTO;
import epams.com.member.entity.RoleEntity;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import epams.com.member.dto.IamUserDTO;
import epams.com.member.entity.IamUserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
    public List<IamUserDTO> findBySearchValue(String searchValue) {
        final List<IamUserEntity> iamUserEntities = sql.selectList("IamUser.findBySearchValue", searchValue);
        RoleDTO member = new RoleDTO();
        log.warn("findBySearchValue : {}", IamUserDTO.toDTOs(iamUserEntities));
        return IamUserDTO.toDTOs(iamUserEntities);
    }

    /***
     * @author 140024
     * @implNote 신규 데이터 입력
     * @since 2024-06-09
     */
    public void insert(final IamUserDTO iamUserDTO) {
        sql.insert("IamUser.insert", iamUserDTO.toEntity());
    }

    public RoleDTO findOneRoleByUsername(final IamUserDTO iamUserDTO) {
        RoleEntity roleEntity = new RoleEntity();
        try {
            roleEntity = sql.selectOne("Role.findOneRoleByUsername", iamUserDTO.toEntity());
        } catch (Exception e) {
            roleEntity.setROLE_ID("ROLE_NORMAL");
            roleEntity.setENO(iamUserDTO.getUsername());
        }
        return RoleDTO.toDTO(roleEntity);
    }

}
