package epams.domain.com.apply.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pams.model.vo.ElaApplTrCVO;

/***
 * @author 140024
 * @implNote 신청서결재내역 데이터 정의 DTO
 * @since 2024-08-04
 */
@Slf4j
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class ElaApplTrCDTO extends ElaApplTrCVO {

    /**
     * @author 140024
     * @implNote 생성자
     * @since 2024-08-04
     */
    public ElaApplTrCDTO(Long empId, String apprKind, Long applId, Integer ordNo, String apprCd) {
        super(); // 부모 클래스의 기본 생성자 호출

        this.setApprKind(apprKind); // 요청승인구분
        this.setApplId(applId); // 신청서ID
        this.setOrdNo(ordNo); // 결재순번
        this.setApprEmpId(empId); // 결재직원ID
        this.setModUserId(empId); // 변경자
        this.setApprCd(apprCd);
    }
}
