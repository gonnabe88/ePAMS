package epams.domain.dtm.repository;

import epams.domain.com.admin.dto.LogLoginDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

/***
 * @author 140024
 * @implNote repository
 * @since 2024-06-09
 */
@Repository
@RequiredArgsConstructor
public class DtmRepository {
	/***
	 * @author 140024
	 * @implNote SQL 쿼리 실행은 위한 SqlSessionTemplate
	 * @since 2024-06-09
	 */
    private final SqlSessionTemplate sql;

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
