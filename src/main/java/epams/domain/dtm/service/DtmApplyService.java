package epams.domain.dtm.service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import epams.domain.com.admin.service.LangService;
import epams.domain.com.apply.dto.ElaApplCDTO;
import epams.domain.com.apply.dto.ElaApplTrCDTO;
import epams.domain.com.apply.repository.ElaApplCRepository;
import epams.domain.com.apply.repository.ElaApplTrCRepository;
import epams.domain.com.constValue.ConstValueService;
import epams.domain.dtm.dto.DtmApplCheckProcDTO;
import epams.domain.dtm.dto.DtmApplElaCheckProcDTO;
import epams.domain.dtm.dto.DtmApplStatusDTO;
import epams.domain.dtm.dto.DtmCheckDTO;
import epams.domain.dtm.dto.DtmHisDTO;
import epams.domain.dtm.dto.DtmKindSumDTO;
import epams.domain.dtm.dto.DtmPromotionDTO;
import epams.domain.dtm.dto.DtmSaveDTO;
import epams.domain.dtm.repository.DtmApplProcRepository;
import epams.domain.dtm.repository.DtmCheckRepository;
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
	 * @implNote Service 객체 생성
	 * @since 2024-08-04
	 */
	private final ConstValueService constValueService;

	/***
	 * @author 140024
	 * @implNote Service 객체 생성
	 * @since 2024-08-04
	 */
	private final LangService langService;

	/***
	 * @author 140024
	 * @implNote 근태신청 가능여부 체크로직
	 * @since 2024-09-13
	 */
	public boolean check(final List<DtmHisDTO> hisDTOList, final DtmPromotionDTO proDTO, final DtmSaveDTO saveDTO, final DtmApplStatusDTO statusDTO, final DtmKindSumDTO sumDTO) {

		// 사용자 화면에 숫자 표시 소숫점이 있으면 실수로, 소숫점이 없으면 정수형으로 표기
		final DecimalFormat decimalFormat = new DecimalFormat("0.#");		
		
		// 선연차 상수값 가져오기
		final float advAnnualDay = Float.parseFloat(constValueService.findConstValue("DTM", "DTM_CREATE_C50")); // cons_cnt
		final float advAnnualHour = Float.parseFloat(constValueService.findConstValue("DTM", "DTM_CREATE_C60")); // cons_hhcnt

		Boolean adUseYn = false;
		
		/***
		 * @author 140024
		 * @implNote 신청된 근태 유형별 합계 시간 계산
		 * @since 2024-09-13
		 */
		for(DtmHisDTO hisDTO : hisDTOList) {

			// 근태 체크를 위한 input 데이터 세팅
			final DtmCheckDTO checkDTO = new DtmCheckDTO( 
				hisDTO.getDtmReasonCd(), // 근태종류
				hisDTO.getStaYmd(), // 근태시작일
				hisDTO.getEndYmd(), // 근태종료일
				hisDTO.getEmpId() // 직원행번
			);
			
			if(checkDTO.getAnnualCheckList().contains(hisDTO.getDtmReasonCd())) { // 연차
				// 근태 체크 대상(연차)인 경우 데이터 가져오기
				dtmCheckRepository.getNumberOfDay(checkDTO); //daycnt_day
				dtmCheckRepository.getNumberOfHour(checkDTO); //daycnt
				log.warn(checkDTO.toString());
				// 합계 구하기
				if(hisDTO.getStaYmd().getYear() == LocalDate.now().getYear()) {
					sumDTO.thisAnnaulSum(checkDTO.getDayCount(), checkDTO.getHourCount()); // 올해 합계 (daycnt1)
				} 
				else {
					sumDTO.nextAnnualSum(checkDTO.getDayCount(), checkDTO.getHourCount()); // 내년 합계 (daycnt8)
				}
			}
		}

		/***
		 * @author 140024
		 * @implNote 연차(당해/내년) 검증
		 * @since 2024-09-13
		 */
		if(sumDTO.getNextAnnualHourSum() > 0) { // (내년 연차 사용 시) 신청된 근태의 일수의 합이 연차사용가능일수보다 크면 막음
			if(sumDTO.getNextAnnualHourSum() > statusDTO.getNextAnnualHourRemainCnt()) { // 내년 연차가 부족할때					
				throw new CustomGeneralRuntimeException(
					langService.findLangById("DTM_ERROR_003") // 내년에 사용 가능한 연차가 부족합니다
				);
			}
		}
		else { // (올해 연차 등 근태 사용 시)
			if(statusDTO.getAnnualHourTotalCnt() < advAnnualHour) { // hhCnt > cons_hhcnt 
				if(sumDTO.getAnnualHourSum() > 0) { // 연차가 아닌경우는 체크하지않음 daycnt1
					if((statusDTO.getAnnualHourUsedCnt() + sumDTO.getAnnualHourSum()) > (statusDTO.getAnnualHourTotalCnt() + statusDTO.getAdvAnnualHourNetUsedCnt())) { 
						//사용한 연차수 + 신청한 연차수 > 연차발생일수 + 선사용가능연차일수
						//usedHhCnt*1 + daycnt1*1 > hhCnt*1 + ad_use_hhcnt*1

						float annualHourRemainCnt = 0f; // 연차 잔여시간
						float advAnnualHourRemainCnt = 0f; // 선연차 잔여시간

						if (statusDTO.getAnnualHourTotalCnt() <= statusDTO.getAnnualHourUsedCnt()) { //연차발생일수가 사용한 연차일수와 같거나 작은 경우
							annualHourRemainCnt = 0f;
							advAnnualHourRemainCnt = statusDTO.getAnnualHourTotalCnt() + statusDTO.getAdvAnnualHourNetUsedCnt() - statusDTO.getAnnualHourUsedCnt();
						} else {
							annualHourRemainCnt = statusDTO.getAnnualHourTotalCnt() - statusDTO.getAnnualHourUsedCnt();
							advAnnualHourRemainCnt = statusDTO.getAdvAnnualHourNetUsedCnt();
						}

						
						throw new CustomGeneralRuntimeException(
							String.format(
								langService.findLangById("DTM_ERROR_001"), // 연차 사용가능 시간이 부족합니다.
								decimalFormat.format(annualHourRemainCnt), 
								decimalFormat.format(advAnnualHourRemainCnt), 
								decimalFormat.format(sumDTO.getAnnualHourSum())
								));
					} else if(statusDTO.getAnnualHourUsedCnt() + sumDTO.getAnnualHourSum() > statusDTO.getAnnualHourTotalCnt()){
						// 사용한 연차수 + 신청한 연차수 > 연차발생일수 => 선사용연차 사용
						// 현재 신청된 모든 건에 대해 ad_use_yn = Y 세팅
						hisDTOList.forEach(hisDTO -> hisDTO.setAdUseYn("Y"));
						adUseYn = true;
					}
				}
			} else {
				if(sumDTO.getAnnualHourSum() > statusDTO.getAnnualHourRemainCnt()) { // 신청한 연차 시간이 잔여 시간보다 큰 경우
					// 연차 사용가능 시간이 부족합니다. 잔여시간(<<remain_cnt>>),신청시간(<<app_cnt>>)
					throw new CustomGeneralRuntimeException(
						String.format(
							langService.findLangById("DTM_ERROR_004"), // 연차 사용가능 시간이 부족합니다
							decimalFormat.format(statusDTO.getAnnualHourRemainCnt()),
							decimalFormat.format(sumDTO.getAnnualHourSum())
						));
				}
			}
		}

		/***
		 * @author 140024
		 * @implNote 연차촉진 검증
		 * @since 2024-09-13
		 */
		if("Y".equals(proDTO.getPromotionYn())){
			if(sumDTO.getAnnualHourSum() > 0) {
				if("Y".equals(saveDTO.getSavableYn())) {  // 저축기간인 경우
					if("Y".equals(saveDTO.getSaveYn())) { // 저축내역이 있을경우
						if(sumDTO.getAnnualHourSum() + saveDTO.getSaveHourCnt() < proDTO.getFixedDutyAnnualHourTotalCnt()) {
							throw new CustomGeneralRuntimeException(
								String.format(
									langService.findLangById("DTM_ERROR_002"), // 연차촉진시간(일수) 확인 후 연차휴가를 등록해주세요
									decimalFormat.format(proDTO.getFixedDutyAnnualHourTotalCnt())
								));
						}
					} else { 
						if(sumDTO.getAnnualHourSum() < proDTO.getFixedDutyAnnualHourTotalCnt()) {
							// 연차휴가 사용시간이 의무사용연차에 미달하는 경우
							throw new CustomGeneralRuntimeException(
								String.format(
									langService.findLangById("DTM_ERROR_002"), // 연차촉진시간(일수) 확인 후 연차휴가를 등록해주세요
									decimalFormat.format(proDTO.getFixedDutyAnnualHourTotalCnt())
							));
						}
					}
				} else {
					if(sumDTO.getAnnualHourSum() < proDTO.getFixedDutyAnnualHourTotalCnt()) {
						throw new CustomGeneralRuntimeException(
							String.format(
								langService.findLangById("DTM_ERROR_002"), // 연차촉진시간(일수) 확인 후 연차휴가를 등록해주세요
								decimalFormat.format(proDTO.getFixedDutyAnnualHourTotalCnt())
						));
					}
				}
			}
		}	
		
		return adUseYn;

	}

	/***
	 * @author 140024
	 * @implNote 근태신청
	 * @since 2024-06-09
	 */
	@Transactional
	public String insert(final List<DtmHisDTO> dtmHisDTOList) {

		for(DtmHisDTO dtmHisDTO : dtmHisDTOList) {
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

			

		} catch (CustomGeneralRuntimeException e) {
			log.error("[신청 불가] {}", e.getMessage());
			throw e; // 커스텀 예외를 그대로 던져서 컨트롤러에서 처리하게 합니다.
		} catch (Exception e) {
			log.error("예기치 못한 오류 발생", e);
			throw new CustomGeneralRuntimeException("신청 처리 중 오류가 발생했습니다. 관리자에게 문의하세요.");
		}
	}

	return "신청이 성공적으로 처리되었습니다.";
	}
}
