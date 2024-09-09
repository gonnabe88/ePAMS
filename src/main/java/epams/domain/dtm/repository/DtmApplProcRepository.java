package epams.domain.dtm.repository;

import epams.domain.dtm.dto.DtmApplCheckProcDTO;
import epams.domain.dtm.dto.DtmApplElaCheckProcDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

/***
 * @author 140024
 * @implNote repository
 * @since 2024-06-09
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class DtmApplProcRepository
{
    /***
     * @author 140024
     * @implNote SQL 쿼리 실행은 위한 SqlSessionTemplate
     * @since 2024-06-09
     */
    private final SqlSessionTemplate sql;

    /***
     * @author 140024
     * @implNote (전처리) 근태 신청가능 여부 확인 프로시저 호출
     * @since 2024-08-05
     */
    public String callPreCheckProc(final DtmApplCheckProcDTO dto){
        return sql.selectOne("DtmApplProc.preCheck", dto);
    }

    /***
     * @author 140024
     * @implNote (후처리) 근태 신청가능 여부 확인 프로시저 호출
     * @since 2024-08-05
     */
    public String callNewCheckProc(final DtmApplElaCheckProcDTO dto){
        return sql.selectOne("DtmApplProc.newCheck", dto);
    }

}
