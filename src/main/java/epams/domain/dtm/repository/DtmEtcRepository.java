package epams.domain.dtm.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import epams.domain.dtm.dto.DtmHisDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/***
 * @author 140024
 * @implNote 근태 기타코드레파지토리
 * @since 2024-09-13
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class DtmEtcRepository {
	/***
	 * @author 140024
	 * @implNote SQL 쿼리 실행은 위한 SqlSessionTemplate
	 * @since 2024-06-09
	 */
    private final SqlSessionTemplate sql;
    
    /***
     * @author 140024
     * @implNote dtm010_03_03_p_11 (근태기타코드조회)
     * 근태 교차사용 가능여부, 근태 시작시간/종료시간
     * @since 2024-09-12
     * @param dto DtmHisDTO
     */
    public void findDtmPeriod(final DtmHisDTO dto) {
        log.warn(dto.toString());
    	final DtmHisDTO res = sql.selectOne("DtmEtc.findDtmPeriod", dto);
        if(res != null) {
            dto.setDtmCross(res.getDtmCross());
            dto.setStaHm(res.getStaHm());
            dto.setEndHm(res.getEndHm());
            dto.setBaseStaHm(res.getBaseStaHm());
            dto.setBaseEndHm(res.getBaseEndHm());
        }
    }    

}
