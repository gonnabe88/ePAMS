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
public class DtmPromotionDTO extends DtmSqlParamDTO {

    /***
     * @author 140024
     * @implNote 연차촉진제외대상여부 (0 : 대상 / 1 이상 : 제외 ) (CNT)
     * @since 2024-09-12
     */
    private String promotionExp = "0";

    /***
     * @author 140024
     * @implNote 연차촉진기간여부 (PROMOTION_YN)
     * @since 2024-09-12
     */
    private String promotionYn = "N";

    /***
     * @author 140024
     * @implNote 시작일 (STA_YMD)
     * @since 2024-08-11
     */
    private LocalDateTime staYmd = LocalDateTime.now();

    /***
     * @author 140024
     * @implNote 종료일 (END_YMD)
     * @since 2024-08-11
     */
    private LocalDateTime endYmd = LocalDateTime.now();

    /***
     * @author 140024
     * @implNote 대상기간 (YMD)
     * @since 2024-09-12
     */
    private String Period;

    /***
     * @author 140024
     * @implNote 행번(140024) (EMP_ID)
     * @since 2024-09-12
     */
    private Long username;

    /***
     * @author 140024
     * @implNote 행번(7140024) (EMP_NO)
     * @since 2024-09-12
     */
    private String empNo;

    /***
     * @author 140024
     * @implNote 이름 (EMP_NM)
     * @since 2024-09-12
     */
    private String realname;

    /***
     * @author 140024
     * @implNote 부서명 (ORG_NM)
     * @since 2024-09-12
     */
    private String deptName;

    /***
     * @author 140024
     * @implNote 직위명 (행원, 대리, 과장, 차장...) (POS_GRD_NM)
     * @since 2024-09-12
     */
    private String positionName;

    /***
     * @author 140024
     * @implNote 직책명 (팀원, 팀장...) (DUTY_NM)
     * @since 2024-09-12
     */
    private String dutyName;

    /***
     * @author 140024
     * @implNote 연차발생일수(TOT_YY)
     * @since 2024-09-11
     */
    private Float annualDayTotalCnt;

    /***
     * @author 140024
     * @implNote 연차발생시간(TOT_HH)
     * @since 2024-09-11
     */
    private Float annualHourTotalCnt;

    /***
     * @author 140024
     * @implNote 연차사용일수(TOT_USED)
     * @since 2024-09-11
     */
    private Float annualDayUsedCnt;

    /***
     * @author 140024
     * @implNote 연차사용시간(TOT_USED_HH)
     * @since 2024-09-11
     */
    private Float annualHourUsedCnt;

    /***
     * @author 140024
     * @implNote 연차잔여일수(TOT_REMAIN)
     * @since 2024-09-11
     */
    private Float annualDayRemainCnt;

    /***
     * @author 140024
     * @implNote 연차잔여시간(TOT_REMAIN_HH)
     * @since 2024-09-11
     */
    private Float annualHourRemainCnt;

    /***
     * @author 140024
     * @implNote 의무사용연차일수(ANNUAL_CNT)
     * @since 2024-09-11
     */
    private Float dutyAnnualDayTotalCnt;

    /***
     * @author 140024
     * @implNote 의무사용연차일수(ANNUAL_CNT_HH)
     * @since 2024-09-11
     */
    private Float dutyAnnualHourTotalCnt;

    /***
     * @author 140024
     * @implNote 사용시기지정일수 (REMAIN_CNT2)
     * @since 2024-09-11
     */
    private Float fixedDutyAnnualDayTotalCnt;

    /***
     * @author 140024
     * @implNote 사용시기지정시간 (REMAIN_CNT2_HH)
     * @since 2024-09-11
     */
    private Float fixedDutyAnnualHourTotalCnt;

    /***
     * @author 140024
     * @implNote 오늘날짜 (NOW_DATE)
     * @since 2024-09-11
     */
    private String todayYmd;

    /***
     * @author 140024
     * @implNote 인사부장 이름 (CHEIF_EMP_NM)
     * @since 2024-09-11
     */
    private String headHrDept;

    /***
     * @author 140024
     * @implNote 촉진동의서 ID (DTM_YM_REST_ID)
     * @since 2024-09-11
     */
    private String dtmYmRestId;

        /***
     * @author 140024
     * @implNote 취소중인 근태일수 (YYCNT_DEL_USING_CNT)
     * @since 2024-09-11
     */
    private Float ingRevokeDayCnt;

        /***
     * @author 140024
     * @implNote 취소중인 근태시간 (HHCNT_DEL_USING_CNT)
     * @since 2024-09-11
     */
    private Float ingRevokeHourCnt;


}
