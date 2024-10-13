package epams.domain.dtm.service;

import org.springframework.stereotype.Service;

import epams.domain.dtm.dto.DtmAnnualStatusDTO;
import epams.domain.dtm.dto.DtmSqlParamDTO;
import epams.domain.dtm.repository.DtmAnnualStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/***
 * @author 140024
 * @implNote 연간근태현황 조회 Service
 * @since 2024-06-09
 */
@Slf4j
@ToString
@RequiredArgsConstructor
@Service
public class DtmAnnualStatusService {

    /***
	 * @author 140024
	 * @implNote Repository 객체 생성
	 * @since 2024-06-09
	 */
	private final DtmAnnualStatusRepository dtmAnnualStatusRepository;


	/***
	 * @author 140024
	 * @implNote 연간근태현황 조회
	 * @since 2024-09-11
	 * @param empId 행번(7140024)
	 */
	public DtmAnnualStatusDTO getDtmAnnualStatus(final Long empId, final String thisYear) {
		final DtmSqlParamDTO paramDTO = new DtmSqlParamDTO(empId, thisYear, "01", "KO"); 
		return dtmAnnualStatusRepository.getDtmAnnualStatus(paramDTO);
	} 
}
