package epams.domain.dtm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * @author 140024
 * @implNote 프로시저 호출용
 * @since 2024-06-09
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DtmApplElaCheckProcDTO {

    /***
     * @author 140024
     * @implNote 신청서ID
     * @since 2024-08-05
     */
    private Long applId;

    /***
     * @author 140024
     * @implNote 변경자
     * @since 2024-08-05
     */
    private Long modUserId;

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
    public DtmApplElaCheckProcDTO(final Long applId, final Long modUserId) {
        this.applId = applId;
        this.modUserId = modUserId;
    }

}
