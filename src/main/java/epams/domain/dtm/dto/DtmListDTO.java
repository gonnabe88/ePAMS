package epams.domain.dtm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/***
 * @author 140024
 * @implNote 빠른신청 데이터 정의 DTO
 * @since 2024-06-09
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DtmListDTO {

    /***
     * @author 140024
     * @implNote 신청날짜구분
     * @since 2024-06-09
     */
    private String applDateType;

    /***
     * @author 140024
     * @implNote 시작날짜 문자열
     * @since 2024-06-09
     */
    private String staYmdStr;

    /***
     * @author 140024
     * @implNote 종료날짜 문자열
     * @since 2024-06-09
     */
    private String endYmdStr;

    /***
     * @author 140024
     * @implNote 시작날짜
     * @since 2024-06-09
     */
    private LocalDate staYmd;

    /***
     * @author 140024
     * @implNote 종료날짜
     * @since 2024-06-09
     */
    private LocalDate endYmd;

    /***
     * @author 140024
     * @implNote 근태코드
     * @since 2024-06-09
     */
    private String dtmCode;

    /***
     * @author 140024
     * @implNote 근태명
     * @since 2024-06-09
     */
    private String dtmName;
}
