package epams.domain.dtm.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/***
 * @author 140024
 * @implNote 연차촉진 DTO
 * @since 2024-06-09
 */
@Slf4j
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DtmApplStatusDTO extends DtmSqlParamDTO {

    /***
     * @author 140024
     * @implNote 행번(7140024) (EMP_NO)
     * @since 2024-09-12
     */
    private String empNo;

    /***
     * @author 140024
     * @implNote 직원명(emp_nm)
     * @since 2024-09-12
     */
    private String empNm;

    /***
     * @author 140024
     * @implNote 직급명 (pos_grd_nm)
     * @since 2024-08-11
     */
    private String gradeNm;

    /***
     * @author 140024
     * @implNote 직급코드 (pos_grd_cd)
     * @since 2024-08-11
     */
    private String gradeCd;

    /***
     * @author 140024
     * @implNote 부점명 (org_nm)
     * @since 2024-08-19
     */
    private String deptName;

    /***
     * @author 140024
     * @implNote 직위명 (행원, 대리, 과장, 차장...) (pos_nm)
     * @since 2024-09-12
     */
    private String positionName;

    /***
     * @author 140024
     * @implNote 채용일자 (hire_ymd)
     * @since 2024-09-12
     */
    private LocalDateTime hireYmd;

    /***
     * @author 140024
     * @implNote 암호화주민등록번호 (ctz_no)
     * @since 2024-09-12
     */
    private String encryptedCtzNo;

    /***
     * @author 140024
     * @implNote 휴대폰번호 (telno)
     * @since 2024-09-12
     */
    private String PhoneNo;

    /***
     * @author 140024
     * @implNote 연차잔여일수(remain_cnt)
     * @since 2024-09-11
     */
    private float annualDayRemainDayCnt;

    /***
     * @author 140024
     * @implNote 자기개발연수(duty_cnt)
     * @since 2024-09-11
     */
    private float selfDevTrainingDayCnt;
    
    /***
     * @author 140024
     * @implNote 안식년휴가 10년(remain_cnt1)
     * @since 2024-09-11
     */
    private float sabbaticalYear10DayCnt;

    /***
     * @author 140024
     * @implNote 안식년휴가 20년(remain_cnt2)
     * @since 2024-09-11
     */
    private float sabbaticalYear20DayCnt;

    /***
     * @author 140024
     * @implNote 안식년휴가 30년(remain_cnt3)
     * @since 2024-09-11
     */
    private float sabbaticalYear30DayCnt;

    /***
     * @author 140024
     * @implNote 마일리지(remain_cnt4)
     * @since 2024-09-11
     */
    private float mileageDayCnt;

    /***
     * @author 140024
     * @implNote 장기근속/기타(remain_cnt5)
     * @since 2024-09-11
     */
    private float longSerivceDayCnt;

    /***
     * @author 140024
     * @implNote 보상휴가(remain_cnt6)
     * @since 2024-09-11
     */
    private float rewardDayCnt;

    /***
     * @author 140024
     * @implNote 보상휴가시간(remain_cnt7)
     * @since 2024-09-11
     */
    private float rewardHourCnt;

    /***
     * @author 140024
     * @implNote 내년연차잔여일수(NEXT_REMAIN_CNT)
     * @since 2024-09-11
     */
    private float NextAnnualDayRemainCnt;

    /***
     * @author 140024
     * @implNote 내년연차잔여시간(NEXT_REMAIN_HHCNT)
     * @since 2024-09-11
     */
    private float NextAnnualHourRemainCnt;

    /***
     * @author 140024
     * @implNote 연차발생일수(YYCNT)
     * @since 2024-09-11
     */
    private float annualDayTotalCnt;
    
    /***
     * @author 140024
     * @implNote 연차사용일수(USED_CNT)
     * @since 2024-09-11
     */
    private float annualDayUsedCnt;

    /***
     * @author 140024
     * @implNote 선연차사용일수(AD_USE_YYCNT)
     * @since 2024-09-11
     */
    private float advAnnualDayNetUsedCnt;

    /***
     * @author 140024
     * @implNote (-)연차 + 선연차사용일수(AD_USE_YYCNT2)
     * @since 2024-09-11
     */
    private float advAnnualDayTotalUsedCnt;

    /***
     * @author 140024
     * @implNote 선연차사용여부(AD_USE_YN)
     * @since 2024-09-11
     */
    private String advAnnualUsedYn = "Y";

    /***
     * @author 140024
     * @implNote 저축일수(SAVE_YYCNT)
     * @since 2024-09-11
     */
    private float saveDayCnt = 0F;


    /***
     * @author 140024
     * @implNote 연차잔여시간(remain_hhcnt)
     * @since 2024-09-11
     */
    private float annualHourRemainCnt;

    /***
     * @author 140024
     * @implNote 연차발생시간(HHCNT)
     * @since 2024-09-11
     */
    private float annualHourTotalCnt;

    /***
     * @author 140024
     * @implNote 연차사용시간(USED_HHCNT)
     * @since 2024-09-11
     */
    private float annualHourUsedCnt;

    /***
     * @author 140024
     * @implNote 선연차사용시간(AD_USE_HHCNT)
     * @since 2024-09-11
     */
    private float advAnnualHourNetUsedCnt;

    /***
     * @author 140024
     * @implNote (-)연차 + 선연차사용시간(AD_USE_HHCNT2)
     * @since 2024-09-11
     */
    private float advAnnualHourTotalUsedCnt;

    /***
     * @author 140024
     * @implNote 저축시간 (SAVE_HHCNT)
     * @since 2024-09-12
     */
    private float saveHourCnt;

}
