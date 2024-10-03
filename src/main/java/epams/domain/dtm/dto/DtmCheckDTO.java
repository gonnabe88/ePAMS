package epams.domain.dtm.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Arrays;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/***
 * @author 140024
 * @implNote 근태 기간(일/시간) 데이터 정의 DTO
 * @since 2024-06-09
 */
@Slf4j
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DtmCheckDTO {

    /***
     * @author 140024
     * @implNote 취소 후 연차사용일수 (tot_used2 = tot_used*1 - daycnt_day*1)
     * @implSpec Variable
     * @since 2024-08-11
     */
    private Float afterAnnualDayUsedCnt;

    /***
     * @author 140024
     * @implNote 취소 후 연차사용시간 (tot_used_hh2 = tot_used_hh*1 - daycnt*1)
     * @implSpec Variable
     * @since 2024-08-11
     */
    private Float afterAnnualHourUsedCnt;

    /***
     * @author 140024
     * @implNote 연차촉진/저축 체크 대상 [DTM_REASON_CD]
     * @implSpec Constant
     * @since 2024-08-11
     */
    private List<String> annualCheckList = Arrays.asList("1A1", "1A5", "1A6", "1F1", "1K1", "1AE", "1AG", "1AH");

    /***
     * @author 140024
     * @implNote 취소 후 저축사용일수 (tot_used2 = tot_used*1 - daycnt_day*1)
     * @implSpec Variable
     * @since 2024-08-11
     */
    private Float afterSaveDayUsedCnt;

    /***
     * @author 140024
     * @implNote 취소 후 저축사용시간 (tot_used_hh2 = tot_used_hh*1 - daycnt*1)
     * @implSpec Variable
     * @since 2024-08-11
     */
    private Float afterSaveHourUsedCnt;

    /***
     * @author 140024
     * @implNote 저축 체크 대상
     * @implSpec Constant
     * @since 2024-08-11
     */
    private List<String> saveCheckList = Arrays.asList("1AB", "1AC", "1AD", "1AI", "1AJ");


    /***
     * @author 140024
     * @implNote 회사코드
     * @implSpec Input
     * @since 2024-08-11
     */
    private String companyCd = "01";

    /***
     * @author 140024
     * @implNote 근태사유코드 [DTM_REASON_CD]
     * @implSpec Input
     * @since 2024-08-11
     */
    private String dtmReasonCd;

    /***
     * @author 140024
     * @implNote 시작일
     * @implSpec Input
     * @since 2024-08-11
     */
    private LocalDateTime staYmd;

    /***
     * @author 140024
     * @implNote 종료일
     * @implSpec Input
     * @since 2024-08-11
     */
    private LocalDateTime endYmd;

    /***
     * @author 140024
     * @implNote 종료일
     * @implSpec Input
     * @since 2024-08-11
     */
    private String localeCd = "KO";

    /***
     * @author 140024
     * @implNote 행번(7140024)
     * @implSpec Input
     * @since 2024-08-11
     */
    private Long empId;

    /***
     * @author 140024
     * @implNote 일수
     * @implSpec Output
     * @since 2024-08-11
     */
    private Float dayCount;

    /***
     * @author 140024
     * @implNote 시간수
     * @implSpec Output
     * @since 2024-08-11
     */
    private Float hourCount;

    public DtmCheckDTO(String dtmReasonCd, LocalDateTime staYmd, LocalDateTime endYmd, Long empId) {
        this.dtmReasonCd = dtmReasonCd;
        this.staYmd = staYmd;
        this.endYmd = endYmd;
        this.empId = empId;
    }
    public String getStaYmdStr() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return this.staYmd.format(formatter);
    }
    public String getEndYmdStr() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");        
        return this.endYmd.format(formatter);
    }
}
