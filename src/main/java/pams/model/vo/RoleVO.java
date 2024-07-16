package pams.model.vo;

import epams.model.vo.BaseVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/***
 * @author 140024
 * @implNote 사용자
 * @since 2024-06-30
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleVO extends BaseVO {

    /***
     * @author 140024
     * @implNote [Column] 사원번호(ENO)
     * @since 2024-06-30
     */
    private String username;

    /***
     * @author 140024
     * @implNote [Column] 권한ID(ROLE_ID)
     * @implSpec 외래키
     * @since 2024-06-30
     */
    private String roleId = "ROLE_ADMIN";

}
