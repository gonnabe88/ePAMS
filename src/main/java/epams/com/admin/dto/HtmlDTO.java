package epams.com.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import epams.com.admin.entity.HtmlEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
public class HtmlDTO {

    /***
     * @author 140024
     * @implNote HTML 경로
     * @since 2024-06-09
     */
    private String html;

    /***
     * @author 140024
     * @implNote 코드명
     * @since 2024-06-09
     */
    private String htmlName;

    /***
     * @author 140024
     * @implNote 생성시간
     * @since 2024-06-09
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

    /***
     * @author 140024
     * @implNote 수정시간
     * @since 2024-06-09
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedTime;

    /***
     * @author 140024
     * @implNote Entity > DTO 변경 메소드
     * @since 2024-06-09
     */
    public static HtmlDTO toDTO(final HtmlEntity htmlEntity) {
        final HtmlDTO HtmlDTO = new HtmlDTO();
        HtmlDTO.setHtml(htmlEntity.getHTML_FL_PTH());
        HtmlDTO.setHtmlName(htmlEntity.getHTML_FL_NM());
        HtmlDTO.setCreatedTime(htmlEntity.getGNT_DTM());
        HtmlDTO.setUpdatedTime(htmlEntity.getAMN_DTM());
        return HtmlDTO;
    }

    /***
     * @author 140024
     * @implNote DTO > Entity 변경 메소드
     * @since 2024-06-09
     */
    public HtmlEntity toEntity() {
        final HtmlEntity htmlEntity = new HtmlEntity();
        htmlEntity.setHTML_FL_PTH(this.html);
        htmlEntity.setHTML_FL_NM(this.htmlName);
        htmlEntity.setAMN_DTM(this.updatedTime);
        htmlEntity.setGNT_DTM(this.createdTime);
        return htmlEntity;
    }

    /***
     * @author 140024
     * @implNote List<Entity> > List<DTO> 변경 메소드
     * @since 2024-06-09
     */
    public static List<HtmlDTO> toDTOList(final List<HtmlEntity> htmlEntity) {
        return htmlEntity.stream()
                .map(HtmlDTO::toDTO)
                .collect(Collectors.toList());
    }

    /***
     * @author 140024
     * @implNote List<DTO> > List<Entity> 변경 메소드
     * @since 2024-06-09
     */
    public static List<HtmlEntity> toEntityList(final List<HtmlDTO> HtmlDTOs) {
        return HtmlDTOs.stream()
                .map(HtmlDTO::toEntity)
                .collect(Collectors.toList());
    }

}