package epams.com.admin.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import epams.com.admin.entity.CodeEntity;
import epams.com.board.dto.BaseDTO;
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
public class CodeDTO extends BaseDTO {
	
    /***
     * @author 140024
     * @implNote 코드값ID(CDVA_ID)
     * @since 2024-06-09
     */
    private String code;
    
    /***
     * @author 140024
     * @implNote 코드값명(CDVA_NM)
     * @since 2024-06-09
     */
    private String codeName;
    
    /***
     * @author 140024
     * @implNote 코드값종류명(CDVA_KD_NM)
     * @since 2024-06-09
     */
    private String codeType;

}