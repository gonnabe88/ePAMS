package epams.domain.com.constValue;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/***
 * @author 140024
 * @implNote 근태 이력 데이터 정의 DTO
 * @since 2024-06-09
 */
@Slf4j
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConstValueDTO {

    /***
     * @author 140024
     * @implNote [OUTPUT] 상수값
     * @since 2024-09-19
     */
    private String constValue;

    /***
     * @author 140024
     * @implNote [INPUT] 회사코드
     * @since 2024-09-19
     */
    private String companyCd = "01";

    /***
     * @author 140024
     * @implNote [INPUT] 단위업무(업무분류)코드
     * @since 2024-09-19
     */
    private String unitCd;

    /***
     * @author 140024
     * @implNote [INPUT] 상수값구분
     * @since 2024-09-19
     */
    private String constKind;
    
    /***
     * @author 140024
     * @implNote [INPUT] 기준일자 XF_SYSDATE(0)
     * @since 2024-09-19
     */
    private LocalDateTime baseYmd;

    /***
     * @author 140024
     * @implNote 생성자
     * @param unitCd [INPUT] 단위업무(업무분류)코드
     * @param constKind [INPUT] 상수값구분
     * @since 2024-09-19
     */
    public ConstValueDTO(String unitCd, String constKind) {
        this.unitCd = unitCd;
        this.constKind = constKind;
        this.baseYmd = LocalDateTime.now();
    }

    /***
     * @author 140024
     * @implNote 생성자
     * @param unitCd [INPUT] 단위업무(업무분류)코드
     * @param constKind [INPUT] 상수값구분
     * @param baseYmd [INPUT] 기준일자
     * @since 2024-09-19
     */
    public ConstValueDTO(String unitCd, String constKind, LocalDateTime baseYmd) {
        this.unitCd = unitCd;
        this.constKind = constKind;
        this.baseYmd = baseYmd;
    }
}
