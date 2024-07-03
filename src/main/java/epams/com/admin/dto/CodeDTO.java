package epams.com.admin.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import epams.com.admin.entity.CodeEntity;
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
    private LocalDate createdTime;
    
    /***
     * @author 140024
     * @implNote 수정시간
     * @since 2024-06-09
     */
    private LocalDate updatedTime;
    
    /***
     * @author 140024
     * @implNote Entity > DTO 변경 메소드
     * @since 2024-06-09
     */
    public static CodeDTO toDTO(final CodeEntity codeEntity) {
    	final CodeDTO codeDTO = new CodeDTO();
        codeDTO.setCode(codeEntity.getCDVA_ID());
        codeDTO.setCodeName(codeEntity.getCDVA_NM());
        codeDTO.setCodeType(codeEntity.getCDVA_KD_NM());
        codeDTO.setCreatedTime(codeEntity.getGNT_DTM());
        codeDTO.setUpdatedTime(codeEntity.getAMN_DTM());
        return codeDTO;
    }

    /***
     * @author 140024
     * @implNote DTO > Entity 변경 메소드
     * @since 2024-06-09
     */
    public CodeEntity toEntity() {
        final CodeEntity codeEntity = new CodeEntity();
        codeEntity.setCDVA_ID(this.code);
        codeEntity.setCDVA_NM(this.codeName);
        codeEntity.setCDVA_KD_NM(this.codeType);
        codeEntity.setAMN_DTM(this.updatedTime);
        codeEntity.setGNT_DTM(this.createdTime);
        return codeEntity;
    }
    
    /***
     * @author 140024
     * @implNote List<Entity> > List<DTO> 변경 메소드
     * @since 2024-06-09
     */
    public static List<CodeDTO> toDTOList(final List<CodeEntity> codeEntities) {
        return codeEntities.stream()
                .map(CodeDTO::toDTO)
                .collect(Collectors.toList());
    }

    /***
     * @author 140024
     * @implNote List<DTO> > List<Entity> 변경 메소드
     * @since 2024-06-09
     */
    public static List<CodeEntity> toEntityList(final List<CodeDTO> codeDTOs) {
        return codeDTOs.stream()
                .map(CodeDTO::toEntity)
                .collect(Collectors.toList());
    }
    
}