package epams.domain.dtm.repository;

import epams.domain.dtm.dto.DtmAnnualStatusDTO;
import epams.domain.dtm.dto.DtmSqlParamDTO;
import epams.domain.dtm.dto.DtmApplElaCheckProcDTO;
import epams.domain.dtm.dto.DtmHisDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/***
 * @author 140024
 * @implNote repository
 * @since 2024-06-09
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class DtmAnnualStatusRepository {
	/***
	 * @author 140024
	 * @implNote SQL 쿼리 실행은 위한 SqlSessionTemplate
	 * @since 2024-06-09
	 */
    private final SqlSessionTemplate sql;

    
    /***
     * @author 140024
     * @implNote 년간근태현황 조회
     * @since 2024-09-09
     */
    public DtmAnnualStatusDTO getDtmAnnualStatus(final DtmSqlParamDTO dto) {
    	return sql.selectOne("DtmAnnualStatus.findDtmAnnualStatus", dto);
    }
}
