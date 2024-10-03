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
     * @implNote 관리자 여부
     * @since 2024-06-09
     */
    private Boolean isAdmin;

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
    private String staTime = "09:00";


    /**
     * @author 140024
     * @implNote 근무종료시간
     * @since 2024-08-19
     */
    private String endTime = "18:00";

        /**
     * @author 140024
     * @implNote 근무시작시간(내일)
     * @since 2024-08-19
     */
    private String staTime2 = "09:00";


    /**
     * @author 140024
     * @implNote 근무종료시간(내일)
     * @since 2024-08-19
     */
    private String endTime2 = "18:00";

    public void setTime(String staTime, String endTime, String staTime2, String endTime2) {
        if(!"N/A".equals(staTime) && !"N/A".equals(endTime) && staTime.length() == 4 && endTime.length() == 4 ) {
            this.staTime = staTime.substring(0, 2) + ":" + staTime.substring(2, 4);
            this.endTime = endTime.substring(0, 2) + ":" + endTime.substring(2, 4);
        }
        if(!"N/A".equals(staTime2) && !"N/A".equals(endTime2) && staTime2.length() == 4 && endTime2.length() == 4 ) {
            this.staTime2 = staTime2.substring(0, 2) + ":" + staTime2.substring(2, 4);
            this.endTime2 = endTime2.substring(0, 2) + ":" + endTime2.substring(2, 4);
        }
    }

}
