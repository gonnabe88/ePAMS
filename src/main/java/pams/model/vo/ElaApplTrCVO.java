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
public class ElaApplTrCVO {

    /***
     * @author 140024
     * @implNote 요청승인구분
     * @since 2024-08-11
     */
    private String apprKind;

    /***
     * @author 140024
     * @implNote 신청서ID
     * @since 2024-08-11
     */
    private Long applId;

    /***
     * @author 140024
     * @implNote 결재순번
     * @since 2024-08-11
     */
    private int ordNo;

    /***
     * @author 140024
     * @implNote 결재직원ID
     * @since 2024-08-11
     */
    private Long apprEmpId;

    /***
     * @author 140024
     * @implNote 결재자역할ID
     * @since 2024-08-11
     */
    private String apprUsergroupId;

    /***
     * @author 140024
     * @implNote 결재코드
     * @since 2024-08-11
     */
    private String apprCd;

    /***
     * @author 140024
     * @implNote 결재일시
     * @since 2024-08-11
     */
    private LocalDateTime apprDate;

    /***
     * @author 140024
     * @implNote 결재자정보
     * @since 2024-08-11
     */
    private String apprInfo;

    /***
     * @author 140024
     * @implNote 반려사유
     * @since 2024-08-11
     */
    private String refusalReason;

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
     * @implNote 변경일시
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
     * @implNote 타임존일시
     * @since 2024-08-11
     */
    private LocalDateTime tzDate;

}
