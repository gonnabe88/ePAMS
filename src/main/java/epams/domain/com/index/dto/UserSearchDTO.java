package epams.domain.com.index.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * @author 140024
 * @implNote 사용자 검색 데이터 정의 DTO
 * @since 2024-06-09
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserSearchDTO {
    /**
     * @author 140024
     * @implNote 직원명
     * @since 2024-06-09
     */
    private String realname;

    /**
     * @author 140024
     * @implNote 사원번호
     * @since 2024-06-09
     */
    private String username;

    /**
     * @author 140024
     * @implNote 인사조직코드내용
     * @since 2024-08-19
     */
    private String deptCode;

    /**
     * @author 140024
     * @implNote 부점명
     * @since 2024-08-19
     */
    private String deptName;

    /**
     * @author 140024
     * @implNote 팀코드
     * @since 2024-06-09
     */
    private String teamCode;

    /**
     * @author 140024
     * @implNote 팀명
     * @since 2024-08-19
     */
    private String teamName;

    /**
     * @author 140024
     * @implNote 직위코드명
     * @since 2024-08-19
     */
    private String positionName;

    /**
     * @author 140024
     * @implNote 직무상세내용
     * @since 2024-08-19
     */
    private String jobDetail;

    /**
     * @author 140024
     * @implNote 내선번호
     * @since 2024-08-19
     */
    private String inlineNo;
}
