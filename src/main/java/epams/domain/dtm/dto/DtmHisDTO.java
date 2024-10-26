package epams.domain.dtm.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import jakarta.validation.OverridesAttribute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import pams.model.vo.DtmHisVO;

/***
 * @author 140024
 * @implNote 근태 이력 데이터 정의 DTO
 * @since 2024-06-09
 */
@Slf4j
@ToString(callSuper = true)
@EqualsAndHashCode(of = {"empId", "dtmReasonCd", "staDate", "endDate"}, callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DtmHisDTO extends DtmHisVO {

    /***
     * @author 140024
     * @implNote 교차사용 가능여부
     * @since 2024-06-09
     */
    private String dtmCross;

    /***
     * @author 140024
     * @implNote 기본근무 시작시간
     * @since 2024-06-09
     */
    private String baseStaHm;

        /***
     * @author 140024
     * @implNote 기본근무 종료시간
     * @since 2024-06-09
     */
    private String baseEndHm;

    /***
     * @author 140024
     * @implNote 취소 가능여부
     * @since 2024-09-22
     */
    private Boolean isRevocable;
    
    /***
     * @author 140024
     * @implNote 수정 가능여부
     * @since 2024-09-22
     */
    private Boolean isModifiable;

    /***
     * @author 140024
     * @implNote 화면에 표시할 근태명
     * @since 2024-06-09
     */
    private String dtmRange;


    /***
     * @author 140024
     * @implNote 화면에 표시할 근태명
     * @since 2024-06-09
     */
    private String dtmDispName;

    /***
     * @author 140024
     * @implNote 결재상태 (결재중/결재완료/반려)
     * @since 2024-06-09
     */
    private String statCdNm;

    /***
     * @author 140024
     * @implNote 경과여부 (과거/진행중/예정)
     * @since 2024-06-09
     */
    private String status;

    /***
     * @author 140024
     * @implNote 근태명 (연차/반차/반반차..)
     * @since 2024-06-09
     */
    private String dtmReasonNm;

    /***
     * @author 140024
     * @implNote 근태종류 (연차/보상..)
     * @since 2024-06-09
     */
    private String dtmKindNm;

        /***
     * @author 140024
     * @implNote 근태시작일(Localdate)
     * @since 2024-06-09
     */
    private LocalDate staDate;

        /***
     * @author 140024
     * @implNote 근태종료일(Localdate)
     * @since 2024-06-09
     */
    private LocalDate endDate;

    /***
     * @author 140024
     * @implNote 근태시작일자시간
     * @since 2024-09-28
     * @return LocalDateTime
     */
    /* @TODO 외부 테스트 시 주석 처리(시작) */
    public LocalDateTime getStartDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
        LocalTime time = LocalTime.parse(super.getStaHm(), formatter);
        return LocalDateTime.of(super.getStaYmd().toLocalDate(), time);
    }

    public LocalDateTime getBaseStartDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
        LocalTime time = LocalTime.parse(this.getBaseStaHm(), formatter);
        return LocalDateTime.of(super.getStaYmd().toLocalDate(), time);
    }
    
     /* @TODO 외부 테스트 시 주석 처리(끝) */

    /***
     * @author 140024
     * @implNote 근태종료일자시간
     * @since 2024-09-28
     * @return LocalDateTime
     */
    /* @TODO 외부 테스트 시 주석 처리(시작) */
    public LocalDateTime getEndDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
        LocalTime time = LocalTime.parse(super.getEndHm(), formatter);
        return LocalDateTime.of(super.getEndYmd().toLocalDate(), time);
    }

    public LocalDateTime getBaseEndDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
        LocalTime time = LocalTime.parse(this.getBaseEndHm(), formatter);
        return LocalDateTime.of(super.getEndYmd().toLocalDate(), time);
    }

    /* @TODO 외부 테스트 시 주석 처리(끝) */
    
    /***
     * @author 140024
     * @implNote 경과여부 (과거/진행중/예정) 세팅
     * @since 2024-06-09
     * @param staYmd 시작일
     * @param staHm 시작시간
     * @param endYmd 종료일
     * @param endHm 종료시간
     * @return String 타입의 예정, 과거, 진행
     */
    public final void updateStatus(final String statCd, final String dtmKindCd, final LocalDateTime staYmd, final String staHm, final LocalDateTime endYmd, final String endHm) {
        final LocalDate today = LocalDate.now();  // 현재 날짜
        final LocalDate startDate = staYmd.toLocalDate();  // 시작 날짜
        final LocalDateTime startDateTime = LocalDateTime.of(staYmd.toLocalDate(), LocalTime.parse("0000", DateTimeFormatter.ofPattern("HHmm")));
        final LocalDateTime endDateTime = LocalDateTime.of(endYmd.toLocalDate(), LocalTime.parse("2359", DateTimeFormatter.ofPattern("HHmm")));

        // 상태(status) 결정
        if (LocalDateTime.now().isBefore(startDateTime)) {
            this.status = "예정";  // 현재 시간이 시작 전이면 '예정'
        } else if (LocalDateTime.now().isAfter(endDateTime)) {
            this.status = "과거";  // 현재 시간이 종료 후면 '과거'
        } else {
            this.status = "진행";  // 현재 시간이 시작과 종료 사이면 '진행'
        }

        // 취소 가능 여부 결정 (오늘 이후, 결재완료, 연차/보상만 가능)
        if ("132".equals(statCd) &&
            ("1A".equals(dtmKindCd) || "1Z".equals(dtmKindCd)) &&
            (startDate.isAfter(today) || startDate.isEqual(today))) {
            this.isRevocable = true;
        } else {
            this.isRevocable = false;
        }

        // 수정 가능 여부 결정 (오늘 이후, 결재완료, 연차/보상만 가능)
        if ("132".equals(statCd) &&
            ("1A".equals(dtmKindCd) || "1Z".equals(dtmKindCd)) &&
            (startDate.isAfter(today) || startDate.isEqual(today))) {
            this.isModifiable = true;
        } else {
            this.isModifiable = false;
        }
    }
}
