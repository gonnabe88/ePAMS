package com.kdb.com.entity;

import java.io.Serializable;

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
@Table(name = "com_code_html")
public class CodeHtmlMapEntity extends BaseEntity implements Serializable {

    /***
     * @author 140024
     * @implNote 공통코드 (복합키)
     * @since 2024-06-09
     */
	@Id
    @ManyToOne
    @JoinColumn(
            name = "CD",
            foreignKey = @ForeignKey(name = "fk_com_code_html_cd", foreignKeyDefinition = "FOREIGN KEY (CD) REFERENCES com_code (CD) ON DELETE CASCADE ON UPDATE CASCADE")
        )
    private CodeEntity codeEntity;

    /***
     * @author 140024
     * @implNote HTML (복합키)
     * @since 2024-06-09
     */
    @Id
    @ManyToOne
    @JoinColumn(
            name = "HTML",
            foreignKey = @ForeignKey(name = "fk_com_code_html_html", foreignKeyDefinition = "FOREIGN KEY (HTML) REFERENCES com_html (HTML) ON DELETE CASCADE ON UPDATE CASCADE")
        )
    private HtmlEntity htmlEntity;

}