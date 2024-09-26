package epams.domain.dtm.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import pams.model.vo.DtmHisVO;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/***
 * @author 140024
 * @implNote 근태 이력 데이터 정의 DTO
 * @since 2024-06-09
 */
@Slf4j
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DtmSearchDTO {

    /***
     * @author 140024
     * @implNote 직원ID
     * @since 2024-08-11
     */
    private Long empId;

    /***
     * @author 140024
     * @implNote 페이지당 게시물 수
     * @since 2024-06-09
     */
    private Integer itemsPerPage = 5;

    /***
     * @author 140024
     * @implNote 결재상태코드 리스트 (121/132/131/141)
     * @since 2024-06-09
     */
    private List<String> statCdList = List.of("121","132"); // 121(결재중), 132(결재완료) 기본선택

    /***
     * @author 140024
     * @implNote 근태종류 (전체)
     * @since 2024-06-09
     */
    private String dtmKindCd = "";

    /***
     * @author 140024
     * @implNote 근태유형 (전체)
     * @since 2024-06-09
     */
    private String dtmReasonCd = "";

    /***
     * @author 140024
     * @implNote 근태시작일 입력값
     * @since 2024-06-09
     */
    private String staYmdInput = "2023-01-01";

    /***
     * @author 140024
     * @implNote 근태종료일 입력값
     * @since 2024-06-09
     */
    private String endYmdInput = "";

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

}
