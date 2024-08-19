package epams.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

/**
 * @author 140024
 * @implNote 게시판 테이블 정의 entity
 * @since 2024-08-19
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "THURXE_CORGLM")
@Comment("인사_외부근태 공통조직기본")
public class IamOrgEntity extends BaseEntity {

    /**
     * @author 140024
     * @implNote 인사조직코드내용
     * @since 2024-08-19
     */
    @Id 
    @Column(name = "PRLM_OGZ_C_CONE", length = 100, nullable = false)
    @Comment("인사조직코드내용")
    private String PRLM_OGZ_C_CONE;
    
    /**
     * @author 140024
     * @implNote 인사상위조직코드내용
     * @since 2024-08-19
     */
    @Column(name = "PRLM_HRK_OGZ_C_CONE", length = 100)
    @Comment("인사상위조직코드내용")
    private String PRLM_HRK_OGZ_C_CONE;
    
    /**
     * @author 140024
     * @implNote 부점명
     * @since 2024-08-19
     */
    @Column(name = "BBR_NM", length = 100)
    @Comment("부점명")
    private String BBR_NM;

    /**
     * @author 140024
     * @implNote 부점영문명
     * @since 2024-08-19
     */
    @Column(name = "BBR_WREN_NM",  length = 100)
    @Comment("부점영문명")
    private String BBR_WREN_NM;

    /**
     * @author 140024
     * @implNote 항목순서일련번호
     * @since 2024-08-19
     */
    @Column(name = "ITM_SQN_SNO", length = 9)
    @Comment("항목순서일련번호")
    private String ITM_SQN_SNO;

    /**
     * @author 140024
     * @implNote 사용여부
     * @since 2024-08-19
     */
    @Column(name = "USE_YN", length = 1)
    @Comment("사용여부")
    private String USE_YN;

}
