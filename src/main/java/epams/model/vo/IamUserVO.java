package epams.model.vo;

import epams.model.entity.BaseEntity;
import lombok.*;

/**
 * @author 140024
 * @implNote 게시판 테이블 정의 entity
 * @since 2024-06-09
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true) // 상위 클래스의 toString 포함
public class IamUserVO extends BaseEntity {

    /**
     * @author 140024
     * @implNote 사원번호
     * @since 2024-08-19
     */
    private String empNo;
    
    /**
     * @author 140024
     * @implNote 직원명
     * @since 2024-06-09
     */
    private String userName;
    
    /**
     * @author 140024
     * @implNote 사용자영문명
     * @since 2024-06-09
     */
    private String userEngName;

    /**
     * @author 140024
     * @implNote 부점코드
     * @since 2024-06-09
     */
    private String deptCode;

    /**
     * @author 140024
     * @implNote 파견부점코드
     * @since 2024-06-09
     */
    private String dispatchDeptCode;

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
     * @implNote 직위코드
     * @since 2024-08-19
     */
    private String positionCode;

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
     * @implNote 전자우편주소명
     * @since 2024-08-19
     */
    private String email;

    /**
     * @author 140024
     * @implNote 내선번호
     * @since 2024-08-19
     */
    private String inlineNumber;

    /**
     * @author 140024
     * @implNote 연락처전화번호
     * @since 2024-08-19
     */
    private String phoneNumber;

    /**
     * @author 140024
     * @implNote 사용여부
     * @since 2024-08-19
     */
    private String useYn;

}
