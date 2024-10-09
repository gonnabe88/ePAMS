package epams.domain.dtm.service;

import java.text.DecimalFormat;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import epams.domain.com.apply.dto.ElaApplCDTO;
import epams.domain.com.apply.dto.ElaApplTrCDTO;
import epams.domain.com.apply.repository.ElaApplCRepository;
import epams.domain.com.apply.repository.ElaApplTrCRepository;
import epams.domain.dtm.dto.DtmApplElaCheckProcDTO;
import epams.domain.dtm.dto.DtmCheckDTO;
import epams.domain.dtm.dto.DtmHisDTO;
import epams.domain.dtm.dto.DtmPromotionDTO;
import epams.domain.dtm.dto.DtmSaveDTO;
import epams.domain.dtm.repository.DtmCheckRepository;
import epams.domain.dtm.repository.DtmHistoryRepository;
import epams.domain.dtm.repository.DtmRevokeRepository;
import epams.framework.exception.CustomGeneralRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/***
 * @author 140024
 * @implNote 근태취소 Service
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
	 * @implNote Repository 객체 생성
	 * @since 2024-09-13
	 */
	private final DtmCheckRepository dtmCheckRepository;

	/***
	 * @author 140024
	 * @implNote 근태취소 가능여부 체크로직
	 * @since 2024-09-13
	 */
	public void check(final DtmHisDTO hisDTO, final DtmPromotionDTO proDTO, final DtmSaveDTO saveDTO) {

		// 사용자 화면에 숫자 표시 소숫점이 있으면 실수로, 소숫점이 없으면 정수형으로 표기
		DecimalFormat decimalFormat = new DecimalFormat("0.#");		

		// 근태 체크를 위한 input 데이터 세팅
		final DtmCheckDTO checkDTO = new DtmCheckDTO( 
			hisDTO.getDtmReasonCd(), // 근태종류
			hisDTO.getStaYmd(), // 근태시작일
			hisDTO.getEndYmd(), // 근태종료일
			hisDTO.getEmpId() // 직원행번
		);

		// 근태 체크 대상(연차)인 경우 데이터 가져오기
		if(checkDTO.getAnnualCheckList().contains(hisDTO.getDtmReasonCd())) {
			dtmCheckRepository.getNumberOfDay(checkDTO); //daycnt_day
			dtmCheckRepository.getNumberOfHour(checkDTO); //daycnt
			log.info("연차촉진 및 저축 확인대상");
		}

		// 취소 후 연차사용일수 (tot_used2 = tot_used*1 - daycnt_day*1)
		checkDTO.setAfterAnnualDayUsedCnt(proDTO.getAnnualDayUsedCnt() - checkDTO.getDayCount());
		// 취소 후 연차사용시간 (tot_used_hh2 = tot_used_hh*1 - daycnt*1)
		checkDTO.setAfterAnnualHourUsedCnt(proDTO.getAnnualHourUsedCnt() - checkDTO.getHourCount());

		if(checkDTO.getDayCount() > 0) { // 연차촉진 및 저축 확인대상 근태를 사용하는 경우
			if("Y".equals(saveDTO.getSavableYn())) { // 저축가능기간인 경우		

				if(proDTO.getIngRevokeHourCnt() > 0) { //취소중인 근태가있을경우
					checkDTO.setAfterAnnualHourUsedCnt(checkDTO.getAfterAnnualHourUsedCnt() - proDTO.getIngRevokeHourCnt());
					checkDTO.setAfterAnnualDayUsedCnt(checkDTO.getAfterAnnualDayUsedCnt() - proDTO.getIngRevokeDayCnt());
				}
				
				if("N".equals(saveDTO.getSaveYn())) { // 저축을 하지 않은 경우					
					if(proDTO.getAnnualHourUsedCnt() < proDTO.getDutyAnnualHourTotalCnt()) {
						throw new CustomGeneralRuntimeException(
							"<p> 연차 촉진기간만큼 연차가 등록되지않았습니다." + 
							"연차 촉진기간만큼 연차를 등록한 후 진행해주세요.</p>" +
							"<h6 class=\"text-left\"> 연차 촉진기간 : " + decimalFormat.format(proDTO.getDutyAnnualDayTotalCnt()) + "일</h6>" + 
							"<h6 class=\"text-left\"> 취소 전 연차 사용기간 : " + decimalFormat.format(proDTO.getAnnualDayUsedCnt()) + "일</h6>" +
							"<h6 class=\"text-left text-danger\"> 취소 후 연차 사용기간 : " + decimalFormat.format(checkDTO.getAfterAnnualDayUsedCnt()) + "일</h6>");
					}					
				}

				if(proDTO.getDutyAnnualHourTotalCnt() < saveDTO.getSaveHourCnt()) {
					// 촉진시간-저축시간이 < 0 인 경우 저축가능시간은 촉진시간 이하가 되어야함
					saveDTO.setSaveHourCnt(proDTO.getDutyAnnualHourTotalCnt());
				}
				
				if(checkDTO.getAfterAnnualHourUsedCnt() < proDTO.getDutyAnnualHourTotalCnt()) {
					//취소 후 사용한 근태사용일수가 의무사용연차보다 작아지는 경우
					throw new CustomGeneralRuntimeException(
						"<p> 연차저축에 해당하는 근태변경입니다. 저축 필요시 내부망 PAMS 근태 저축화면을 이용해주세요</p>" +
						"<h6 class=\"text-left\"> 연차 촉진기간 : " + decimalFormat.format(proDTO.getDutyAnnualDayTotalCnt()) + "일</h6>" + 
						"<h6 class=\"text-left\"> 취소 전 연차 사용기간 : " + decimalFormat.format(proDTO.getAnnualDayUsedCnt()) + "일</h6>" +
						"<h6 class=\"text-left text-danger\"> 취소 후 연차 사용기간 : " + decimalFormat.format(checkDTO.getAfterAnnualDayUsedCnt()) + "일</h6>");
				}

			} else { //연차저축제시행하지않는경우 원래 촉진제 체크로직으로 실행

				if(checkDTO.getAfterAnnualHourUsedCnt() < proDTO.getDutyAnnualHourTotalCnt()) {
					// 취소 후 사용한 근태사용시간이 의무사용연차보다 작은 경우
					throw new CustomGeneralRuntimeException(
						"<p> 근태 취소 시 연차 촉진기간보다 사용한 근태기간이 적어 취소할 수 없습니다." + 
						"취소 필요시 새로운 근태를 등록한 후 취소해주시기 바랍니다.</p>" +
						"<h6 class=\"text-left\"> 연차 촉진기간 : " + decimalFormat.format(proDTO.getDutyAnnualDayTotalCnt()) + "일</h6>" + 
						"<h6 class=\"text-left\"> 취소 전 연차 사용기간 : " + decimalFormat.format(proDTO.getAnnualDayUsedCnt()) + "일</h6>" +
						"<h6 class=\"text-left text-danger\"> 취소 후 연차 사용기간 : " + decimalFormat.format(checkDTO.getAfterAnnualDayUsedCnt()) + "일</h6>");
				}

			}
		}
	}

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

			// [INSERT] 신청서 추가 후 applId 반환
			final long applId = elaApplCRepo.insert(elaApplCDTO); 

			// 근태이력 추가 (D 삭제 / DTM_HIS_ID 삭제대상 / 121 결재중)
			dtmHisDTO.setApplId(applId); // DtmHisDTO(근태신청) 객체에 신청서 ID 설정
			dtmHisDTO.setModiDtmHisId(dtmHisDTO.getDtmHisId());
			dtmHisDTO.setModiType("D");
			dtmHisDTO.setStatCd("121");
			dtmHisDTO.setModiReason("ePAMS Test");

			log.warn(dtmHisDTO.toString());
			// [INSERT] 근태신청 추가
			dtmHisRepo.insert(dtmHisDTO); 

			// [INSERT] 신청서결재내역 추가 (201 결재요청)
			final ElaApplTrCDTO elaApplTrCDTO = new ElaApplTrCDTO(dtmHisDTO.getEmpId(), "1", applId, 1, "201"); // ElaApplTrCDTO(신청서결재내역) 객체 생성
			elaApplTrCRepo.insert(elaApplTrCDTO); // 신청서결재내역 추가

			// 사후검증 프로시저
			final DtmApplElaCheckProcDTO dtmApplElaCheckProcDTO = new DtmApplElaCheckProcDTO(
				dtmHisDTO.getApplId(),
				dtmHisDTO.getModUserId()
			); // DtmApplCheckProcDTO(검증) 객체 생성

			// [CALL] 프로시저 호출
			dtmRevokeRepo.callRevokeCheckProc(dtmApplElaCheckProcDTO);

			// 프로시저 실패 시 예외 처리
			if ("FAILURE!".equals(dtmApplElaCheckProcDTO.getResultCode())) {
				throw new CustomGeneralRuntimeException(dtmApplElaCheckProcDTO.getResultMsg());
			}

			// [UPDATE] 신청서 업데이트
			elaApplCDTO.setStatCd("132"); // 결재완료 상태 세팅
			elaApplCRepo.update(elaApplCDTO); // 결재완료 상태로 업데이트
			
			log.warn(dtmHisDTO.toString());
			// [UPDATE] 근태이력 업데이트
			dtmHisDTO.setStatCd("132"); // 결재완료 상태 세팅
			dtmHisRepo.updateByApplId(dtmHisDTO); // 결재완료 상태로 업데이트
			
			// [UPDATE] 신청서결재내역 업데이트
			elaApplTrCDTO.setApprCd("202"); // 결재완료 상태 세팅
			elaApplTrCRepo.updateByApplId(elaApplTrCDTO); // 결재완료 상태로 업데이트

			// [CALL] 프로시저 호출
			dtmRevokeRepo.callRevokeCheckProc(dtmApplElaCheckProcDTO); 
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
