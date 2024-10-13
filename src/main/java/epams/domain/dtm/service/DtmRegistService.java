package epams.domain.dtm.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import epams.domain.com.apply.dto.ElaApplCDTO;
import epams.domain.com.apply.dto.ElaApplTrCDTO;
import epams.domain.com.apply.repository.ElaApplCRepository;
import epams.domain.com.apply.repository.ElaApplTrCRepository;
import epams.domain.com.member.service.MemberService;
import epams.domain.com.sidebar.dto.UserInfoDTO;
import epams.domain.dtm.dto.DtmApplCheckProcDTO;
import epams.domain.dtm.dto.DtmApplElaCheckProcDTO;
import epams.domain.dtm.dto.DtmHisDTO;
import epams.domain.dtm.repository.DtmApplProcRepository;
import epams.domain.dtm.repository.DtmHistoryRepository;
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
public class DtmRegistService {

    /***
	 * @author 140024
	 * @implNote Repository 객체 생성
	 * @since 2024-06-09
	 */
	private final DtmHistoryRepository dtmHisRepo;

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
	 * @since 2024-08-04
	 */
	private final DtmApplProcRepository proCheckProcRepo;
	
	/***
	 * @author 140024
	 * @implNote Service 객체 생성
	 * @since 2024-09-13
	 */
	private final MemberService memberService;

	/***
	 * @author 140024
	 * @implNote 근태 신규
	 * @since 2024-06-09
	 */

	@Transactional
	public String regist(final long empId, final List<DtmHisDTO> dtmHisDTOList) {

		try {
			for(DtmHisDTO dtmHisDTO : dtmHisDTOList) {				
				// DtmApplCheckProcDTO(사전검증) 객체 생성
				final DtmApplCheckProcDTO dtmApplCheckProcDTO = new DtmApplCheckProcDTO(
						dtmHisDTO.getEmpId(),
						dtmHisDTO.getDtmReasonCd(),
						dtmHisDTO.getStaYmd(),
						dtmHisDTO.getEndYmd(),
						dtmHisDTO.getEmpId()
				); 

				// [CALL] 사전검증 프로시저 호출
				proCheckProcRepo.callPreCheckProc(dtmApplCheckProcDTO); 

				// 사전검증 실패 시 예외 처리
				if ("FAILURE!".equals(dtmApplCheckProcDTO.getResultCode())) {
					throw new CustomGeneralRuntimeException(dtmApplCheckProcDTO.getResultMsg());
				}				
			}
			// [INSERT] 신청서 추가
			final ElaApplCDTO elaApplCDTO = new ElaApplCDTO(empId, "DTM01", "121"); // ElaApplCDTO(신청서) 객체 생성
			final long applId = elaApplCRepo.insert(elaApplCDTO); // 신청서 추가 후 applId 반환

			UserInfoDTO userInfoDTO = new UserInfoDTO();

			/* @TODO 외부 테스트 시 주석 처리(시작)
			// 결재자(신청자) 정보(직위) 가져오기
			userInfoDTO = memberService.findUsrDeptInfoByUserNo(dtmHisDTO.getEmpId());
			@TODO 외부 테스트 시 주석 처리(끝) */

			// [INSERT] 신청서결재내역 추가
			final ElaApplTrCDTO elaApplTrCDTO = new ElaApplTrCDTO(empId, "1", applId, 1, "201", userInfoDTO.getPositionName()); // ElaApplTrCDTO(신청서결재내역) 객체 생성
			elaApplTrCRepo.insert(elaApplTrCDTO); // 신청서결재내역 추가

			for(DtmHisDTO dtmHisDTO : dtmHisDTOList) {

				// [INSERT] 근태이력 추가
				dtmHisDTO.setApplId(applId); // DtmHisDTO(근태신청) 객체에 신청서 ID 설정
				dtmHisRepo.insert(dtmHisDTO); // 근태신청 추가

				// postCheckProcDTO(사후검증) 객체 생성
				final DtmApplElaCheckProcDTO preDtmElaCheckProcDTO = new DtmApplElaCheckProcDTO(
						dtmHisDTO.getApplId(),
						dtmHisDTO.getEmpId()
				); 

				// [CALL] 사후검증 프로시저 호출 #1
				proCheckProcRepo.callNewCheckProc(preDtmElaCheckProcDTO); 

				// 사후검증 실패 시 예외 처리
				if ("FAILURE!".equals(preDtmElaCheckProcDTO.getResultCode())) {
					throw new CustomGeneralRuntimeException(preDtmElaCheckProcDTO.getResultMsg());
				}			
			}
			
			// [UPDATE] 신청서 업데이트
			elaApplCDTO.setStatCd("132"); // 결재완료 상태 세팅
			elaApplCRepo.update(elaApplCDTO); // 결재완료 상태로 업데이트

			// [UPDATE] 신청서결재내역 업데이트
			elaApplTrCDTO.setApprCd("202"); // 결재완료 상태 세팅
			elaApplTrCRepo.updateByApplId(elaApplTrCDTO); // 결재완료 상태로 업데이트

			for(DtmHisDTO dtmHisDTO : dtmHisDTOList) {					
				
				// [UPDATE] 근태이력 업데이트
				dtmHisDTO.setStatCd("132"); // 결재완료 상태 세팅
				dtmHisRepo.updateByApplId(dtmHisDTO); // 결재완료 상태로 업데이트

				// postCheckProcDTO(사후검증) 객체 생성
				final DtmApplElaCheckProcDTO postElaDtmCheckProcDTO = new DtmApplElaCheckProcDTO(
					dtmHisDTO.getApplId(),
					dtmHisDTO.getEmpId()
				); 

				// [CALL] 사후검증 프로시저 호출 #2
				proCheckProcRepo.callNewCheckProc(postElaDtmCheckProcDTO); // 사후검증 프로시저 호출

				// 사후검증 실패 시 예외 처리
				if ("FAILURE!".equals(postElaDtmCheckProcDTO.getResultCode())) {
					throw new CustomGeneralRuntimeException(postElaDtmCheckProcDTO.getResultMsg());
				}			

			}

			
		} catch (CustomGeneralRuntimeException e) {
			log.error("[신청 불가] {}", e.getMessage());
			throw e; // 커스텀 예외를 그대로 던져서 컨트롤러에서 처리하게 합니다.
		} catch (Exception e) {
			log.error("예기치 못한 오류 발생", e);
			throw new CustomGeneralRuntimeException("신청 처리 중 오류가 발생했습니다. 관리자에게 문의하세요.");
		}

		return "신청이 성공적으로 처리되었습니다.";
	}
}
