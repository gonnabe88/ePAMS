package epams.domain.dtm.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/***
 * @author 140024
 * @implNote 연차저축 DTO
 * @since 2024-06-09
 */
@Slf4j
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DtmSaveDTO extends DtmSqlParamDTO {

    /***
     * @author 140024
     * @implNote 저축시행(가능)여부 (DTM_SAVE_YN)
     * @since 2024-09-12
     */
    private String savableYn = "N";

    /***
     * @author 140024
     * @implNote 저축기간 시작일 (STA_YMD)
     * @since 2024-08-11
     */
    private LocalDateTime staYmd = LocalDateTime.now();

    /***
     * @author 140024
     * @implNote 저축기간 종료일 (END_YMD)
     * @since 2024-08-11
     */
    private LocalDateTime endYmd = LocalDateTime.now();
    
    /***
     * @author 140024
     * @implNote 저축가능비율 (DTM_SAVE_RATE)
     * @since 2024-09-12
     */
    private float savableRate = 0;

    /***
     * @author 140024
     * @implNote 저축일수 (SAVE_CNT)
     * @since 2024-09-12
     */
    private float saveDayCnt = 0;

    
    /***
     * @author 140024
     * @implNote 저축시간 (SAVE_HHCNT)
     * @since 2024-09-12
     */
    private float saveHourCnt = 0;

    
    /***
     * @author 140024
     * @implNote 저축여부 (STORE_YN)
     * @since 2024-09-12
     */
    private String saveYn = "N";
}
