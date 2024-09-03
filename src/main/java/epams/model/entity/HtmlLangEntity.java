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
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@Builder
@Table(name = "THURXE_CCDHTA")
@Comment("인사_외부근태 HTML관계")
public class HtmlLangEntity extends BaseEntity implements Serializable {

    /**
	 * 명시적 serialVersionUID 설정
	 */
	private static final long serialVersionUID = 1L;


    /***
     * @author 140024
     * @implNote HTML (복합키)
     * @since 2024-06-09
     */
    @Id
    @ManyToOne
    @JoinColumn(
            name = "HTML_FL_PTH",
            foreignKey = @ForeignKey(name = "FK_THURXE_CCDHTA_HTML_FL_PTH", foreignKeyDefinition = "FOREIGN KEY (HTML_FL_PTH) REFERENCES OEHR.THURXE_CHTMLM (HTML_FL_PTH) ON DELETE CASCADE")
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
            name = "SRE_VCB_CDVA_ID",
            foreignKey = @ForeignKey(name = "FK_THURXE_CCDHTA_SRE_VCB_CDVA_ID", foreignKeyDefinition = "FOREIGN KEY (SRE_VCB_CDVA_ID) REFERENCES OEHR.THURXE_CCODEM (SRE_VCB_CDVA_ID) ON DELETE CASCADE")
    )
    @Comment("화면용어코드값ID")
    private LangEntity langEntity;

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
    public String getSRE_VCB_CDVA_ID() {
        return this.langEntity != null ? this.langEntity.getSRE_VCB_CDVA_ID() : null;
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
     * @implNote setter for SRE_VCB_CDVA_ID
     * @since 2024-06-09
     */
    public void setSRE_VCB_CDVA_ID(final String code) {
        if (this.langEntity == null) {
            this.langEntity = new LangEntity();
        }
        this.langEntity.setSRE_VCB_CDVA_ID(code);
    }
}