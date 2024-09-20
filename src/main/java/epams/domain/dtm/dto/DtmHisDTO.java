package epams.domain.dtm.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DtmHisDTO extends DtmHisVO {

    /***
     * @author 140024
     * @implNote 자정 시간
     * @since 2024-06-09
     */
    private final static String midnight = "2400";

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
     * @implNote 경과여부 (과거/진행중/예정) 세팅
     * @since 2024-06-09
     * @param staYmd 시작일
     * @param staHm 시작시간
     * @param endYmd 종료일
     * @param endHm 종료시간
     * @return String 타입의 예정, 과거, 진행
     */
    public final String calculateStatus(final LocalDateTime staYmd, final String staHm, final LocalDateTime endYmd, final String endHm) {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime startDateTime = LocalDateTime.of(staYmd.toLocalDate(), LocalTime.parse(staHm, DateTimeFormatter.ofPattern("HHmm")));
        final LocalDateTime endDateTime;
        if (midnight.equals(endHm)) {
            // endHm이 2400이면 +1일 00시로 설정
            endDateTime = LocalDateTime.of(endYmd.toLocalDate().plusDays(1), LocalTime.MIDNIGHT);
        } else {
            endDateTime = LocalDateTime.of(endYmd.toLocalDate(), LocalTime.parse(endHm, DateTimeFormatter.ofPattern("HHmm")));
        }

        if (now.isBefore(startDateTime)) {
            this.status = "예정";
        } else if (now.isAfter(endDateTime)) {
        	this.status = "과거";
        } else {
        	this.status = "진행";
        }
        
        return status;
    }
}
