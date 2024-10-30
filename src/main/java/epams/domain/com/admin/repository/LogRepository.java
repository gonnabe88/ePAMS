package epams.domain.com.admin.repository;

import epams.domain.com.admin.dto.LogLoginDTO;
import epams.domain.com.member.dto.IamUserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/***
 * @author 140024
 * @implNote 각종 로그 데이터 관리를 위한 repository
 * @since 2024-06-09
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class LogRepository {
	/***
	 * @author 140024
	 * @implNote SQL 쿼리 실행은 위한 SqlSessionTemplate
	 * @since 2024-06-09
	 */
    private final SqlSessionTemplate sql;
    
    /**
     * @author K140024
     * @implNote 로그인 정보를 확인하고 사용자 정보를 반환하는 메서드 
     * @since 2024-06-11
     */
    public LogLoginDTO checkFailCnt(final String username) {        
        return sql.selectOne("LogLogin.checkFailCnt", username);
    }
    
    /***
     * @author 140024
     * @implNote Login Log 조회(전체 로그)
     * @since 2024-06-09
     */
    public List<LogLoginDTO> findAll() {
    	return sql.selectList("LogLogin.findAll");
    }
    
    /***
     * @author 140024
     * @implNote Login Log 저장 
     * @since 2024-06-09
     * @since 2024-06-09 PMD MethodArgumentCouldBeFinal 취약점 조치 (140024) 
     */
    public void insert(final LogLoginDTO logLoginDTO) {
		sql.update("LogLogin.insert", logLoginDTO);
    }
}
