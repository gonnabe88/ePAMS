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

    private String apprKind; // 요청승인구분
    private Long applId; // 신청서ID
    private int ordNo; // 결재순번
    private Long apprEmpId; // 결재직원ID
    private String apprUsergroupId; // 결재자역할ID
    private String apprCd; // 결재코드
    private LocalDateTime apprDate; // 결재일시
    private String apprInfo; // 결재자정보
    private String refusalReason; // 반려사유
    private String note; // 비고
    private Long modUserId; // 변경자
    private LocalDateTime modDate; // 변경일시
    private String tzCd = "KO"; // 타임존코드
    private LocalDateTime tzDate; // 타임존일시

}
