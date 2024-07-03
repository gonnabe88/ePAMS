package epams.com.admin.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import epams.com.admin.entity.CodeEntity;
import epams.com.admin.entity.CodeHtmlEntity;
import epams.com.admin.entity.HtmlEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/***
 * @author 140024
 * @implNote 코드 테이블 데이터 정의 DTO
 * @since 2024-06-09
 */
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
public class CodeHtmlMapDTO {
    
    /***
     * @author 140024
     * @implNote HTML 경로
     * @since 2024-06-09
     */
    private String html;
	
    /***
     * @author 140024
     * @implNote 공통코드
     * @since 2024-06-09
     */
    private String code;
    
    /***
     * @author 140024
     * @implNote 수정시간
     * @since 2024-06-09
     */
    private LocalDate updatedTime;

    /***
     * @author 140024
     * @implNote 생성시간
     * @since 2024-06-09
     */
    private LocalDate createdTime;    
    
    /***
     * @author 140024
     * @implNote DTO를 Entity로 변환하는 메서드
     * @since 2024-06-09
     */
    public CodeHtmlEntity toEntity() {
        final CodeHtmlEntity entity = new CodeHtmlEntity();
        final CodeEntity codeEntity = new CodeEntity();
        codeEntity.setCDVA_ID(this.code);
        final HtmlEntity htmlEntity = new HtmlEntity();
        htmlEntity.setHTML_PTH_NM(this.html);
        entity.setCodeEntity(codeEntity);
        entity.setHtmlEntity(htmlEntity);
        entity.setGNT_DTM(this.createdTime);
        entity.setAMN_DTM(this.updatedTime);
        return entity;
    }

    /***
     * @author 140024
     * @implNote Entity를 DTO로 변환하는 메서드
     * @since 2024-06-09
     */
    public static CodeHtmlMapDTO toDTO(final CodeHtmlEntity entity) {
        final CodeHtmlMapDTO dto = new CodeHtmlMapDTO();
        dto.setHtml(entity.getHtmlEntity().getHTML_PTH_NM());
        dto.setCode(entity.getCodeEntity().getCDVA_ID());
        dto.setCreatedTime(entity.getGNT_DTM());
        dto.setUpdatedTime(entity.getAMN_DTM());
        return dto;
    }

    /***
     * @author 140024
     * @implNote Entity 리스트를 DTO 리스트로 변환하는 메서드
     * @since 2024-06-09
     */
    public static List<CodeHtmlMapDTO> toDTOList(final List<CodeHtmlEntity> entities) {
        return entities.stream().map(CodeHtmlMapDTO::toDTO).collect(Collectors.toList());
    }

    /***
     * @author 140024
     * @implNote DTO 리스트를 Entity 리스트로 변환하는 메서드
     * @since 2024-06-09
     */
    public static List<CodeHtmlEntity> toEntityList(final List<CodeHtmlMapDTO> dtos) {
        return dtos.stream().map(CodeHtmlMapDTO::toEntity).collect(Collectors.toList());
    }
    
}