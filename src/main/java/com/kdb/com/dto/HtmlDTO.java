package com.kdb.com.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.kdb.com.entity.CodeEntity;
import com.kdb.com.entity.HtmlEntity;

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
    private LocalDateTime createdTime;
    
    /***
     * @author 140024
     * @implNote 수정시간
     * @since 2024-06-09
     */
    private LocalDateTime updatedTime;
    
    /***
     * @author 140024
     * @implNote Entity > DTO 변경 메소드
     * @since 2024-06-09
     */
    public static HtmlDTO toDTO(HtmlEntity htmlEntity) {
        HtmlDTO htmlDTO = new HtmlDTO();
        htmlDTO.setHtml(htmlEntity.getHTML());
        htmlDTO.setHtmlName(htmlEntity.getHTML_NM());
        htmlDTO.setCreatedTime(htmlEntity.getCreatedTime());
        htmlDTO.setUpdatedTime(htmlEntity.getUpdatedTime());
        return htmlDTO;
    }

    /***
     * @author 140024
     * @implNote DTO > Entity 변경 메소드
     * @since 2024-06-09
     */
    public HtmlEntity toEntity() {
    	HtmlEntity htmlEntity = new HtmlEntity();
    	htmlEntity.setHTML(this.html);
    	htmlEntity.setHTML_NM(this.htmlName);
    	htmlEntity.setUpdatedTime(this.updatedTime);
    	htmlEntity.setCreatedTime(this.createdTime);
        return htmlEntity;
    }
    
    /***
     * @author 140024
     * @implNote List<Entity> > List<DTO> 변경 메소드
     * @since 2024-06-09
     */
    public static List<HtmlDTO> toDTOList(List<HtmlEntity> htmlEntity) {
        return htmlEntity.stream()
                .map(HtmlDTO::toDTO)
                .collect(Collectors.toList());
    }

    /***
     * @author 140024
     * @implNote List<CodeDTO> > List<CodeEntity> 변경 메소드
     * @since 2024-06-09
     */
    public static List<HtmlEntity> toEntityList(List<HtmlDTO> htmlDTOs) {
        return htmlDTOs.stream()
                .map(HtmlDTO::toEntity)
                .collect(Collectors.toList());
    }
    
}