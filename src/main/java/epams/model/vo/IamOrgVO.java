package epams.model.vo;

import epams.model.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Comment;

/**
 * @author 140024
 * @implNote 게시판 테이블 정의 entity
 * @since 2024-08-19
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true) // 상위 클래스의 toString 포함
public class IamOrgVO extends BaseEntity {

    /**
     * @author 140024
     * @implNote 인사조직코드내용
     * @since 2024-08-19
     */
    private String deptCode;
    
    /**
     * @author 140024
     * @implNote 인사상위조직코드내용
     * @since 2024-08-19
     */
    private String upperDeptCode;
    
    /**
     * @author 140024
     * @implNote 부점명
     * @since 2024-08-19
     */
    private String deptName;

    /**
     * @author 140024
     * @implNote 부점영문명
     * @since 2024-08-19
     */
    private String deptEngName;

    /**
     * @author 140024
     * @implNote 항목순서일련번호
     * @since 2024-08-19
     */
    private String itemSeqNo;

    /**
     * @author 140024
     * @implNote 사용여부
     * @since 2024-08-19
     */
    private String useYn;

}
