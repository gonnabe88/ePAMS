package epams.domain.dtm.service;

import epams.domain.com.apply.dto.ElaApplCDTO;
import epams.domain.com.apply.dto.ElaApplTrCDTO;
import epams.domain.com.apply.repository.ElaApplCRepository;
import epams.domain.com.apply.repository.ElaApplTrCRepository;
import epams.domain.dtm.dto.DtmAnnualStatusDTO;
import epams.domain.dtm.dto.DtmAnnualStatusParamDTO;
import epams.domain.dtm.dto.DtmApplCheckProcDTO;
import epams.domain.dtm.dto.DtmApplElaCheckProcDTO;
import epams.domain.dtm.dto.DtmHisDTO;
import epams.domain.dtm.repository.DtmAnnualStatusRepository;
import epams.domain.dtm.repository.DtmHistoryRepository;
import epams.domain.dtm.repository.DtmRevokeRepository;
import epams.framework.exception.CustomGeneralRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/***
 * @author 140024
 * @implNote Service
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
	 */
	@Transactional
	public DtmAnnualStatusDTO getDtmAnnualStatus(final Long empId) {

		try {
			final DtmAnnualStatusParamDTO paramDTO = new DtmAnnualStatusParamDTO(empId, "2024"); 
			return dtmAnnualStatusRepository.getDtmAnnualStatus(paramDTO);
		} catch (CustomGeneralRuntimeException e) {
			log.error("[조회 불가] {}", e.getMessage());
			throw e; // 커스텀 예외를 그대로 던져서 컨트롤러에서 처리하게 합니다.
		} catch (Exception e) {
			log.error("예기치 못한 오류 발생", e);
			throw new CustomGeneralRuntimeException("조회 중 오류가 발생했습니다. 관리자에게 문의하세요.");
		}
	}
}
