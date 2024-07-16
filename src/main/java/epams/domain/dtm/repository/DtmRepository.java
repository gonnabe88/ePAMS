package epams.domain.dtm.repository;

import java.util.List;

import epams.domain.dtm.dto.BasePeriodDTO;
import epams.domain.dtm.dto.DtmApplDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import epams.domain.com.admin.dto.LogLoginDTO;
import lombok.RequiredArgsConstructor;

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
     * @implNote 기간 조회
     * @since 2024-06-09
     */
    public List<DtmApplDTO> findAllByPeriod(final BasePeriodDTO period) {
    	return sql.selectList("DtmAppl.findAllByPeriod",  period);
    }

        /***
     * @author 140024
     * @implNote 총 갯수
     * @since 2024-06-09
     */
    public long countAllByPeriod(final BasePeriodDTO period) {    
    	return sql.selectOne("DtmAppl.countAllByPeriod", period);
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
