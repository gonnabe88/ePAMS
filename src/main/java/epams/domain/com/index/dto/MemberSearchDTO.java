package epams.domain.com.index.dto;

import epams.model.vo.IamUserVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/***
 * @author 140024
 * @implNote 빠른신청 데이터 정의 DTO
 * @since 2024-06-09
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class MemberSearchDTO extends IamUserVO {

    /***
     * @author 140024
     * @implNote 부서명
     * @since 2024-06-09
     */
    private String deptName;

    /***
     * @author 140024
     * @implNote 파견부서명
     * @since 2024-06-09
     */
    private String dispatchDeptName;

}
