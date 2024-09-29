package epams.domain.dtm.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import epams.domain.dtm.dto.DtmSaveDTO;
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
public class DtmSaveRepository {
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
     * @param dto 연차저축 DTO
     */
    public void getSaveStandard(final DtmSaveDTO dto) {
    	final DtmSaveDTO res = sql.selectOne("DtmGetSaveStandard.getSaveStandard", dto);
        if(res != null) {
            dto.setSavableYn(res.getSavableYn()); //dtm_save_yn
            dto.setSavableRate(res.getSavableRate()); //dtm_save_rate
        }
    }

    /***
     * @author 140024
     * @implNote dtm010_03_03_p_09 (연차저축가능일수 조회)
     * @since 2024-09-12
     * @param dto 연차저축 DTO
     */
    public void getDtmGetSavableDay(final DtmSaveDTO dto) {
    	final DtmSaveDTO res = sql.selectOne("DtmGetSavableDay.getDtmGetSavableDay", dto);
        if(res != null) {
            dto.setSaveDayCnt(res.getSaveDayCnt()); //dtm_save_cnt
            dto.setSaveHourCnt(res.getSaveHourCnt()); //dtm_save_hhcnt
            dto.setSaveYn(res.getSaveYn()); //store_yn
        }
    }
}
