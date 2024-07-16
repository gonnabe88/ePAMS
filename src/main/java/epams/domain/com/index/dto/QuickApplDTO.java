package epams.domain.com.index.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
     * @implNote 신청날짜
     * @since 2024-06-09
     */
    private String applDate;

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
