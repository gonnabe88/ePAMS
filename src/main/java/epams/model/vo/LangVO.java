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
public class LangVO extends BaseVO {
	
    /***
     * @author 140024
     * @implNote 화면용어코드값ID(SRE_VCB_CDVA_ID)
     * @since 2024-06-09
     */
    private String lang;
    
    /***
     * @author 140024
     * @implNote 화면용어명(SRE_VCB_NM)
     * @since 2024-06-09
     */
    private String langName;
    
    /***
     * @author 140024
     * @implNote 언어종류명(언어_KD_NM)
     * @since 2024-06-09
     */
    private String langType;

}