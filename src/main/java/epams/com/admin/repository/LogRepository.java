package epams.com.admin.repository;

import epams.com.admin.dto.LogLoginDTO;
import epams.com.admin.entity.LogLoginEntity;
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

    /***
     * @author 140024
     * @implNote Login Log 조회(전체 로그)
     * @since 2024-06-09
     */
    public List<LogLoginDTO> findAll() {
    	final List<LogLoginEntity> logLoginEntities = sql.selectList("LogLogin.findAll");
    	return LogLoginDTO.toDTOList(logLoginEntities);
    }
    
    /***
     * @author 140024
     * @implNote Login Log 저장 
     * @since 2024-06-09
     * @since 2024-06-09 PMD MethodArgumentCouldBeFinal 취약점 조치 (140024) 
     */
    public void insert(final LogLoginDTO logLoginDTO) {
    	sql.update("LogLogin.insert", logLoginDTO.toEntity());
    }
}
