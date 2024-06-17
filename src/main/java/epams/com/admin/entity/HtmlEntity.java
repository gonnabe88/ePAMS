package epams.com.admin.entity;

import java.util.HashSet;
import java.util.Set;

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
@Table(name = "com_html")
public class HtmlEntity extends BaseEntity {

    /***
     * @author 140024
     * @implNote HTML 경로
     * @since 2024-06-09
     */
    @Id // 기본키
    @Column(length = 128)
    private String HTML;

    /***
     * @author 140024
     * @implNote HTML 설명
     * @since 2024-06-09
     */
    @Column(length = 100, nullable = false)
    private String HTML_NM;

    @ManyToMany(mappedBy = "htmlEntities")
    @Builder.Default
    private Set<CodeEntity> codeEntities = new HashSet<>();
}