package epams.domain.dtm.dto;

import lombok.*;

import java.time.LocalDate;

/***
 * @author 210058
 * @implNote  근태 신청 테이블 정보
 * @since 2024-08-30
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Builder
public class DtmRegDTO {


//    /***
//     * @author 210058
//     * @implNote
//     * @since 2024-08-30
//     */
//    private String applDateType;
//
//    /***
//     * @author 210058
//     * @implNote 시작날짜 문자열
//     * @since 2024-08-30
//     */
//    private String staDateStr;
//
//    /***
//     * @author 210058
//     * @implNote 종료날짜 문자열
//     * @since 2024-08-30
//     */
//    private String endDateStr;

    /***
     * @author 210058
     * @implNote 시작날짜
     * @since 2024-08-30
     */
    private LocalDate staDate;

    /***
     * @author 210058
     * @implNote 종료날짜
     * @since 2024-08-30
     */
    private LocalDate endDate;

    /***
     * @author 140024
     * @implNote 근태코드
     * @since 2024-06-09
     */
    private String dtmCode;

    /***
     * @author 140024
     * @implNote 근태종류
     * @since 2024-06-09
     */
    private String dtmReasonCd;

    /***
     * @author 140024
     * @implNote 근태명
     * @since 2024-06-09
     */
    private String dtmDispName;

}