package epams.domain.com.apply.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pams.model.vo.ElaApplCVO;

import java.time.LocalDateTime;

/***
 * @author 140024
 * @implNote 신청서 데이터 정의 DTO
 * @since 2024-06-09
 */
@Slf4j
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class ElaApplCDTO extends ElaApplCVO {

    /**
     * @author 140024
     * @implNote empId, applCd, statCd를 받아서 생성하는 생성자
     * @since 2024-08-04
     */
    public ElaApplCDTO(Long empId, String applCd, String statCd) {
        super(); // 부모 클래스의 기본 생성자 호출

        this.setEmpId(empId); // 직원ID
        this.setMakeEmpId(empId); // 작성자ID
        this.setModUserId(empId); // 변경자
        this.setApplCd(applCd != null ? applCd : "DTM01"); // 신청서종류코드(기본값 : DTM01)
        this.setStatCd(statCd != null ? statCd : "121");   // 신청서상태코드 (기본값 : 121)
    }
}
