package epams.domain.dtm.service;

import java.text.DecimalFormat;

import epams.domain.com.apply.dto.ElaApplCDTO;
import epams.domain.com.apply.dto.ElaApplTrCDTO;
import epams.domain.com.apply.repository.ElaApplCRepository;
import epams.domain.com.apply.repository.ElaApplTrCRepository;
import epams.domain.dtm.dto.DtmApplCheckProcDTO;
import epams.domain.dtm.dto.DtmApplElaCheckProcDTO;
import epams.domain.dtm.dto.DtmApplStatusDTO;
import epams.domain.dtm.dto.DtmCheckDTO;
import epams.domain.dtm.dto.DtmHisDTO;
import epams.domain.dtm.dto.DtmKindSumDTO;
import epams.domain.dtm.dto.DtmPromotionDTO;
import epams.domain.dtm.dto.DtmSaveDTO;
import epams.domain.dtm.repository.DtmHistoryRepository;
import epams.domain.dtm.repository.DtmApplProcRepository;
import epams.domain.dtm.repository.DtmCheckRepository;
import epams.framework.exception.CustomGeneralRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.time.LocalDate;



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
	 * @implNote Repository 객체 생성
	 * @since 2024-08-04
	 */
	private final DtmCheckRepository dtmCheckRepository;

	/***
	 * @author 140024
	 * @implNote 근태신청 가능여부 체크로직
	 * @since 2024-09-13
	 */
	public void check(final List<DtmHisDTO> hisDTOList, final DtmPromotionDTO proDTO, final DtmSaveDTO saveDTO, final DtmApplStatusDTO statusDTO) {

		// 사용자 화면에 숫자 표시 소숫점이 있으면 실수로, 소숫점이 없으면 정수형으로 표기
		final DecimalFormat decimalFormat = new DecimalFormat("0.#");		
		final DtmKindSumDTO sumDTO = new DtmKindSumDTO();

		for(DtmHisDTO hisDTO : hisDTOList) {

			// 근태 체크를 위한 input 데이터 세팅
			final DtmCheckDTO checkDTO = new DtmCheckDTO( 
				hisDTO.getDtmReasonCd(), // 근태종류
				hisDTO.getStaYmd(), // 근태시작일
				hisDTO.getEndYmd(), // 근태종료일
				hisDTO.getEmpId() // 직원행번
			);
			
			if(checkDTO.getAnnualCheckList().contains(hisDTO.getDtmReasonCd())) {
				// 근태 체크 대상(연차)인 경우 데이터 가져오기
				dtmCheckRepository.getNumberOfDay(checkDTO); //daycnt_day
				dtmCheckRepository.getNumberOfHour(checkDTO); //daycnt

				// 합계 구하기
				if(hisDTO.getStaYmd().getYear() == LocalDate.now().getYear()) {
					sumDTO.thisAnnaulSum(checkDTO.getDayCount(), checkDTO.getHourCount()); // 올해 합계
				} 
				else {
					sumDTO.nextAnnualSum(checkDTO.getDayCount(), checkDTO.getHourCount()); // 내년 합계
				}
			}

			if(sumDTO.getNextAnnualHourSum() > 0) {
				// 내년 연차 사용 시
				if(sumDTO.getNextAnnualHourSum() > statusDTO.getNextAnnualHourRemainCnt()) {
					// 내년 연차가 부족할때	
					throw new CustomGeneralRuntimeException(
							"<p> 내년에 사용 가능한 연차가 부족합니다.</p>");	
				}
			}
			else {
				// 올해 연차 등 근태 사용 시

			}
			
			

		}

		

	}

	/***
	 * @author 140024
	 * @implNote 근태신청
	 * @since 2024-06-09
	 */
	@Transactional
	public String insert(final DtmHisDTO dtmHisDTO) {
		try {
			// 사전검증 프로시저
			final DtmApplCheckProcDTO preCheckProcDTO = new DtmApplCheckProcDTO(
					dtmHisDTO.getEmpId(),
					dtmHisDTO.getDtmReasonCd(),
					dtmHisDTO.getStaYmd(),
					dtmHisDTO.getEndYmd(),
					dtmHisDTO.getEmpId()
			); // DtmApplCheckProcDTO(사전검증) 객체 생성
			proCheckProcRepo.callPreCheckProc(preCheckProcDTO); // 사전검증 프로시저 호출

			// 사전검증 실패 시 예외 처리
			if ("FAILURE!".equals(preCheckProcDTO.getResultCode())) {
				throw new CustomGeneralRuntimeException(preCheckProcDTO.getResultMsg());
			}

			// 신청서 추가
			final ElaApplCDTO elaApplCDTO = new ElaApplCDTO(dtmHisDTO.getEmpId(), "DTM01", "121"); // ElaApplCDTO(신청서) 객체 생성
			final long applId = elaApplCRepo.insert(elaApplCDTO); // 신청서 추가 후 applId 반환

			// 근태이력 추가
			dtmHisDTO.setApplId(applId); // DtmHisDTO(근태신청) 객체에 신청서 ID 설정
			dtmHisRepo.insert(dtmHisDTO); // 근태신청 추가

			// 신청서결재내역 추가
			final ElaApplTrCDTO elaApplTrCDTO = new ElaApplTrCDTO(dtmHisDTO.getEmpId(), "1", applId, 1, "201"); // ElaApplTrCDTO(신청서결재내역) 객체 생성
			elaApplTrCRepo.insert(elaApplTrCDTO); // 신청서결재내역 추가

			// 사후검증 프로시저 #1
			final DtmApplElaCheckProcDTO postCheckProcDTO = new DtmApplElaCheckProcDTO(
					dtmHisDTO.getApplId(),
					dtmHisDTO.getEmpId()
			); // postCheckProcDTO(사후검증) 객체 생성
			proCheckProcRepo.callNewCheckProc(postCheckProcDTO); // 사후검증 프로시저 호출

			// 사후검증 실패 시 예외 처리
			if ("FAILURE!".equals(postCheckProcDTO.getResultCode())) {
				throw new CustomGeneralRuntimeException(postCheckProcDTO.getResultMsg());
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

			// 사후검증 프로시저 #2
			proCheckProcRepo.callNewCheckProc(postCheckProcDTO); // 사후검증 프로시저 호출

			// 사후검증 실패 시 예외 처리
			if ("FAILURE!".equals(postCheckProcDTO.getResultCode())) {
				throw new CustomGeneralRuntimeException(postCheckProcDTO.getResultMsg());
			}

			return "신청이 성공적으로 처리되었습니다.";

		} catch (CustomGeneralRuntimeException e) {
			log.error("[신청 불가] {}", e.getMessage());
			throw e; // 커스텀 예외를 그대로 던져서 컨트롤러에서 처리하게 합니다.
		} catch (Exception e) {
			log.error("예기치 못한 오류 발생", e);
			throw new CustomGeneralRuntimeException("신청 처리 중 오류가 발생했습니다. 관리자에게 문의하세요.");
		}
	}
}
