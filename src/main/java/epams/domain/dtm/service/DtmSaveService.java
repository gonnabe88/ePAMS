package epams.domain.dtm.service;

import org.springframework.stereotype.Service;

import epams.domain.dtm.dto.DtmSaveDTO;
import epams.domain.dtm.repository.DtmSaveRepository;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/***
 * @author 140024
 * @implNote 연차저축 관련 체크 Service
 * @since 2024-06-09
 */
@Slf4j
@ToString
@RequiredArgsConstructor
@Service
public class DtmSaveService {

	/***
	 * @author 140024
	 * @implNote Repository 객체 생성
	 * @since 2024-06-09
	 */
	private final DtmSaveRepository dtmSaveRepo;

	/***
	 * @author 140024
	 * @implNote 연차저축 체크
	 * @since 2024-09-11
	 * @param empId 행번(7140024)
	 * @param stdYear 기준년도(2024)
	 */
	public DtmSaveDTO getDtmSaveCheckData(final Long empId, final String stdYear) {

		final DtmSaveDTO dtmSaveDTO = new DtmSaveDTO();
		dtmSaveDTO.setEmpId(empId);
		dtmSaveDTO.setStdYear(stdYear);		

		dtmSaveRepo.getSaveStandard(dtmSaveDTO);
		dtmSaveRepo.getDtmGetSavableDay(dtmSaveDTO);

		return dtmSaveDTO;
	}
}
