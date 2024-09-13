package epams.domain.dtm.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/***
 * @author 140024
 * @implNote 근태종류별 합계 데이터 정의 DTO
 * @since 2024-06-09
 */
@Slf4j
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DtmKindSumDTO {

    /***
     * @author 140024
     * @implNote 연차휴가 일수 합계
     * @implSpec Output
     * @since 2024-08-11
     */
    private Float annualDaySum = 0F;

    /***
     * @author 140024
     * @implNote 연차휴가 시간 합계
     * @implSpec Output
     * @since 2024-08-11
     */
    private Float annualHourSum = 0F;

    /***
     * @author 140024
     * @implNote 내년 연차휴가 일수 합계
     * @implSpec Output
     * @since 2024-08-11
     */
    private Float nextAnnualDaySum = 0F;

    /***
     * @author 140024
     * @implNote 내년 연차휴가 시간 합계
     * @implSpec Output
     * @since 2024-08-11
     */
    private Float nextAnnualHourSum = 0F;

    public void thisAnnaulSum(Float day, Float hour) {
        this.annualDaySum += day;
        this.annualHourSum += hour;
    }

    public void nextAnnualSum(Float day, Float hour) {
        this.nextAnnualDaySum += day;
        this.nextAnnualHourSum += hour;
    }
}
