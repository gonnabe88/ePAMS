package epams.model.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import lombok.*;
import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

/***
 * @author 140024
 * @implNote 코드 테이블 정의 entity
 * @since 2024-06-09
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@Builder
@Table(name = "THURXE_CCODEM")
@Comment("인사_외부근태 언어기본")
public class LangEntity extends BaseEntity implements Serializable {
	
    /**
	 * 명시적 serialVersionUID 설정
	 */
	private static final long serialVersionUID = 3L;

	/***
     * @author 140024
     * @implNote 공통코드
     * @since 2024-06-09
     */
    @Id // 기본키
    @Column(name="SRE_VCB_CDVA_ID", length = 40)
    @Comment("화면용어코드값ID")
    private String SRE_VCB_CDVA_ID;
    
    /***
     * @author 140024
     * @implNote 코드값명
     * @since 2024-06-09
     */
    @Column(name="SRE_VCB_NM", length = 200, nullable = false)
    @Comment("화면용어명")
    private String SRE_VCB_NM;

    /***
     * @author 140024
     * @implNote 코드형식
     * @since 2024-06-09
     */
    @Column(name="SRE_VCB_KD_NM", length = 200, nullable = false) // 크기 20, not null
    @Comment("화면용어종류명")
    private String SRE_VCB_KD_NM;
    
    /***
     * @author 140024
     * @implNote 테이블 조인
     * @since 2024-06-09
     */
    @ManyToMany
    @JoinTable(
        name = "OEHR.THURXE_CCDHTA",
        joinColumns = @JoinColumn(name = "SRE_VCB_CDVA_ID", foreignKey = @ForeignKey(name = "FK_THURXE_CCDHTA_SRE_VCB_CDVA_ID")),
        inverseJoinColumns = @JoinColumn(name = "HTML_FL_PTH", foreignKey = @ForeignKey(name = "FK_THURXE_CCDHTA_HTML_FL_PTH"))
    )
    
    /***
     * @author 140024
     * @implNote htmlEntities 빌더
     * @since 2024-06-09
     */
    @Builder.Default
    private final Set<HtmlEntity> htmlEntities = new HashSet<>();
    
}