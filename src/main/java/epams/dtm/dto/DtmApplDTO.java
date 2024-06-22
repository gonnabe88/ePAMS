package epams.dtm.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/***
 * @author 140024
 * @implNote (Table = "OHR.DTM_APPL") 코드 테이블 정의 entity
 * @since 2024-06-09
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DtmApplDTO {

    /***
     * @author 140024
     * @implNote 근태신청서ID
     * @since 2024-06-09
     */
    private String applID;

        /***
     * @author 140024
     * @implNote 사용자ID
     * @since 2024-06-09
     */
    private String empId;

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

    /***
     * @author 140024
     * @implNote 근태시작일
     * @since 2024-06-09
     */
    private LocalDateTime startDate;

     /***
     * @author 140024
     * @implNote 근태종료일
     * @since 2024-06-09
     */
    private LocalDateTime endDate;

    /***
     * @author 140024
     * @implNote 근태신청일자
     * @since 2024-06-09
     */
    private LocalDateTime applDate;   
    
}