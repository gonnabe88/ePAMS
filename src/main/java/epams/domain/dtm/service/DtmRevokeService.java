package epams.domain.dtm.service;

import epams.domain.com.apply.dto.ElaApplCDTO;
import epams.domain.com.apply.dto.ElaApplTrCDTO;
import epams.domain.com.apply.repository.ElaApplCRepository;
import epams.domain.com.apply.repository.ElaApplTrCRepository;
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
	 * @implNote Repository 객체 생성
	 * @since 2024-08-04
	 */
	private final ElaApplCRepository elaApplCRepo;

	/***
	 * @author 140024
	 * @implNote Repository 객체 생성
	 * @since 2024-08-04
	 */
	private final ElaApplTrCRepository elaApplTrCRepo;

	    /***
	 * @author 140024
	 * @implNote Repository 객체 생성
	 * @since 2024-06-09
	 */
	private final DtmHistoryRepository dtmHisRepo;

	/***
	 * @author 140024
	 * @implNote 근태취소
	 * @since 2024-09-11
	 */
	@Transactional
	public String revoke(final DtmHisDTO dtmHisDTO) {

		try {

			// 신청서 추가 (DTM03 근태취소신청서 / 121 결재중)
			final ElaApplCDTO elaApplCDTO = new ElaApplCDTO(dtmHisDTO.getEmpId(), "DTM03", "121"); // ElaApplCDTO(신청서) 객체 생성
			final long applId = elaApplCRepo.insert(elaApplCDTO); // 신청서 추가 후 applId 반환

			// 근태이력 추가 (D 삭제 / DTM_HIS_ID 삭제대상 / 121 결재중)
			dtmHisDTO.setApplId(applId); // DtmHisDTO(근태신청) 객체에 신청서 ID 설정
			dtmHisDTO.setModiDtmHisId(dtmHisDTO.getDtmHisId());
			dtmHisDTO.setModiType("D");
			dtmHisDTO.setStatCd("121");
			dtmHisDTO.setModiReason("ePAMS Test");
			dtmHisRepo.insert(dtmHisDTO); // 근태신청 추가

			// 신청서결재내역 추가 (201 결재요청)
			final ElaApplTrCDTO elaApplTrCDTO = new ElaApplTrCDTO(dtmHisDTO.getEmpId(), "1", applId, 1, "201"); // ElaApplTrCDTO(신청서결재내역) 객체 생성
			elaApplTrCRepo.insert(elaApplTrCDTO); // 신청서결재내역 추가

			// 사후검증 프로시저
			final DtmApplElaCheckProcDTO dtmApplElaCheckProcDTO = new DtmApplElaCheckProcDTO(
				dtmHisDTO.getApplId(),
				dtmHisDTO.getModUserId()
			); // DtmApplCheckProcDTO(검증) 객체 생성
			log.warn(dtmApplElaCheckProcDTO.toString());
			dtmRevokeRepo.callRevokeCheckProc(dtmApplElaCheckProcDTO); // 프로시저 호출
			log.warn(dtmApplElaCheckProcDTO.getResultMsg());
			// 프로시저 실패 시 예외 처리
			if ("FAILURE!".equals(dtmApplElaCheckProcDTO.getResultCode())) {
				throw new CustomGeneralRuntimeException(dtmApplElaCheckProcDTO.getResultMsg());
			}

			// 신청서 업데이트
			elaApplCDTO.setStatCd("132"); // 결재완료 상태 세팅
			elaApplCRepo.update(elaApplCDTO); // 결재완료 상태로 업데이트
			
			// 근태이력 업데이트
			dtmHisDTO.setStatCd("132"); // 결재완료 상태 세팅
			dtmHisRepo.updateByApplId(dtmHisDTO); // 결재완료 상태로 업데이트
			
			// 신청서결재내역 업데이트
			elaApplTrCDTO.setApprCd("202"); // 결재완료 상태 세팅
			elaApplTrCRepo.updateByApplId(elaApplTrCDTO); // 결재완료 상태로 업데이트

			dtmRevokeRepo.callRevokeCheckProc(dtmApplElaCheckProcDTO); // 프로시저 호출
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
