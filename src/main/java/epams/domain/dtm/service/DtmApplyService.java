package epams.domain.dtm.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import epams.domain.dtm.dto.DtmHisDTO;
import epams.framework.exception.CustomGeneralRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/***
 * @author 140024
 * @implNote Service
 * @since 2024-06-09
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class DtmApplyService {

    /***
     * @author 140024
     * @implNote 신청 서비스
     * @since 2024-06-09
     */
    private final DtmRegistService dtmRegistService;

    /***
     * @author 140024
     * @implNote 취소 서비스
     * @since 2024-06-09
     */
    private final DtmRevokeService dtmRevokeService;

	/***
	 * @author 140024
	 * @implNote 근태 신청
	 * @since 2024-06-09
	 */
	@Transactional
	public String apply(final long empId, final List<DtmHisDTO> revokeDTOList, final List<DtmHisDTO> registDTOList) {
		
		String resultMessage = "";
		
		try {

			for(DtmHisDTO dto : revokeDTOList) {
				resultMessage = dtmRevokeService.revoke(dto); // 근태 취소
            }

			resultMessage = dtmRegistService.regist(empId, registDTOList); // 근태 신청
		} catch (CustomGeneralRuntimeException e) {
			log.error("[신청 불가] {}", e.getMessage());
			throw e; // 커스텀 예외를 그대로 던져서 컨트롤러에서 처리하게 합니다.
		} catch (Exception e) {
			log.error("예기치 못한 오류 발생", e);
			throw new CustomGeneralRuntimeException("신청 처리 중 오류가 발생했습니다. 관리자에게 문의하세요.");
		}

		return resultMessage;
	}

}
