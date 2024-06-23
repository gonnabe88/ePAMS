package epams.com.login.util.webauthn.user;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.yubico.webauthn.data.ByteArray;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class WebauthUserRepository {

    /***
     * @author K140024
     * @implNote MyBatis DBMS 연결을 위한 템플릿 클래스
     * @since 2024-06-10
     */
    private final SqlSessionTemplate sql;

    /***
     * @author K140024
     * @implNote paging 조건에 따라 모든 게시물을 조회하여 반환
     * @since 2024-06-09
     */
    public List<WebauthUserDTO> findAll() {
        final List<WebauthUserEntity> webauthUserEntityList = sql.selectList("WebauthUser.findAll");
        return WebauthUserDTO.toDTOList(webauthUserEntityList);
    }

    /***
     * @author K140024
     * @implNote 기본키 검색
     * @since 2024-06-09
     */
    public Long countById(final WebauthUserDTO webauthUserDTO) {
        return sql.selectOne("WebauthUser.countById", webauthUserDTO.toEntity());
    }

    /***
     * @author K140024
     * @implNote 신규 데이터 입력
     * @since 2024-06-09
     */
    public void insert(final WebauthUserDTO webauthUserDTO) {
        sql.insert("WebauthUser.insert", webauthUserDTO.toEntity());
    }

    /***
     * @author K140024
     * @implNote 기존 데이터 삭제
     * @since 2024-06-09
     */
    public void delete(final WebauthUserDTO webauthUserDTO) {
        sql.delete("WebauthUser.delete", webauthUserDTO.toEntity());
    }

    /***
     * @author K140024
     * @implNote 데이터 업데이트(기본키 제외)
     * @since 2024-06-09
     */
    public void update(final WebauthUserDTO webauthUserDTO) {
        sql.update("WebauthUser.update", webauthUserDTO.toEntity());
    }

    /***
     * @author K140024
     * @implNote 신규 데이터 입력
     * @since 2024-06-09
     */
    public void insertUpdate(final WebauthUserDTO webauthUserDTO) {
        sql.insert("WebauthUser.insertUpdate", webauthUserDTO.toEntity());
    }

    /***
     * @author K140024
     * @implNote 사용자 이름으로 데이터 조회
     * @since 2024-06-09
     */
    public WebauthUserDTO findByUsername(final String username) {
        log.warn("findByUsername : " + username);
        final WebauthUserEntity entity = sql.selectOne("WebauthUser.findByUsername", username);
        if (entity == null) {
            // Null인 경우에 대한 적절한 처리를 추가
            return null; // 또는 적절한 기본값 반환
        }
        final WebauthUserDTO webauthUserDTO = WebauthUserDTO.toDTO(entity);
        return webauthUserDTO;
    }

    /***
     * @author K140024
     * @implNote 사용자 이름으로 데이터 개수 조회
     * @since 2024-06-09
     */
    public int countByUsername(final String username) {
        return sql.selectOne("WebauthUser.countByUsername", username);
    }

    /***
     * @author K140024
     * @implNote 사용자 핸들로 데이터 조회
     * @since 2024-06-09
     */
    public WebauthUserDTO findByHandle(final ByteArray handle) {
        final WebauthUserDTO webauthUserDTO = WebauthUserDTO.toDTO(sql.selectOne("WebauthUser.findByHandle", handle));
        return webauthUserDTO;
    }

}
