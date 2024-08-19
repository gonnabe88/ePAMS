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
@Table(name = "THURXE_CATHLM")
@Comment("인사_외부근태 공통자격등급기본")
public class IamAuthEntity extends BaseEntity {

    /**
     * @author 140024
     * @implNote 권한ID
     * @since 2024-08-19
     */
    @Id 
    @Column(name = "ATH_ID", length = 32, nullable = false)
    @Comment("권한ID")
    private String ATH_ID;
    
    /**
     * @author 140024
     * @implNote 자격등급명
     * @since 2024-08-19
     */
    @Column(name = "QLF_GR_NM", length = 200)
    @Comment("자격등급명")
    private String QLF_GR_NM;
    
    /**
     * @author 140024
     * @implNote 자격등급사항(내용)
     * @since 2024-08-19
     */
    @Column(name = "QLF_GR_MAT", length = 600)
    @Comment("자격등급사항(내용)")
    private String QLF_GR_MAT;

    /**
     * @author 140024
     * @implNote 사용여부
     * @since 2024-08-19
     */
    @Column(name = "USE_YN", length = 1)
    @Comment("사용여부")
    private String USE_YN;

}
