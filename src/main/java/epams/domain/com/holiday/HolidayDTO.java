package epams.domain.com.holiday;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/***
 * @author 140024
 * @implNote 근태 이력 데이터 정의 DTO
 * @since 2024-06-09
 */
@Getter
@Slf4j
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HolidayDTO {

    /***
     * @author 140024
     * @implNote [OUTPUT] 휴일 날짜
     * @since 2024-09-19
     */
    private LocalDate holiYmd;

    /***
     * @author 140024
     * @implNote [OUTPUT] 휴일 요일
     * @since 2024-09-19
     */
    private String holiDay;

    /***
     * @author 140024
     * @implNote [OUTPUT] 휴일 이름
     * @since 2024-09-19
     */
    private String holiNm;

    /***
     * @author 140024
     * @implNote [INPUT] 시작일
     * @since 2024-09-19
     */
    private LocalDateTime staYmd;
    
    /***
     * @author 140024
     * @implNote [INPUT] 종료일
     * @since 2024-09-19
     */
    private LocalDateTime endYmd;

}
