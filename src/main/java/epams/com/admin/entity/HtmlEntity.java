package epams.com.admin.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Comment;

import epams.com.board.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/***
 * @author 140024
 * @implNote HTML 테이블 정의 entity
 * @since 2024-06-09
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "THURXE_CHTMLM")
@Comment("인사_외부근태 HTML화면기본")
public class HtmlEntity extends BaseEntity implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7682177015095971108L;

	/***
     * @author 140024
     * @implNote HTML 경로
     * @since 2024-06-09
     */
    @Id // 기본키
    @Column(name="HTML_PTH_NM", length = 100)
    @Comment("HTML경로명")
    private String HTML_PTH_NM;

    /***
     * @author 140024
     * @implNote HTML 설명
     * @since 2024-06-09
     */
    @Column(name="HTML_NM", length = 100, nullable = false)
    @Comment("HTML명")
    private String HTML_NM;

    /***
     * @author 140024
     * @implNote codeEntities 빌더
     * @since 2024-06-09
     */
    @ManyToMany(mappedBy = "htmlEntities")
    @Builder.Default
    private Set<CodeEntity> codeEntities = new HashSet<>();
}