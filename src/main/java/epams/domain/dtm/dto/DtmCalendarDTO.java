package epams.domain.dtm.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import pams.model.vo.DtmHisVO;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/***
 * @author 140024
 * @implNote 근태 이력 데이터 정의 DTO
 * @since 2024-06-09
 */
@Slf4j
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DtmCalendarDTO{

    /***
     * @author 140024
     * @implNote 근태사유 (dtmReasonNm)
     * @since 2024-08-11
     */
    private String title;

    /***
     * @author 140024
     * @implNote 시작일 (staYmd)
     * @since 2024-08-11
     */
    private LocalDateTime start;

    /***
     * @author 140024
     * @implNote 종료일 (endYmd)
     * @since 2024-08-11
     */
    private LocalDateTime end;

    /***
     * @author 140024
     * @implNote 종료일 (endYmd)
     * @since 2024-08-11
     */
    private Boolean allDay = true;
}
