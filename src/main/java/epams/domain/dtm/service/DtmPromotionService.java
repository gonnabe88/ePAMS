package epams.domain.dtm.service;

import org.springframework.stereotype.Service;

import epams.domain.dtm.dto.DtmPromotionDTO;
import epams.domain.dtm.repository.DtmPromotionRepository;
import epams.domain.dtm.repository.DtmSaveRepository;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/***
 * @author 140024
 * @implNote 연차촉진 관련 체크 Service
 * @since 2024-06-09
 */
@Slf4j
@ToString
@RequiredArgsConstructor
@Service
public class DtmPromotionService {

	/***
	 * @author 140024
	 * @implNote Repository 객체 생성
	 * @since 2024-06-09
	 */
	private final DtmPromotionRepository dtmPromotionRepo;

	/***
	 * @author 140024
	 * @implNote Repository 객체 생성
	 * @since 2024-06-09
	 */
	private final DtmSaveRepository dtmSaveRepo;

	/***
	 * @author 140024
	 * @implNote 연차촉진 체크
	 * @since 2024-09-11
	 * @param empId 행번(7140024)
	 * @param stdYear 기준년도(2024)
	 */
	public DtmPromotionDTO getDtmPromotionYnCheckData(final Long empId, final String stdYear) {

		final DtmPromotionDTO dtmPromotionDTO = new DtmPromotionDTO();
		dtmPromotionDTO.setEmpId(empId);
		dtmPromotionDTO.setStdYear(stdYear);

		dtmPromotionRepo.getDtmPromotionYn(dtmPromotionDTO); //dtm010_03_03_p_07

		if("Y".equals(dtmPromotionDTO.getPromotionYn())) {
			// 연차촉진 기간인 경우 제외자 확인
			dtmPromotionRepo.getDtmPromotionExp(dtmPromotionDTO); //dtm010_23_03_p_01
			if("0".equals(dtmPromotionDTO.getPromotionExp())) {
				// 연차촉진 대상자인 경우
				dtmPromotionRepo.getPromotionDetail(dtmPromotionDTO); //dtm010_23_03_p_04				
			}
		}		

		return dtmPromotionDTO;
	}
}
