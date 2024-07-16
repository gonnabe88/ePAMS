package epams.model.vo;

import lombok.*;

/***
 * @author 140024
 * @implNote 코드 테이블 데이터 정의 DTO
 * @since 2024-06-09
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CodeVO extends BaseVO {
	
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