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

    private Long applId; // 신청서ID
    private String applCd; // 신청서종류코드
    private Long empId; // 직원ID
    private LocalDateTime applDate; // 승인신청일시
    private Long makeEmpId; // 작성자ID
    private String statCd; // 신청서상태코드
    private String forceCancelYn = "0"; // 강제반려여부 (기본값 0)
    private String note; // 비고
    private Long modUserId; // 변경자
    private LocalDateTime modDate; // 변경일시
    private String tzCd; // 타임존코드
    private LocalDateTime tzDate; // 타임존일시

}
