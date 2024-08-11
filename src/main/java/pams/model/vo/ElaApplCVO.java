package pams.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/***
 * @author 140024
 * @implNote 근태 이력 테이블 정의 VO
 * @since 2024-06-09
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ElaApplCVO {

    /***
     * @author 140024
     * @implNote 신청서ID (시퀀스)
     * @since 2024-08-11
     */
    private Long applId;

    /***
     * @author 140024
     * @implNote 신청서종류코드
     * @since 2024-08-11
     */
    private String applCd = "DTM01";

    /***
     * @author 140024
     * @implNote 직원ID
     * @since 2024-08-11
     */
    private Long empId;

    /***
     * @author 140024
     * @implNote 승인신청일시 (SYSDATE)
     * @since 2024-08-11
     */
    private LocalDateTime applDate;

    /***
     * @author 140024
     * @implNote 작성자ID
     * @since 2024-08-11
     */
    private Long makeEmpId;

    /***
     * @author 140024
     * @implNote 신청서상태코드
     * @since 2024-08-11
     */
    private String statCd = "121";

    /***
     * @author 140024
     * @implNote 강제반려여부 (기본값 0)
     * @since 2024-08-11
     */
    private String forceCancelYn = "0";

    /***
     * @author 140024
     * @implNote 비고
     * @since 2024-08-11
     */
    private String note;

    /***
     * @author 140024
     * @implNote 변경자
     * @since 2024-08-11
     */
    private Long modUserId;

    /***
     * @author 140024
     * @implNote 변경일시 (SYSDATE)
     * @since 2024-08-11
     */
    private LocalDateTime modDate;

    /***
     * @author 140024
     * @implNote 타임존코드
     * @since 2024-08-11
     */
    private String tzCd = "KST";

    /***
     * @author 140024
     * @implNote 타임존일시 (SYSDATE)
     * @since 2024-08-11
     */
    private LocalDateTime tzDate;

}
