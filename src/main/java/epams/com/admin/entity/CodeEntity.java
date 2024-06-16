package epams.com.admin.entity;

import java.util.HashSet;
import java.util.Set;

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
@Table(name = "com_code")
public class CodeEntity extends BaseEntity {
	
    /***
     * @author 140024
     * @implNote 공통코드
     * @since 2024-06-09
     */
    @Id // 기본키
    @Column(length = 128)
    private String CD_ID;
    
    /***
     * @author 140024
     * @implNote 코드명
     * @since 2024-06-09
     */
    @Column(length = 500, nullable = false)
    private String CD_NM;

    /***
     * @author 140024
     * @implNote 코드형식
     * @since 2024-06-09
     */
    @Column(length = 20, nullable = false) // 크기 20, not null
    private String CD_TYPE;
    
    @ManyToMany
    @JoinTable(
        name = "com_code_html",
        joinColumns = @JoinColumn(name = "CD_ID", foreignKey = @ForeignKey(name = "fk_com_code_html_cd")),
        inverseJoinColumns = @JoinColumn(name = "HTML", foreignKey = @ForeignKey(name = "fk_com_code_html_html"))
    )
    private Set<HtmlEntity> htmlEntities = new HashSet<>();
    
}