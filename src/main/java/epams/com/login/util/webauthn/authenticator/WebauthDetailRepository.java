package epams.com.login.util.webauthn.authenticator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.yubico.webauthn.data.ByteArray;

import epams.com.login.util.webauthn.user.WebauthUserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class WebauthDetailRepository {

	/***
	 * @author 140024
	 * @implNote MyBatis DBMS 연결을 위한 템플릿 클래스
	 * @since 2024-06-10
	 */
    private final SqlSessionTemplate sql;

    
	/***
	 * @author 140024
	 * @implNote paging 조건에 따라 모든 게시물을 조회하여 반환
	 * @since 2024-06-09
	 */
    public List<WebauthDetailDTO> findAll() {
    	final List<WebauthDetailEntity> webauthDetailEntityList = sql.selectList("WebauthDetail.findAll");
        return WebauthDetailDTO.toDTOList(webauthDetailEntityList);
    }
    
	/***
	 * @author 140024
	 * @implNote 기본키 검색
	 * @since 2024-06-09
	 */
    public Long countById(WebauthDetailDTO webauthDetailDTO) {
        return sql.selectOne("WebauthDetail.countById", webauthDetailDTO.toEntity());
    }
    
	/***
	 * @author 140024
	 * @implNote 신규 데이터 입력
	 * @since 2024-06-09
	 */
    public void insert(WebauthDetailDTO webauthDetailDTO) {
        sql.insert("WebauthDetail.insert", webauthDetailDTO.toEntity());
    }
    
	/***
	 * @author 140024
	 * @implNote 기존 데이터 삭제
	 * @since 2024-06-09
	 */
    public void delete(WebauthDetailDTO webauthDetailDTO) {
        sql.delete("WebauthDetail.delete", webauthDetailDTO.toEntity());
    }
    
	/***
	 * @author 140024
	 * @implNote 데이터 업데이트(기본키 제외)
	 * @since 2024-06-09
	 */
    public void update(WebauthDetailDTO webauthDetailDTO) {
        sql.update("WebauthDetail.update", webauthDetailDTO.toEntity());
    }
    
	/***
	 * @author 140024
	 * @implNote 기존 데이터 삭제
	 * @since 2024-06-09
	 */
    public Optional<WebauthDetailDTO> findByCredentialId(ByteArray credentialId) {
    	WebauthDetailEntity webauthDetailEntity = sql.selectOne("WebauthDetail.findByCredentialId", credentialId);
    	log.warn("DTO 변환 전 : " + webauthDetailEntity.toString());
    	WebauthDetailDTO webauthDetailDTO = WebauthDetailDTO.toDTO(webauthDetailEntity);
    	log.warn("DTO 변환 후 : " + webauthDetailDTO.toString());
        if (webauthDetailDTO != null) {
            return Optional.of(webauthDetailDTO);
        } else {
            return Optional.empty();
        }
    }
    
	/***
	 * @author 140024
	 * @implNote 데이터 업데이트(기본키 제외)
	 * @since 2024-06-09
	 */
    public List<WebauthDetailDTO> findAllByCredentialId(ByteArray credentialId) {
    	final List<WebauthDetailDTO> webauthDetailEntityList = sql.selectList("WebauthDetail.findAllByCredentialId", credentialId);
    	return webauthDetailEntityList;
    }
    
	/***
	 * @author 140024
	 * @implNote 기존 데이터 삭제
	 * @since 2024-06-09
	 */
    public Optional<WebauthDetailDTO> findByUser(String username) {
    	WebauthDetailDTO webauthDetailDTO = WebauthDetailDTO.toDTO(sql.selectOne("WebauthDetail.findByUser", username));
        if (webauthDetailDTO != null) {
            return Optional.of(webauthDetailDTO);
        } else {
            return Optional.empty();
        }
    }
    
    /**
     * @author 
     * @implNote 데이터 업데이트(기본키 제외)
     * @since 2024-06-09
     */
    public List<WebauthDetailDTO> findAllByUser(String username) {
        List<WebauthDetailEntity> webauthDetailEntityList = sql.selectList("WebauthDetail.findAllByUser", username);
        final List<WebauthDetailDTO> webauthDetailDTOList= WebauthDetailDTO.toDTOList(webauthDetailEntityList);
        return webauthDetailDTOList;
    }
    
    
    
}
