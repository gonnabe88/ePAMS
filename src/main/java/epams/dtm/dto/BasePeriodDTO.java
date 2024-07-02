package epams.dtm.dto;

import java.time.LocalDateTime;
import java.time.Month;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/***
 * @author 140024
 * @implNote 기간 DTO
 * @since 2024-06-09
 */
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BasePeriodDTO {

    /***
     * @author 140024
     * @implNote 시작일
     * @since 2024-06-09
     */
    private LocalDateTime startDate;

     /***
     * @author 140024
     * @implNote 종료일
     * @since 2024-06-09
     */
    private LocalDateTime endDate;

    /***
     * @author 140024
     * @implNote 종료일
     * @since 2024-06-09
     */
    private int limit;

    /***
     * @author 140024
     * @implNote 종료일
     * @since 2024-06-09
     */
    private int offset;

    /***
     * 기본 생성자
     * @author 140024
     * @since 2024-06-09
     */
    public BasePeriodDTO() {
        // 디폴트 값 설정 (필요에 따라 설정)
       this.startDate = LocalDateTime.of(1900, Month.JANUARY, 1, 0, 0);
       this.endDate = LocalDateTime.of(9999, Month.DECEMBER, 31, 0, 0);
       this.limit = 9999;
       this.offset = 9999;
    }
    
    
}