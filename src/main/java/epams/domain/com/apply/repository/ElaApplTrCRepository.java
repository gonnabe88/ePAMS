package epams.domain.com.apply.repository;

import epams.domain.com.apply.dto.ElaApplCDTO;
import epams.domain.com.apply.dto.ElaApplTrCDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

/***
 * @author 140024
 * @implNote repository
 * @since 2024-08-04
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class ElaApplTrCRepository {

    /***
     * @author 140024
     * @implNote SQL 쿼리 실행은 위한 SqlSessionTemplate
     * @since 2024-06-09
     */
    private final SqlSessionTemplate sql;

    /***
     * @author 140024
     * @implNote 신청서 추가
     * @since 2024-06-09
     */
    public long insert(final ElaApplTrCDTO dto) {
        return sql.insert("ElaApplTrC.insert", dto);
    }

    /***
     * @author 140024
     * @implNote 신청서 결재완료 업데이트
     * @since 2024-08-07
     */
    public long updateByApplId(final ElaApplTrCDTO dto) {
        return sql.insert("ElaApplTrC.updateByApplId", dto);
    }
}
