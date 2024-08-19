package epams.model.vo;

import lombok.*;

/***
 * @author 140024
 * @implNote Login Log 데이터를 객체로 관리하기 위한 DTO
 * @since 2024-06-09
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LogViewVO extends BaseVO {

    /***
     * @author 140024
     * @implNote 페이지로그일련번호 (PAG_LOG_SNO)
     * @since 2024-06-09
     */
    private Long seqId;

    /***
     * @author 140024
     * @implNote 컨트롤러명 (CTRL_NM)
     * @since 2024-06-09
     */
    private String controllerName;

    /***
     * @author 140024
     * @implNote 메소드명(MTH_NM)
     * @since 2024-06-09
     */
    private String methodName;

    /***
     * @author 140024
     * @implNote 클라이언트IP주소(CLI_IP_ADDR)
     * @since 2024-06-09
     */
    private String clientIp;

    /***
     * @author 140024
     * @implNote 사용자접속환경내용(USR_CNC_ENV_CONE)
     * @since 2024-06-09
     */
    private String userAgent;

    /***
     * @author 140024
     * @implNote 요청URL주소(REQ_URL_ADDR)
     * @since 2024-06-09
     */
    private String requestUrl;


}