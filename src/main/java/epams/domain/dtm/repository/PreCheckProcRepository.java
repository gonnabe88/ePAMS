package epams.domain.dtm.repository;

import epams.domain.dtm.dto.PreCheckProcDTO;
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
public class PreCheckProcRepository
{
    /***
     * @author 140024
     * @implNote SQL 쿼리 실행은 위한 SqlSessionTemplate
     * @since 2024-06-09
     */
    private final SqlSessionTemplate sql;

    /***
     * @author 140024
     * @implNote 프로시저 호출
     * @since 2024-06-09
     */
    public String callPreCheckProc(final PreCheckProcDTO dto){
        return sql.selectOne("PreCheckProc.call", dto);
    }

}
