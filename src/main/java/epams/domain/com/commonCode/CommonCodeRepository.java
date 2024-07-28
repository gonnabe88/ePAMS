package epams.domain.com.commonCode;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/***
 * @author 140024
 * @implNote Code 정보를 DBMS에서 가져오기 위한 레파지토리 
 * @since 2024-06-14
 */
@Repository
@RequiredArgsConstructor
public class CommonCodeRepository {

	/***
	 * @author 140024
	 * @implNote MyBatis DBMS 연결을 위한 템플릿 클래스
	 * @since 2024-06-10
	 */
    private final SqlSessionTemplate sql;
    
	/***
	 * @author 140024
	 * @implNote 모든 내용 조회
	 * @since 2024-06-09
	 */
    public List<CommonCodeDTO> findAll() {
        return sql.selectList("CommonCode.findAll");
    }

	/***
	 * @author 140024
	 * @implNote 코드유형에 해당하는 코드 목록 조회
	 * @since 2024-06-09
	 */
	public List<CommonCodeDTO> findByCodeKind(CommonCodeDTO dto) {
		return sql.selectList("CommonCode.findByCodeKind", dto);
	}

	/***
	 * @author 140024
	 * @implNote 코드유형 & 코드에 해당하는 코드 목록 조회
	 * @since 2024-06-09
	 */
	public List<CommonCodeDTO> findByCodeKindAndCode(CommonCodeDTO dto) {
		return sql.selectList("CommonCode.findByCodeKindAndCode", dto);
	}
    
}