package epams.domain.dtm.dto;

import epams.domain.dtm.vo.DtmHisVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class DtmHisDTO extends DtmHisVO {

    /***
     * @author 140024
     * @implNote 페이지당 게시물 수
     * @since 2024-06-09
     */
    private Integer itemsPerPage = 10;

    /***
     * @author 140024
     * @implNote 결재상태 (결재중/결재완료/반려)
     * @since 2024-06-09
     */
    private String statCdNm = "결재완료";

    /***
     * @author 140024
     * @implNote 결재상태 리스트 (결재중/결재완료/반려)
     * @since 2024-06-09
     */
    private List<String> statCdList;

    /***
     * @author 140024
     * @implNote 경과여부 (과거/진행중/예정)
     * @since 2024-06-09
     */
    private String status;

    /***
     * @author 140024
     * @implNote 근태유형이름 (연차/반차..)
     * @since 2024-06-09
     */
    private String dtmKindNm;

    /***
     * @author 140024
     * @implNote 근태시작일 입력값
     * @since 2024-06-09
     */
    private String staYmdInput;

    /***
     * @author 140024
     * @implNote 근태종료일 입력값
     * @since 2024-06-09
     */
    private String endYmdInput;

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
     * @author 140024
     * @implNote 생성자
     * @since 2024-06-09
     */
    public DtmHisDTO(Long dtmHisId, Long empId, String dtmKindCd, String dtmReasonCd, LocalDateTime staYmd, String staHm, LocalDateTime endYmd, String endHm, String dtmReason, String destPlc, String telno, String childNo, Long applId, String statCd, LocalDateTime finalApprYmd, String modiType, String modiReason, Long modiDtmHisId, Long modUserId, LocalDateTime modDate, String tzCd, LocalDateTime tzDate, String companyNm, String document, String adUseYn, String mailSendYn, String esbAskDt, LocalDateTime childBirthYmd, String dtmStoreYn, Long fileId, String sealMgrYn, String secuMgrYn, String infoMgrYn, String safeMgrYn, String placeCd, int limit, int offset) {
        super(dtmHisId, empId, dtmKindCd, dtmReasonCd, staYmd, staHm, endYmd, endHm, dtmReason, destPlc, telno, childNo, applId, statCd, finalApprYmd, modiType, modiReason, modiDtmHisId, modUserId, modDate, tzCd, tzDate, companyNm, document, adUseYn, mailSendYn, esbAskDt, childBirthYmd, dtmStoreYn, fileId, sealMgrYn, secuMgrYn, infoMgrYn, safeMgrYn, placeCd);
        this.limit = limit;
        this.offset = offset;
        this.status = calculateStatus(staYmd, staHm, endYmd, endHm);
    }

    /***
     * @author 140024
     * @implNote 경과여부 (과거/진행중/예정) 세팅
     * @since 2024-06-09
     */
    private String calculateStatus(LocalDateTime staYmd, String staHm, LocalDateTime endYmd, String endHm) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDateTime = LocalDateTime.of(staYmd.toLocalDate(), LocalTime.parse(staHm, DateTimeFormatter.ofPattern("HHmm")));
        LocalDateTime endDateTime;
        if ("2400".equals(endHm)) {
            // endHm이 2400이면 +1일 00시로 설정
            endDateTime = LocalDateTime.of(endYmd.toLocalDate().plusDays(1), LocalTime.MIDNIGHT);
        } else {
            endDateTime = LocalDateTime.of(endYmd.toLocalDate(), LocalTime.parse(endHm, DateTimeFormatter.ofPattern("HHmm")));
        }

        if (now.isBefore(startDateTime)) {
            return "예정";
        } else if (now.isAfter(endDateTime)) {
            return "과거";
        } else {
            return "진행";
        }
    }
    
}
