package epams.domain.dtm.repository;

import epams.domain.dtm.dto.DtmHisDTO;
import epams.domain.dtm.dto.DtmSearchDTO;
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
public class DtmHistoryRepository {
	/***
	 * @author 140024
	 * @implNote SQL 쿼리 실행은 위한 SqlSessionTemplate
	 * @since 2024-06-09
	 */
    private final SqlSessionTemplate sql;

    /***
     * @author 140024
     * @implNote 조건(ID, 기간 등)에 맞는 모든 근태 조회
     * @since 2024-06-09
     */
    public List<DtmHisDTO> findByCondition(final DtmSearchDTO searchDTO) {
        return sql.selectList("DtmHis.findByCondition", searchDTO);
    }

    /***
     * @author 140024
     * @implNote 조건(ID, 기간 등)에 맞는 모든 근태 수
     * @since 2024-06-09
     */
    public long countByCondition(final DtmSearchDTO searchDTO) {
        return sql.selectOne("DtmHis.countByCondition", searchDTO);
    }

     /***
     * @author 140024
     * @implNote 해당 직원의 모든 근태 수
     * @since 2024-06-09
     */
    public long countById(final DtmHisDTO dto) {
    	return sql.selectOne("DtmHis.countById", dto);
    }
    
    /***
     * @author 140024
     * @implNote 근태 입력
     * @since 2024-06-09
     * @since 2024-06-09 PMD MethodArgumentCouldBeFinal 취약점 조치 (140024) 
     */
    public void insert(final DtmHisDTO dto) {
    	sql.insert("DtmHis.insert", dto);
    }

    /***
     * @author 140024
     * @implNote 근태 승인 업데이트
     * @since 2024-06-09
     * @since 2024-06-09 PMD MethodArgumentCouldBeFinal 취약점 조치 (140024)
     */
    public void updateByApplId(final DtmHisDTO dto) {
        sql.update("DtmHis.updateByApplId", dto);
    }

}
