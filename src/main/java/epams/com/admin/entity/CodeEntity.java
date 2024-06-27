package epams.com.admin.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Comment;

import epams.com.board.entity.BaseEntity;
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
@Table(name = "THURXE_CCODEM")
@Comment("인사_외부근태 공통코드기본")
public class CodeEntity extends BaseEntity implements Serializable {
	
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
    @Column(length = 128)
    @Comment("공통코드식별번호")
    private String CD_ID;
    
    /***
     * @author 140024
     * @implNote 코드명
     * @since 2024-06-09
     */
    @Column(length = 500, nullable = false)
    @Comment("공통코드명칭")
    private String CD_NM;

    /***
     * @author 140024
     * @implNote 코드형식
     * @since 2024-06-09
     */
    @Column(length = 20, nullable = false) // 크기 20, not null
    @Comment("공통코드타입")
    private String CD_TYPE;
    
    /***
     * @author 140024
     * @implNote 테이블 조인
     * @since 2024-06-09
     */
    @ManyToMany
    @JoinTable(
        name = "THURXE_CCDHTR",
        joinColumns = @JoinColumn(name = "CD_ID", foreignKey = @ForeignKey(name = "FK_THURXE_CCDHTR_CD_ID")),
        inverseJoinColumns = @JoinColumn(name = "HTML", foreignKey = @ForeignKey(name = "FK_THURXE_CCDHTR_HTML"))
    )
    
    /***
     * @author 140024
     * @implNote htmlEntities 빌더
     * @since 2024-06-09
     */
    @Builder.Default
    private final Set<HtmlEntity> htmlEntities = new HashSet<>();
    
}