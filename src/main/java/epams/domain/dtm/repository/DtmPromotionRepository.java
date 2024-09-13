package epams.domain.dtm.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import epams.domain.dtm.dto.DtmPromotionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/***
 * @author 140024
 * @implNote 연차 촉진관련 데이터를 가져오는 레파지토리
 * @since 2024-09-13
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class DtmPromotionRepository {
	/***
	 * @author 140024
	 * @implNote SQL 쿼리 실행은 위한 SqlSessionTemplate
	 * @since 2024-06-09
	 */
    private final SqlSessionTemplate sql;
    
    /***
     * @author 140024
     * @implNote dtm010_03_03_p_07 (업무기준관리 연차촉진기간 조회)
     * @since 2024-09-12
     * @param DtmPromotionDTO 연차촉진 DTO
     */
    public void getDtmPromotionYn(final DtmPromotionDTO dto) {
    	final DtmPromotionDTO res = sql.selectOne("DtmGetPromotionYn.getDtmPromotionYn", dto);
        if(res != null) {
            dto.setPromotionYn(res.getPromotionYn()); //promotion_yn
        }
    }

    /***
     * @author 140024
     * @implNote dtm010_23_03_p_01 (연차사용촉진관리 대상자 여부 조회)
     * @since 2024-09-12
     * @param DtmPromotionDTO 연차촉진 DTO
     */
    public void getDtmPromotionExp(final DtmPromotionDTO dto) {
    	final DtmPromotionDTO res = sql.selectOne("DtmGetPromotionExp.getDtmPromotionExp", dto);
        if(res != null) {
            dto.setPromotionExp(res.getPromotionExp()); //exception_yn
        }
    }
    
    /***
     * @author 140024
     * @implNote dtm010_23_03_p_04 (연차촉진 조회)
     * @since 2024-09-12
     * @param DtmPromotionDTO 연차촉진 DTO
     */
    public void getPromotionDetail(final DtmPromotionDTO dto) {
    	final DtmPromotionDTO res = sql.selectOne("DtmGetPromotionDetail.getPromotionDetail", dto);
        if(res != null) {
            dto.setFixedDutyAnnualDayTotalCnt(res.getFixedDutyAnnualDayTotalCnt()); //remain_cnt
            dto.setAnnualDayUsedCnt(res.getAnnualDayUsedCnt()); //tot_used
            dto.setDutyAnnualDayTotalCnt(res.getDutyAnnualDayTotalCnt()); //annual_cnt
            dto.setIngRevokeDayCnt(res.getIngRevokeDayCnt()); //del_using
            dto.setFixedDutyAnnualHourTotalCnt(res.getFixedDutyAnnualHourTotalCnt()); //remain_hhcnt
            dto.setAnnualHourUsedCnt(res.getAnnualHourUsedCnt()); //tot_used_hh
            dto.setDutyAnnualHourTotalCnt(res.getDutyAnnualHourTotalCnt()); //annual_hhcnt
            dto.setIngRevokeHourCnt(res.getIngRevokeHourCnt()); //del_using_hh
        }
    }

}
