package epams.domain.dtm.repository;

import epams.domain.dtm.dto.DtmApplElaCheckProcDTO;
import epams.domain.dtm.dto.DtmHisDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/***
 * @author 140024
 * @implNote repository
 * @since 2024-06-09
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class DtmRevokeRepository {
	/***
	 * @author 140024
	 * @implNote SQL 쿼리 실행은 위한 SqlSessionTemplate
	 * @since 2024-06-09
	 */
    private final SqlSessionTemplate sql;

    
    /***
     * @author 140024
     * @implNote 근태 취소
     * @since 2024-09-09
     */
    public String callRevokeCheckProc(final DtmApplElaCheckProcDTO dto) {
    	return sql.selectOne("DtmApplProc.revokeCheck", dto);
    }
}
