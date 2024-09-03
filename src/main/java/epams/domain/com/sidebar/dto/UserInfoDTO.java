package epams.domain.com.sidebar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * @author 140024
 * @implNote 빠른신청 데이터 정의 DTO
 * @since 2024-06-09
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserInfoDTO {

    /**
     * @author 140024
     * @implNote 사원번호
     * @since 2024-06-09
     */
    private String username;

    /**
     * @author 140024
     * @implNote 직원명
     * @since 2024-06-09
     */
    private String realname = "N/A";

    /**
     * @author 140024
     * @implNote 부점명
     * @since 2024-08-19
     */
    private String deptName = "N/A";

        /**
     * @author 140024
     * @implNote 팀명
     * @since 2024-08-19
     */
    private String teamName = "N/A";

    /**
     * @author 140024
     * @implNote 직위코드명
     * @since 2024-08-19
     */
    private String positionName = "N/A";

    /**
     * @author 140024
     * @implNote 근무시작시간
     * @since 2024-08-19
     */
    private String staTime = "10:00";


    /**
     * @author 140024
     * @implNote 근무종료시간
     * @since 2024-08-19
     */
    private String endTime = "19:00";

}
