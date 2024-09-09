package epams.domain.dtm.service;

import epams.domain.com.apply.repository.ElaApplCRepository;
import epams.domain.dtm.dto.DtmApplCheckProcDTO;
import epams.domain.dtm.dto.DtmApplElaCheckProcDTO;
import epams.domain.dtm.dto.DtmHisDTO;
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
public class DtmRevokeService {

    /***
	 * @author 140024
	 * @implNote Repository 객체 생성
	 * @since 2024-06-09
	 */
	private final DtmRevokeRepository dtmRevokeRepo;

	/***
	 * @author 140024
	 * @implNote logRepository 객체의 findLoginLogAll 호출하여 LogLoginEntity 리스트 반환
	 * @since 2024-06-09
	 */
	@Transactional
	public String revoke(final DtmHisDTO dto) {

		try {

			final DtmApplElaCheckProcDTO dtmApplElaCheckProcDTO = new DtmApplElaCheckProcDTO(
				dto.getApplId(),
				dto.getModUserId()
			); // DtmApplCheckProcDTO(검증) 객체 생성
			log.warn(dtmApplElaCheckProcDTO.toString());
			dtmRevokeRepo.callRevokeCheckProc(dtmApplElaCheckProcDTO); // 프로시저 호출
			log.warn(dtmApplElaCheckProcDTO.getResultMsg());
			// 프로시저 실패 시 예외 처리
			if ("FAILURE!".equals(dtmApplElaCheckProcDTO.getResultCode())) {
				throw new CustomGeneralRuntimeException(dtmApplElaCheckProcDTO.getResultMsg());
			}

			return "취소가 완료되었습니다.";

		} catch (CustomGeneralRuntimeException e) {
			log.error("[취소 불가] {}", e.getMessage());
			throw e; // 커스텀 예외를 그대로 던져서 컨트롤러에서 처리하게 합니다.
		} catch (Exception e) {
			log.error("예기치 못한 오류 발생", e);
			throw new CustomGeneralRuntimeException("취소 처리 중 오류가 발생했습니다. 관리자에게 문의하세요.");
		}
	}
}
