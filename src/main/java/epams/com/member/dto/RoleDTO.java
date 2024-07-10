package epams.com.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import epams.com.board.dto.BaseDTO;
import epams.com.member.entity.RoleEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/***
 * @author 140024
 * @implNote 사용자
 * @since 2024-06-30
 */
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
public class RoleDTO  extends BaseDTO {

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
