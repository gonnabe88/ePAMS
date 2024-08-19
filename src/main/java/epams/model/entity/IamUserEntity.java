package epams.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 140024
 * @implNote 게시판 테이블 정의 entity
 * @since 2024-06-09
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "THURXE_CUSRLM")
@Comment("인사_외부근태 공통사용자기본")
public class IamUserEntity extends BaseEntity {

    /**
     * @author 140024
     * @implNote 사원번호
     * @since 2024-08-19
     */
    @Id 
    @Column(name = "ENO", length = 32, nullable = false)
    @Comment("사원번호")
    private String ENO;
    
    /**
     * @author 140024
     * @implNote 직원명
     * @since 2024-06-09
     */
    @Column(name = "USR_NM", length = 100)
    @Comment("직원명")
    private String USR_NM;
    
    /**
     * @author 140024
     * @implNote 사용자영문명
     * @since 2024-06-09
     */
    @Column(name = "USR_WREN_NM", length = 100)
    @Comment("사용자영문명")
    private String USR_WREN_NM;

    /**
     * @author 140024
     * @implNote 부점코드
     * @since 2024-06-09
     */
    @Column(name = "BBR_C",  length = 3)
    @Comment("부점코드")
    private String BBR_C;

    /**
     * @author 140024
     * @implNote 파견부점코드
     * @since 2024-06-09
     */
    @Column(name = "DTC_BBR_C", length = 3)
    @Comment("파견부점코드")
    private String DTC_BBR_C;

    /**
     * @author 140024
     * @implNote 팀코드
     * @since 2024-06-09
     */
    @Column(name = "TEM_C", length = 5)
    @Comment("팀코드")
    private String TEM_C;

    /**
     * @author 140024
     * @implNote 팀명
     * @since 2024-08-19
     */
    @Column(name = "TEM_NM", length = 100)
    @Comment("팀명")
    private String TEM_NM;

    /**
     * @author 140024
     * @implNote 직위코드
     * @since 2024-08-19
     */
    @Column(name = "PT_C", length = 5)
    @Comment("직위코드")
    private String PT_C;


    /**
     * @author 140024
     * @implNote 직위코드명
     * @since 2024-08-19
     */
    @Column(name = "PT_C_NM", length = 200)
    @Comment("직위코드명")
    private String PT_C_NM;

    /**
     * @author 140024
     * @implNote 직무상세내용
     * @since 2024-08-19
     */
    @Column(name = "DTS_DTL_CONE", length = 2000)
    @Comment("직무상세내용")
    private String DTS_DTL_CONE;

    /**
     * @author 140024
     * @implNote 전자우편주소명
     * @since 2024-08-19
     */
    @Column(name = "ETR_MIL_ADDR_NM", length = 200)
    @Comment("전자우편주소명")
    private String ETR_MIL_ADDR_NM;

    /**
     * @author 140024
     * @implNote 내선번호
     * @since 2024-08-19
     */
    @Column(name = "INLE_NO", length = 20)
    @Comment("내선번호")
    private String INLE_NO;

    /**
     * @author 140024
     * @implNote 연락처전화번호
     * @since 2024-08-19
     */
    @Column(name = "CADR_TPN", length = 20)
    @Comment("연락처전화번호")
    private String CADR_TPN;

    /**
     * @author 140024
     * @implNote 사용여부
     * @since 2024-08-19
     */
    @Column(name = "USE_YN", length = 1)
    @Comment("사용여부")
    private String USE_YN;

}
