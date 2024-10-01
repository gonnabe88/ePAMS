package epams.domain.dtm.service;

import org.springframework.stereotype.Service;

import epams.domain.dtm.dto.DtmHisDTO;
import epams.domain.dtm.dto.DtmPromotionDTO;
import epams.domain.dtm.repository.DtmEtcRepository;
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
public class DtmEtcService {

	/***
	 * @author 140024
	 * @implNote Repository 객체 생성
	 * @since 2024-06-09
	 */
	private final DtmEtcRepository dtmEtcRepo;

	/***
	 * @author 140024
	 * @implNote 근태시간 체크
	 * @since 2024-09-11
	 * @param empId 행번(7140024)
	 * @param stdYear 기준년도(2024)
	 */
	public void findDtmPeriod(final DtmHisDTO dto) {
		dtmEtcRepo.findDtmPeriod(dto); //dtm010_23_03_p_01
		log.warn(dto.toString());
	}
}
