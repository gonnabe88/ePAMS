package epams.domain.dtm.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import epams.domain.dtm.dto.DtmApplStatusDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/***
 * @author 140024
 * @implNote 연차저축 관련 데이터를 가져오는 레파지토리
 * @since 2024-09-13
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class DtmApplStatusRepository {
	/***
	 * @author 140024
	 * @implNote SQL 쿼리 실행은 위한 SqlSessionTemplate
	 * @since 2024-06-09
	 */
    private final SqlSessionTemplate sql;
    
    /***
     * @author 140024
     * @implNote dtm010_03_03_p_08 (업무기준관리 연차저축기간 및 비율조회)
     * @since 2024-09-12
     * @param DtmSaveDTO 연차저축 DTO
     */
    public void getApplStatus(final DtmApplStatusDTO dto) {
    	final DtmApplStatusDTO res = sql.selectOne("DtmApplStatus.getDtmApplStatus", dto);
        if(res != null) {
            dto.setNextAnnualHourRemainCnt(res.getNextAnnualHourRemainCnt()); //dtm_save_yn
            dto.setNextAnnualDayRemainCnt(res.getNextAnnualDayRemainCnt()); //dtm_save_rate
        }
    }

}
