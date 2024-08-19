package epams.model.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/***
 * @author 140024
 * @implNote 코드 테이블 정의 entity
 * @since 2024-06-09
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "THURXE_CLANGM")
@Comment("인사_외부근태 언어기본")
public class LangEntity extends BaseEntity implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1909895141704463760L;

	/***
     * @author 140024
     * @implNote 공통코드
     * @since 2024-06-09
     */
    @Id // 기본키
    @Column(name="LAN_ID", length = 40)
    @Comment("언어ID")
    private String LAN_ID;
    
    /***
     * @author 140024
     * @implNote 코드값명
     * @since 2024-06-09
     */
    @Column(name="LAN_NM", length = 200, nullable = false)
    @Comment("언어명")
    private String LAN_NM;

    /***
     * @author 140024
     * @implNote 코드형식
     * @since 2024-06-09
     */
    @Column(name="LAN_KD_NM", length = 200, nullable = false) // 크기 20, not null
    @Comment("언어종류명")
    private String LAN_KD_NM;
    
    /***
     * @author 140024
     * @implNote 테이블 조인
     * @since 2024-06-09
     */
    @ManyToMany
    @JoinTable(
        name = "THURXE_CHTMLA",
        joinColumns = @JoinColumn(name = "LAN_ID", foreignKey = @ForeignKey(name = "FK_THURXE_CHTMLA_LAN_ID")),
        inverseJoinColumns = @JoinColumn(name = "HTML_FL_PTH", foreignKey = @ForeignKey(name = "FK_THURXE_CHTMLA_HTML_FL_PTH"))
    )
    
    /***
     * @author 140024
     * @implNote htmlEntities 빌더
     * @since 2024-06-09
     */
    @Builder.Default
    private final Set<HtmlEntity> htmlEntities = new HashSet<>();
    
}