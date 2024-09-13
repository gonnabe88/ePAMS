package epams.domain.dtm.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import epams.domain.dtm.dto.DtmCheckDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/***
 * @author 140024
 * @implNote 근태 신청/취소 체크에 필요한 관련 데이터를 가져오는 레파지토리
 * @since 2024-09-13
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class DtmCheckRepository {
	/***
	 * @author 140024
	 * @implNote SQL 쿼리 실행은 위한 SqlSessionTemplate
	 * @since 2024-06-09
	 */
    private final SqlSessionTemplate sql;
    
    /***
     * @author 140024
     * @implNote dtm010_03_03_p_06 (근태별 신청일수 조회)
     * @since 2024-09-12
     * @param DtmSaveDTO 연차저축 DTO
     */
    public void getNumberOfDay(final DtmCheckDTO dto) {
    	final DtmCheckDTO res = sql.selectOne("DtmGetNumberOfDay.getDay", dto);
        if(res != null) {
            dto.setDayCount(res.getDayCount());
        }
    }

    /***
     * @author 140024
     * @implNote dtm010_03_03_p_10 (근태별 신청시간 조회)
     * @since 2024-09-12
     * @param DtmSaveDTO 연차저축 DTO
     */
    public void getNumberOfHour(final DtmCheckDTO dto) {
    	final DtmCheckDTO res = sql.selectOne("DtmGetNumberOfHour.getHour", dto);
        if(res != null) {
            dto.setHourCount(res.getHourCount());
        }
    }
}
