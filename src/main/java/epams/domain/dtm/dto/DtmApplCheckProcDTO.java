package epams.domain.dtm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
     * @implNote 회사코드
     * @since 2024-08-05
     */
    private String companyCd = "01";

    /***
     * @author 140024
     * @implNote 언어코드
     * @since 2024-08-05
     */
    private String localeCd = "KO";

    /***
     * @author 140024
     * @implNote 사원번호
     * @since 2024-08-05
     */
    private Long empId;

    /***
     * @author 140024
     * @implNote 근태코드
     * @since 2024-08-05
     */
    private String reasonCd;

    /***
     * @author 140024
     * @implNote 시작일자
     * @since 2024-08-05
     */
    private LocalDate staYmd;

    /***
     * @author 140024
     * @implNote 종료일자
     * @since 2024-08-05
     */
    private LocalDate endYmd;

    /***
     * @author 140024
     * @implNote 수정 시 체크로직
     * 수정 신청인 경우 대상 dtm_his_id
     * 신규 신청인 경우 값 불필요
     * @since 2024-08-05
     */
    private Long hisOid;

    /***
     * @author 140024
     * @implNote 신청자구분 (관리자/서무/개인)
     * 프로시저에서 NVL로 NULL이면 기본 emp를 넣어주기 때문에 개인 신청인 경우 값 불필요
     * @since 2024-08-05
     */
    private String authType;

    /***
     * @author 140024
     * @implNote 수정 시 체크로직
     * 수정 신청인 경우 대상 근태종류
     * 신규 신청인 경우 값 불필요
     * @since 2024-08-05
     */
    private String dtmType;
    
    /***
     * @author 140024
     * @implNote 아이번호
     * @since 2024-08-05
     */
    private String childNo;
    
    /***
     * @author 140024
     * @implNote 변경자
     * @since 2024-08-05
     */
    private Long modUserId;

    /***
     * @author 140024
     * @implNote 타임존코드
     * @since 2024-08-05
     */
    private String tzCd = "KST";

    /***
     * @author 140024
     * @implNote 결과코드
     * @since 2024-08-05
     */
    private String resultCode;

    /***
     * @author 140024
     * @implNote 실행결과
     * @since 2024-08-05
     */
    private String resultMsg;

    /***
     * @author 140024
     * @implNote 생성자
     * @since 2024-08-05
     */
    public DtmApplCheckProcDTO(
            final Long empId,
            final String reasonCd,
            final LocalDateTime staYmd,
            final LocalDateTime endYmd,
            final Long modUserId) {
        super();
        this.empId = empId;
        this.reasonCd = reasonCd;
        this.staYmd = staYmd.toLocalDate();
        this.endYmd = endYmd.toLocalDate();
        this.modUserId = modUserId;
    }

}
