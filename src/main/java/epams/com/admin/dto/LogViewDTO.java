package epams.com.admin.dto;

import epams.com.admin.entity.LogViewEntity;
import epams.com.board.dto.BaseDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/***
 * @author 140024
 * @implNote Login Log 데이터를 객체로 관리하기 위한 DTO
 * @since 2024-06-09
 */
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
public class LogViewDTO extends BaseDTO {

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
     * @implNote 사용자접속환경내용(USR_CNC_ENV_INF)
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