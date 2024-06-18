package epams.com.admin.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import epams.com.admin.dto.LogViewDTO;
import epams.com.admin.entity.LogViewEntity;
import lombok.RequiredArgsConstructor;

/***
 * @author 140024
 * @implNote 각종 로그 데이터 관리를 위한 repository
 * @since 2024-06-09
 */
@Repository
@RequiredArgsConstructor
public class ViewLogRepository {
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
    public List<LogViewDTO> findAll() {
    	final List<LogViewEntity> controllerLogEntity = sql.selectList("ViewLog.findAll");
    	return LogViewDTO.toDTOList(controllerLogEntity);
    }
    
    /***
     * @author 140024
     * @implNote Login Log 저장 
     * @since 2024-06-09
     * @since 2024-06-09 PMD MethodArgumentCouldBeFinal 취약점 조치 (140024) 
     */
    public void insert(final LogViewDTO controllerLogDTO) {
    	sql.update("ViewLog.insert", LogViewDTO.toEntity(controllerLogDTO));
    }
    
}