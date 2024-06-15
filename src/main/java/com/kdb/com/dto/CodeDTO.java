package com.kdb.com.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.kdb.com.entity.CodeEntity;

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
public class CodeDTO {
	
    /***
     * @author 140024
     * @implNote 공통코드
     * @since 2024-06-09
     */
    private String code;
    
    /***
     * @author 140024
     * @implNote 코드명
     * @since 2024-06-09
     */
    private String codeName;
    
    /***
     * @author 140024
     * @implNote 코드형식
     * @since 2024-06-09
     */
    private String codeType;
    
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
    public static CodeDTO toDTO(CodeEntity codeEntity) {
    	CodeDTO codeDTO = new CodeDTO();
        codeDTO.setCode(codeEntity.getCD());
        codeDTO.setCodeName(codeEntity.getCD_NM());
        codeDTO.setCodeType(codeEntity.getCD_TYPE());
        codeDTO.setCreatedTime(codeEntity.getCREATED_TIME());
        codeDTO.setUpdatedTime(codeEntity.getUPDATED_TIME());
        return codeDTO;
    }

    /***
     * @author 140024
     * @implNote DTO > Entity 변경 메소드
     * @since 2024-06-09
     */
    public CodeEntity toEntity() {
        CodeEntity codeEntity = new CodeEntity();
        codeEntity.setCD(this.code);
        codeEntity.setCD_NM(this.codeName);
        codeEntity.setCD_TYPE(this.codeType);
        codeEntity.setUPDATED_TIME(this.updatedTime);
        codeEntity.setCREATED_TIME(this.createdTime);
        return codeEntity;
    }
    
    /***
     * @author 140024
     * @implNote List<Entity> > List<DTO> 변경 메소드
     * @since 2024-06-09
     */
    public static List<CodeDTO> toDTOList(List<CodeEntity> codeEntities) {
        return codeEntities.stream()
                .map(CodeDTO::toDTO)
                .collect(Collectors.toList());
    }

    /***
     * @author 140024
     * @implNote List<HtmlDTO> > List<CodeEntity> 변경 메소드
     * @since 2024-06-09
     */
    public static List<CodeEntity> toEntityList(List<CodeDTO> codeDTOs) {
        return codeDTOs.stream()
                .map(CodeDTO::toEntity)
                .collect(Collectors.toList());
    }
    
}