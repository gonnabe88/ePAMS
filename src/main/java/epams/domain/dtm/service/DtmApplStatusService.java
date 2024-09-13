package epams.domain.dtm.service;

import org.springframework.stereotype.Service;

import epams.domain.dtm.dto.DtmApplStatusDTO;
import epams.domain.dtm.repository.DtmApplStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/***
 * @author 140024
 * @implNote 근태 신청 관련 정보 체크 Service
 * @since 2024-06-09
 */
@Slf4j
@ToString
@RequiredArgsConstructor
@Service
public class DtmApplStatusService {

	/***
	 * @author 140024
	 * @implNote Repository 객체 생성
	 * @since 2024-06-09
	 */
	private final DtmApplStatusRepository dtmApplStatusRepo;

	/***
	 * @author 140024
	 * @implNote 근태 신청 관련 정보
	 * @since 2024-09-11
	 * @param empId 행번(7140024)
	 * @param stdYear 기준년도(2024)
	 */
	public DtmApplStatusDTO getApplStatus(final Long empId, final String stdYear) {

		final DtmApplStatusDTO dtmApplStatusDTO = new DtmApplStatusDTO();
		dtmApplStatusDTO.setEmpId(empId);
		dtmApplStatusDTO.setStdYear(stdYear);		

		dtmApplStatusRepo.getApplStatus(dtmApplStatusDTO);

		return dtmApplStatusDTO;
	}
}
