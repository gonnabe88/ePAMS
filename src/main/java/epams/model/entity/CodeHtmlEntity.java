package epams.model.entity;

import java.io.Serializable;

import lombok.*;
import org.hibernate.annotations.Comment;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

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
@ToString
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
     * @implNote HTML (복합키)
     * @since 2024-06-09
     */
    @Id
    @ManyToOne
    @JoinColumn(
            name = "HTML_FL_PTH",
            foreignKey = @ForeignKey(name = "FK_THURXE_CCDHTR_HTML_FL_PTH", foreignKeyDefinition = "FOREIGN KEY (HTML_FL_PTH) REFERENCES THURXE_CHTMLM (HTML_FL_PTH) ON DELETE CASCADE")
        )
    @Comment("HTML경로명")
    private HtmlEntity htmlEntity;

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
     * @implNote getter
     * @since 2024-06-09
     */
    public String getHTML_FL_NM() {
        return this.htmlEntity != null ? this.htmlEntity.getHTML_FL_PTH() : null;
    }

    /***
     * @author 140024
     * @implNote getter
     * @since 2024-06-09
     */
    public String getCDVA_ID() {
        return this.codeEntity != null ? this.codeEntity.getCDVA_ID() : null;
    }

    /***
     * @author 140024
     * @implNote setter for HTML_FL_NM
     * @since 2024-06-09
     */
    public void setHTML_FL_NM(final String html) {
        if (this.htmlEntity == null) {
            this.htmlEntity = new HtmlEntity();
        }
        this.htmlEntity.setHTML_FL_NM(html);
    }

    /***
     * @author 140024
     * @implNote setter for CDVA_ID
     * @since 2024-06-09
     */
    public void setCDVA_ID(final String code) {
        if (this.codeEntity == null) {
            this.codeEntity = new CodeEntity();
        }
        this.codeEntity.setCDVA_ID(code);
    }
}