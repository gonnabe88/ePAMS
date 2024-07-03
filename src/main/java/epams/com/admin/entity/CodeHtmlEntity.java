package epams.com.admin.entity;

import java.io.Serializable;

import org.hibernate.annotations.Comment;

import epams.com.board.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/***
 * @author 140024
 * @implNote 코드-HTML mapping을 위한 테이블 entity
 * @since 2024-06-09
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "THURXE_CCDHTR")
@Comment("인사_외부근태 코드HTML관계")
public class CodeHtmlEntity extends BaseEntity implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6855998655976669255L;

	/***
     * @author 140024
     * @implNote 공통코드 (복합키)
     * @since 2024-06-09
     */
	@Id
    @ManyToOne
    @JoinColumn(
            name = "CDVA_ID",
            foreignKey = @ForeignKey(name = "FK_THURXE_CCDHTR_CDVA_ID", foreignKeyDefinition = "FOREIGN KEY (CDVA_ID) REFERENCES THURXE_CCODEM (CDVA_ID) ON DELETE CASCADE")
        )
    @Comment("코드값ID")
    private CodeEntity codeEntity;

    /***
     * @author 140024
     * @implNote HTML (복합키)
     * @since 2024-06-09
     */
    @Id
    @ManyToOne
    @JoinColumn(
            name = "HTML_PTH_NM",
            foreignKey = @ForeignKey(name = "FK_THURXE_CCDHTR_HTML_PTH_NM", foreignKeyDefinition = "FOREIGN KEY (HTML_PTH_NM) REFERENCES THURXE_CHTMLM (HTML_PTH_NM) ON DELETE CASCADE")
        )
    @Comment("HTML경로명")
    private HtmlEntity htmlEntity;
    
    /***
     * @author 140024
     * @implNote getter
     * @since 2024-06-09
     */
    public String getHTML_PTH_NM() {
        return this.htmlEntity != null ? this.htmlEntity.getHTML_PTH_NM() : null;
    }

    /***
     * @author 140024
     * @implNote getter
     * @since 2024-06-09
     */
    public String getCDVA_ID() {
        return this.codeEntity != null ? this.codeEntity.getCDVA_ID() : null;
    }

}