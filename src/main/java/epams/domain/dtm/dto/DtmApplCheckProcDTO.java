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
     * @implNote 회사코드
     * @since 2024-08-05
     */
    private String companyCd = "01";

    /***
     * @author 140024
     * @implNote 언어코드
     * @since 2024-08-05
     */
    private String localeCd = "ko";

    /***
     * @author 140024
     * @implNote 사원번호
     * @since 2024-08-05
     */
    private Long empId;

    /***
     * @author 140024
     * @implNote 사유코드
     * @since 2024-08-05
     */
    private String reasonCd;

    /***
     * @author 140024
     * @implNote 시작일자
     * @since 2024-08-05
     */
    private LocalDateTime staYmd;

    /***
     * @author 140024
     * @implNote 종료일자
     * @since 2024-08-05
     */
    private LocalDateTime endYmd;

    /***
     * @author 140024
     * @implNote ???
     * @since 2024-08-05
     */
    private Long hisOid;

    /***
     * @author 140024
     * @implNote 인증종류
     * @since 2024-08-05
     */
    private String authType;

    /***
     * @author 140024
     * @implNote 근태종류
     * @since 2024-08-05
     */
    private String dtmType;

    /***
     * @author 140024
     * @implNote 변경자
     * @since 2024-08-05
     */
    private String modUserId;

    /***
     * @author 140024
     * @implNote 타임존코드
     * @since 2024-08-05
     */
    private String tzCd = "ko";

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
    public DtmApplCheckProcDTO(final Long empId, final String reasonCd, final LocalDateTime staYmd, final LocalDateTime endYmd, final Long hisOid, final String authType, final String dtmType) {
        super();
        this.empId = empId;
        this.reasonCd = reasonCd;
        this.staYmd = staYmd;
        this.endYmd = endYmd;
        this.hisOid = hisOid;
        this.authType = authType;
        this.dtmType = dtmType;
    }

}
