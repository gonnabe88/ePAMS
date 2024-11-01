package epams.domain.com.index.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/***
 * @author 140024
 * @implNote 빠른신청 데이터 정의 DTO
 * @since 2024-06-09
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class QuickApplDTO {

    /***
     * @author 140024
     * @implNote 신청날짜구분
     * @since 2024-06-09
     */
    private String applDateType;


    /***
     * @author 140024
     * @implNote 근태코드
     * @since 2024-06-09
     */
    private String dtmKindCd;

    /***
     * @author 140024
     * @implNote 근태종류
     * @since 2024-06-09
     */
    private String dtmReasonCd;

        /***
     * @author 140024
     * @implNote 근태종류명
     * @since 2024-06-09
     */
    private String dtmReasonNm;

    /***
     * @author 140024
     * @implNote 근태표시명
     * @since 2024-06-09
     */
    private String dtmDispName;

    /***
     * @author 140024
     * @implNote 휴일구분
     * @since 2024-09-06
     */
    private String holidayYn;
}
