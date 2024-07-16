package epams.model.vo;

import lombok.*;

/***
 * @author 140024
 * @implNote Login Log 데이터를 객체로 관리하기 위한 DTO
 * @since 2024-06-09
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LogLoginVO extends BaseVO {

    /***
     * @author 140024
     * @implNote 로그인로그일련번호(LGN_LOG_SNO)
     * @since 2024-06-09
     */
    private Long seqId;

    /***
     * @author 140024
     * @implNote 사원번호 (ENO)
     * @since 2024-06-09
     */
    private String empNo;

    /***
     * @author 140024
     * @implNote 인증방식 (CER_KD_NM)
     * @since 2024-06-09
     */
    private String loginType;

    /***
     * @author 140024
     * @implNote 로그인성공여부 (LGN_SCS_YN)
     * @since 2024-06-09
     */
    private char loginResult;
    
}