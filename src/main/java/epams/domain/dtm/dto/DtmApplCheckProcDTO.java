package epams.domain.dtm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/***
 * @author 140024
 * @implNote 프로시저 호출용
 * @since 2024-06-09
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DtmApplCheckProcDTO {

    /***
     * @author 140024
     * @implNote 사원번호
     * @since 2024-06-09
     */
    private Long empId;
    /***
     * @author 140024
     * @implNote 사원번호
     * @since 2024-06-09
     */
    private LocalDateTime staYmd;

    /***
     * @author 140024
     * @implNote 사원번호
     * @since 2024-06-09
     */
    private LocalDateTime endYmd;

    /***
     * @author 140024
     * @implNote 사원번호
     * @since 2024-06-09
     */
    private String result;

}
