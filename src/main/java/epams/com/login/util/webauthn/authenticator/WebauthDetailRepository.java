package epams.com.login.util.webauthn.authenticator;

import java.util.List;
import java.util.Optional;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.yubico.webauthn.data.ByteArray;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
/***
 * @author 140024
 * @implNote 간편인증 레파지토리
 * @since 2024-06-22
 */
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
    	final List<WebauthDetailEntity> webauthEntities = sql.selectList("WebauthDetail.findAll");
        return WebauthDetailDTO.toDTOList(webauthEntities);
    }
    
	/***
	 * @author 140024
	 * @implNote 기본키 검색
	 * @since 2024-06-09
	 */
    public Long countById(final WebauthDetailDTO webauthDetailDTO) {
        return sql.selectOne("WebauthDetail.countById", webauthDetailDTO.toEntity());
    }
    
	/***
	 * @author 140024
	 * @implNote 신규 데이터 입력
	 * @since 2024-06-09
	 */
    public void insert(final WebauthDetailDTO webauthDetailDTO) {
        sql.insert("WebauthDetail.insert", webauthDetailDTO.toEntity());
    }
    
	/***
	 * @author 140024
	 * @implNote 기존 데이터 삭제
	 * @since 2024-06-09
	 */
    public void delete(final WebauthDetailDTO webauthDetailDTO) {
        sql.delete("WebauthDetail.delete", webauthDetailDTO.toEntity());
    }
    
	/***
	 * @author 140024
	 * @implNote 데이터 업데이트(기본키 제외)
	 * @since 2024-06-09
	 */
    public void update(final WebauthDetailDTO webauthDetailDTO) {
        sql.update("WebauthDetail.update", webauthDetailDTO.toEntity());
    }
    
	/***
	 * @author 140024
	 * @implNote 크리덴셜ID로 찾기
	 * @since 2024-06-09
	 */
    public Optional<WebauthDetailDTO> findByCredentialId(final ByteArray credentialId) {
        final WebauthDetailEntity webauthEntities = sql.selectOne("WebauthDetail.findByCredentialId", credentialId);
        final WebauthDetailDTO webauthDetailDTO = WebauthDetailDTO.toDTO(webauthEntities);
        Optional<WebauthDetailDTO> result = Optional.empty();
        if (webauthDetailDTO != null) {
            result = Optional.of(webauthDetailDTO);
        } 
        return result;
    }
    
	/***
	 * @author 140024
	 * @implNote 자격증명 기준 모든 정보 조회
	 * @since 2024-06-09
	 */
    public List<WebauthDetailDTO> findAllByCredentialId(final ByteArray credentialId) {
    	return sql.selectList("WebauthDetail.findAllByCredentialId", credentialId);
    }
    
	/***
	 * @author 140024
	 * @implNote 사용자 기준 자격증명 정보 조회
	 * @since 2024-06-09
	 */
    public Optional<WebauthDetailDTO> findByUser(final String username) {
    	final WebauthDetailDTO webauthDetailDTO = WebauthDetailDTO.toDTO(sql.selectOne("WebauthDetail.findByUser", username));
    	Optional<WebauthDetailDTO> result = Optional.empty();
    	if (webauthDetailDTO != null) {
    		result = Optional.of(webauthDetailDTO);
        }
        return result;
    }
    
    /**
     * @author 
     * @implNote 사용자 기준 모든 자격증명 정보 조회
     * @since 2024-06-09
     */
    public List<WebauthDetailDTO> findAllByUser(final String username) {
        final List<WebauthDetailEntity> webauthEntities = sql.selectList("WebauthDetail.findAllByUser", username);
        return WebauthDetailDTO.toDTOList(webauthEntities);
    }
    
    /**
     * @author 
     * @implNote 사용자 기준 자격증명 수 조회
     * @since 2024-06-09
     */
    public int countByUser(final String username) {
       return sql.selectOne("WebauthDetail.countByUser", username);
    }
    
    
    
}
