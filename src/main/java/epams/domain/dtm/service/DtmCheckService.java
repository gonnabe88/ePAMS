package epams.domain.dtm.service;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import epams.domain.com.admin.service.LangService;
import epams.domain.com.constValue.ConstValueService;
import epams.domain.dtm.dto.DtmApplStatusDTO;
import epams.domain.dtm.dto.DtmCheckDTO;
import epams.domain.dtm.dto.DtmHisDTO;
import epams.domain.dtm.dto.DtmKindSumDTO;
import epams.domain.dtm.dto.DtmPromotionDTO;
import epams.domain.dtm.dto.DtmSaveDTO;
import epams.domain.dtm.repository.DtmCheckRepository;
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
public class DtmCheckService {

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
	 * @implNote 근태신청 가능여부 체크로직 (공통로직)
	 * dtmCheck, applicationCheck
	 * @since 2024-09-13
	 */
	public boolean commonCheck(final List<DtmHisDTO> hisDTOList, final DtmPromotionDTO proDTO, final DtmSaveDTO saveDTO, final DtmApplStatusDTO statusDTO, final DtmKindSumDTO sumDTO) {

		// 사용자 화면에 숫자 표시 소숫점이 있으면 실수로, 소숫점이 없으면 정수형으로 표기
		final DecimalFormat decimalFormat = new DecimalFormat("0.#");

		// 선연차 상수값 가져오기
		final float advAnnualDay = Float.parseFloat(constValueService.findConstValue("DTM", "DTM_CREATE_C50")); // 선연차 신청 가능일수(cons_cnt)
		final float advAnnualHour = Float.parseFloat(constValueService.findConstValue("DTM", "DTM_CREATE_C60")); // 선연차 신청 가능시간(cons_hhcnt) (yyhhcnt?, hhCnt?)

		// 1일 최소근무시간 상수값 가져오기
		final float minWorkTime = Float.parseFloat(constValueService.findConstValue("DTM", "DTM_MIN_WORK_HOUR")); 

		// 선연차 사용 동의 필요여부
		boolean adUseYn = false;

		//hisDTOList.forEach(dto -> log.warn(dto.toString()));

		final DtmCheckDTO checkDTO = new DtmCheckDTO();

		/***
		 * @author 140024
		 * @implNote 신청된 근태 유형별 합계 시간 계산 & 중복 체크
		 * @since 2024-09-13
		 */
		for(DtmHisDTO hisDTO : hisDTOList) {
			/* @TODO 외부 테스트 시 주석 처리(시작) */
			long totalWorkTime = Duration.between(hisDTO.getBaseStartDateTime(), hisDTO.getBaseEndDateTime()).toMinutes();
			long workTime = totalWorkTime;
			LocalDateTime lunchStart;
			LocalDateTime lunchEnd;
			log.warn("전체근무시간 : " + String.valueOf(totalWorkTime));

			// 신청건 중 중복된 근태가 있는지 확인
			if(!"D".equals(hisDTO.getModiType())) { // 취소건 비교 X
				LocalDateTime staDateTime = hisDTO.getStartDateTime(); // 근태시작일시
				LocalDateTime endDateTime = hisDTO.getEndDateTime(); // 근태종료일시
				for (DtmHisDTO checkHisDTO : hisDTOList) {

					if (hisDTO != checkHisDTO) { // 동일 객체 비교 X
						
						LocalDateTime checkStaDateTime = checkHisDTO.getStartDateTime(); // 근태시작일시 (비교대상)
						LocalDateTime checkEndDateTime = checkHisDTO.getEndDateTime(); // 근태종료일시 (비교대상)

						if("D".equals(checkHisDTO.getModiType())) { // 취소건인 경우 동일한 날짜 & 근태로 변경인지 확인
							if(	(staDateTime.toLocalDate().equals(checkStaDateTime.toLocalDate())) &&
								(endDateTime.toLocalDate().equals(checkEndDateTime.toLocalDate())) &&
								(hisDTO.getDtmReasonCd().equals(checkHisDTO.getDtmReasonCd()))) {
								throw new CustomGeneralRuntimeException("<p class=\"text-center\">중복된 근태가 있습니다.</p><p class=\"text-center\">(변경 전/후 근태 동일)</p>");
							}
						} else {
							if((staDateTime.toLocalDate().isBefore(checkEndDateTime.toLocalDate()) && checkStaDateTime.toLocalDate().isBefore(endDateTime.toLocalDate())) && ("N".equals(checkHisDTO.getDtmCross()))) { // 날짜가 겹치는데 교차신청 불가 근태인 경우
								throw new CustomGeneralRuntimeException("<p class=\"text-center\">중복된 근태가 있습니다.</p><p class=\"text-center\">(교차신청 불가 근태)");
							} else if (staDateTime.isBefore(checkEndDateTime) && checkStaDateTime.isBefore(endDateTime)) { // 두 시간이 겹치는지 확인
								throw new CustomGeneralRuntimeException("<p class=\"text-center\">중복된 근태가 있습니다.</p><p class=\"text-center\">(근태 시간 중복)");
							} else if (hisDTO.getEndYmd() == checkHisDTO.getStaYmd()) { // 동일날짜(기준근태 < 비교근태)
								workTime = Duration.between(hisDTO.getEndDateTime(), checkHisDTO.getStartDateTime()).toMinutes(); // 실근무시간 (분)
								lunchStart = LocalDateTime.of(hisDTO.getEndYmd().toLocalDate(), LocalTime.of(12,0)); // 점심시작 YYYY-MM-DD hh:mm
								lunchEnd = LocalDateTime.of(hisDTO.getEndYmd().toLocalDate(), LocalTime.of(13,0)); // 점심종료 YYYY-MM-DD hh:mm
								if (hisDTO.getEndYmd().isAfter(lunchStart) || checkHisDTO.getStaYmd().isBefore(lunchEnd)) {
									workTime -= 60; // 두 근태중 하나라도 점심시간(12-13)에 겹치는 경우 1시간(60분) 차감
								}								
							} else if (hisDTO.getStaYmd() == checkHisDTO.getEndYmd()) { // 동일날짜(비교근태 < 기준근태)
								workTime = Duration.between(checkHisDTO.getEndDateTime(), hisDTO.getStartDateTime()).toMinutes(); // 실근무시간 (분)
								lunchStart = LocalDateTime.of(hisDTO.getStaYmd().toLocalDate(), LocalTime.of(12,0)); // 점심시작 YYYY-MM-DD hh:mm
								lunchEnd = LocalDateTime.of(hisDTO.getStaYmd().toLocalDate(), LocalTime.of(13,0)); // 점심종료 YYYY-MM-DD hh:mm
								if (checkHisDTO.getEndYmd().isAfter(lunchStart) || hisDTO.getStaYmd().isBefore(lunchEnd)) {
									workTime -= 60; // 두 근태중 하나라도 점심시간(12-13)에 겹치는 경우 1시간(60분) 차감
								}
							}
						}
					}
					if(workTime < minWorkTime) {
						throw new CustomGeneralRuntimeException("1일 최소근무시간 이상 근무해야합니다.");
					}
				}
			}
			/* @TODO 외부 테스트 시 주석 처리(끝) */

			// 금번 근태신청시간 합계 계산을 위한 input 데이터 세팅
			checkDTO.setEmpId(hisDTO.getEmpId()); // 직원행번
			checkDTO.setStaYmd(hisDTO.getStaYmd()); // 근태시작일
			checkDTO.setEndYmd(hisDTO.getEndYmd()); // 근태종료일
			checkDTO.setDtmReasonCd(hisDTO.getDtmReasonCd()); // 근태종류

			// 근태신청시간 합계 계산
			dtmCheckRepository.getNumberOfDay(checkDTO); // daycnt_day (dtm010_03_03_p_06, 근태신청일수)
			dtmCheckRepository.getNumberOfHour(checkDTO); // daycnt (dtm010_03_03_p_10, 근태신청시간)
			if(checkDTO.getAnnualCheckList().contains(hisDTO.getDtmReasonCd())) { // 연차
				if("D".equals(hisDTO.getModiType())){ // 취소인 경우 일수에 (-) 설정 (촉진일수 검증 시 필요)
					checkDTO.setDayCount(checkDTO.getDayCount() * -1);
					checkDTO.setHourCount(checkDTO.getHourCount() * -1);
				}
				if(hisDTO.getStaYmd().getYear() == LocalDate.now().getYear()) { // 시작일자가 올해인 경우
					sumDTO.thisAnnaulSum(checkDTO.getDayCount(), checkDTO.getHourCount()); // 올해 합계 (시간 daycnt1, 일수 daycnt9/ promotion_remain_cnt)
				} else { // 시작일자가 내년인 경우
					sumDTO.nextAnnualSum(checkDTO.getDayCount(), checkDTO.getHourCount()); // 내년 합계 (시간 daycnt8 / promotion_remain_tot_cnt)
				}
			} else if (checkDTO.getSaveCheckList().contains(hisDTO.getDtmReasonCd())) { // 저축
				if("D".equals(hisDTO.getModiType())){ // 취소인 경우 변경 전 저축 시간 반영
					sumDTO.setBeforeSaveHourSum(checkDTO.getHourCount());
				} else { // 신규인 경우 변경 후 저축 시간 반영
					sumDTO.setAfterSaveHourSum(checkDTO.getHourCount());
					sumDTO.thisSaveSum(checkDTO.getDayCount(), checkDTO.getHourCount()); // 합계 (시간 daycnt10)
				}
			}

			log.warn("금번 근태신청시간 합계 : " + checkDTO.toString());
			log.warn("전체 근태신청시간 합계 : " + sumDTO.toString());
			log.warn("촉진 : " + proDTO.toString());
			log.warn("저축 : " + saveDTO.toString());
		}

		/***
		 * @author 140024
		 * @implNote 저축사용가능시간 검증
		 * @since 2024-09-13
		 */
		if((sumDTO.getSaveHourSum() > 0) && (sumDTO.getSaveHourSum() - sumDTO.getBeforeSaveHourSum() > statusDTO.getSaveHourCnt())) {
			// 저축신청시간(daycnt10) > 0 && 저축신청시간(daycnt10) - 저축취소시간(daycnt11) > 저축사용가능시간(save_hhcnt)
			// 저축 사용시간이 저축잔여시간보다 큰 경우
			throw new CustomGeneralRuntimeException("저축일수가 부족합니다.");
		}

		/***
		 * @author 140024
		 * @implNote 연차사용가능시간 & 선연차 검증
		 * 기존 PAMS Javascript applicationCheck() 해당 로직
		 * @since 2024-09-13
		 */
		if(sumDTO.getNextAnnualHourSum() > 0) { // 내년 연차 사용 시 (신청된 근태의 일수의 합이 연차사용가능일수보다 크면 막음)
			if(sumDTO.getNextAnnualHourSum() > statusDTO.getNextAnnualHourRemainCnt()) { // 내년 연차가 부족할때
				throw new CustomGeneralRuntimeException(
						langService.findLangById("DTM_ERROR_003") // 내년에 사용 가능한 연차가 부족합니다
				);
			}
		} else { // 올해 연차 등 근태 사용 시
			if(statusDTO.getAnnualHourTotalCnt() < advAnnualHour) { // 연차발생시간(hhCnt) < 선연차기준시간(cons_hhcnt) @TODO 이거 왜 선연차 상수랑 비교하는지 모르겠음
				if(sumDTO.getAnnualHourSum() != 0) { // 연차신청시간합계(daycnt1, to_cnt) : 연차가 아닌경우는 체크하지않음
					if((statusDTO.getAnnualHourUsedCnt() + sumDTO.getAnnualHourSum()) > (statusDTO.getAnnualHourTotalCnt() + statusDTO.getAdvAnnualHourNetUsedCnt())) {
						//사용한 연차수(usedHhCnt / used_hhcnt) + 신청한 연차수(daycnt1, to_cnt) > 연차발생시간(hhCnt) + 선사용가능연차시간(ad_use_hhcnt)

						float annualHourRemainCnt = 0f; // 연차 잔여시간
						float advAnnualHourRemainCnt = 0f; // 선연차 잔여시간

						// @TODO stat_cd == '111'인 경우 따로 처리해야하나?
						if (statusDTO.getAnnualHourTotalCnt() <= statusDTO.getAnnualHourUsedCnt()) { //연간연차발생시간(hhCnt, yyCnt)이 사용한 연차시간(usedCnt+as_cnt)과 같거나 작은 경우
							annualHourRemainCnt = 0f;
							advAnnualHourRemainCnt = statusDTO.getAnnualHourTotalCnt() + statusDTO.getAdvAnnualHourNetUsedCnt() - statusDTO.getAnnualHourUsedCnt(); // (hhCnt + ad_use_hhcnt)-usedHhCnt;
						} else {
							annualHourRemainCnt = statusDTO.getAnnualHourTotalCnt() - statusDTO.getAnnualHourUsedCnt(); // hhCnt*1 - usedHhCnt*1
							advAnnualHourRemainCnt = statusDTO.getAdvAnnualHourNetUsedCnt(); // ad_use_hhcnt
						}

						throw new CustomGeneralRuntimeException(
								String.format(
										langService.findLangById("DTM_ERROR_001"), // 연차 사용가능 시간이 부족합니다.
										decimalFormat.format(annualHourRemainCnt),
										decimalFormat.format(advAnnualHourRemainCnt),
										decimalFormat.format(sumDTO.getAnnualHourSum())
								));
					} else if(statusDTO.getAnnualHourUsedCnt() + sumDTO.getAnnualHourSum() > statusDTO.getAnnualHourTotalCnt()){
						// 사용한 연차시간(usedHhCnt) + 금번 신청 연차시간(to_cnt - as_cnt) > 연차발생시간(hhCnt)
						// sumDTO.getAnnualHourSum() 구할 때 변경 전 근태시간(as_cnt) 차감함
						hisDTOList.forEach(hisDTO -> hisDTO.setAdUseYn("Y")); // 현재 신청된 모든 건에 대해 ad_use_yn = Y 세팅
						adUseYn = true;
					}
				}
			} else { // 연차발생시간(hhCnt) >= 선연차기준시간(cons_hhcnt)
				if(sumDTO.getAnnualHourSum() > statusDTO.getAnnualHourRemainCnt()) { // 신청시간(연차) > 잔여시간(연차)
					// 연차 사용가능 시간이 부족합니다. 잔여시간(<<remain_cnt>>),신청시간(<<app_cnt>>)
					throw new CustomGeneralRuntimeException(
							String.format(
									langService.findLangById("DTM_ERROR_004"), // 연차 사용가능 시간이 부족합니다
									decimalFormat.format(statusDTO.getAnnualDayRemainDayCnt()),
									decimalFormat.format(sumDTO.getAnnualDaySum())
							));
				}
			}
		}

		/***
		 * @author 140024
		 * @implNote 연차촉진 검증
		 * @since 2024-09-13
		 */
		if("Y".equals(proDTO.getPromotionYn()) && "0".equals(proDTO.getPromotionExp())){ // 연차촉진기간인 경우
			if(sumDTO.getAnnualHourSum() != 0) { // 금번 신청으로 인해 연차 사용일수에 변화가 있는 경우 (daycnt1 != 0)
				if("Y".equals(saveDTO.getSavableYn())) {  // 저축기간인 경우


					if("Y".equals(saveDTO.getSaveYn())) { // 저축내역이 있을경우
						if(proDTO.getAnnualHourUsedCnt() + sumDTO.getAnnualHourSum() + saveDTO.getSaveHourCnt() < proDTO.getDutyAnnualHourTotalCnt()) { // 연차(기존) + 연차(금번) + 저축(전체) < 연차 촉진시간
							throw new CustomGeneralRuntimeException(
									String.format(
											langService.findLangById("DTM_ERROR_002"), // 연차촉진시간(일수) 확인 후 연차휴가를 등록해주세요
											decimalFormat.format(proDTO.getDutyAnnualDayTotalCnt()) // 촉진일수
									));
						}
					} else {  // 저축내역이 없을경우
						if(proDTO.getAnnualHourUsedCnt() + sumDTO.getAnnualHourSum() < proDTO.getDutyAnnualHourTotalCnt()) { // 연차(기존) + 연차(금번) < 연차 촉진시간
							throw new CustomGeneralRuntimeException(
									String.format(
											langService.findLangById("DTM_ERROR_002"), // 연차촉진시간(일수) 확인 후 연차휴가를 등록해주세요
											decimalFormat.format(proDTO.getDutyAnnualDayTotalCnt()) // 촉진일수
									));
						} 
					}

					if(proDTO.getDutyAnnualHourTotalCnt() < saveDTO.getSaveHourCnt()) {
						// 촉진시간 < 저축시간 인 경우 저축가능시간은 촉진시간 이하가 되어야함
						saveDTO.setSaveHourCnt(proDTO.getDutyAnnualHourTotalCnt());
					}

				} else { // 저축기간이 아닌 경우
					if(proDTO.getAnnualHourUsedCnt() + sumDTO.getAnnualHourSum() < proDTO.getDutyAnnualHourTotalCnt()) { // 연차(기존) + 연차(금번) < 연차 촉진시간
						throw new CustomGeneralRuntimeException(
								String.format(
										langService.findLangById("DTM_ERROR_002"), // 연차촉진시간(일수) 확인 후 연차휴가를 등록해주세요
										decimalFormat.format(proDTO.getDutyAnnualHourTotalCnt())
								));
					}
				}
			}
		}

		return adUseYn;

	}
}
