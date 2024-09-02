package epams.model.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import lombok.*;
import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

/***
 * @author 140024
 * @implNote HTML 테이블 정의 entity
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
@Table(name = "THURXE_CHTMLM")
@Comment("인사_외부근태 HTML화면기본")
public class HtmlEntity extends BaseEntity implements Serializable {

    /**
	 * 명시적 serialVersionUID 설정
	 */
	private static final long serialVersionUID = 2L;

	/***
     * @author 140024
     * @implNote HTML 경로
     * @since 2024-06-09
     */
    @Id // 기본키
    @Column(name="HTML_FL_PTH", length = 2000)
    @Comment("HTML파일경로")
    private String HTML_FL_PTH;

    /***
     * @author 140024
     * @implNote HTML 설명
     * @since 2024-06-09
     */
    @Column(name="HTML_FL_NM", length = 100, nullable = false)
    @Comment("HTML파일명")
    private String HTML_FL_NM;

    /***
     * @author 140024
     * @implNote codeEntities 빌더
     * @since 2024-06-09
     */
    @ManyToMany(mappedBy = "htmlEntities")
    @Builder.Default
    private Set<LangEntity> codeEntities = new HashSet<>();
}