package epams.domain.dtm.dto;

import pams.model.vo.DtmHisVO;
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
    private Integer itemsPerPage = 5;

    /***
     * @author 140024
     * @implNote 자정 시간
     * @since 2024-06-09
     */
    private final static String midnight = "2400";

    /***
     * @author 140024
     * @implNote 화면에 표시할 근태명
     * @since 2024-06-09
     */
    private String dtmDispName;

    /***
     * @author 140024
     * @implNote 결재상태 (결재중/결재완료/반려)
     * @since 2024-06-09
     */
    private String statCdNm;

    /***
     * @author 140024
     * @implNote 결재상태코드 리스트 (121/132/131)
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
    private String dtmReasonNm;

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
    public DtmHisDTO(final Long dtmHisId, final Long empId, final String dtmKindCd, final String dtmReasonCd, final LocalDateTime staYmd, final String staHm, final LocalDateTime endYmd, final String endHm, final String dtmReason, final String destPlc, final String telno, final String childNo, final Long applId, final String statCd, final LocalDateTime finalApprYmd, final String modiType, final String modiReason, final Long modiDtmHisId, final Long modUserId, final LocalDateTime modDate, final String tzCd, final LocalDateTime tzDate, final String companyNm, final String document, final String adUseYn, final String mailSendYn, final String esbAskDt, final LocalDateTime childBirthYmd, final String dtmStoreYn, final Long fileId, final String sealMgrYn, final String secuMgrYn, final String infoMgrYn, final String safeMgrYn, final String placeCd, final int limit, final int offset, final String dtmReasonNm, final String statCdNm) {
        super(dtmHisId, empId, dtmKindCd, dtmReasonCd, staYmd, staHm, endYmd, endHm, dtmReason, destPlc, telno, childNo, applId, statCd, finalApprYmd, modiType, modiReason, modiDtmHisId, modUserId, modDate, tzCd, tzDate, companyNm, document, adUseYn, mailSendYn, esbAskDt, childBirthYmd, dtmStoreYn, fileId, sealMgrYn, secuMgrYn, infoMgrYn, safeMgrYn, placeCd);
        this.limit = limit;
        this.offset = offset;
        this.status = calculateStatus(staYmd, staHm, endYmd, endHm);
        this.dtmReasonNm = dtmReasonNm;
        this.statCdNm = statCdNm;
    }

    /***
     * @author 140024
     * @implNote 경과여부 (과거/진행중/예정) 세팅
     * @since 2024-06-09
     */
    public final String calculateStatus(final LocalDateTime staYmd, final String staHm, final LocalDateTime endYmd, final String endHm) {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime startDateTime = LocalDateTime.of(staYmd.toLocalDate(), LocalTime.parse(staHm, DateTimeFormatter.ofPattern("HHmm")));
        final LocalDateTime endDateTime;
        if (midnight.equals(endHm)) {
            // endHm이 2400이면 +1일 00시로 설정
            endDateTime = LocalDateTime.of(endYmd.toLocalDate().plusDays(1), LocalTime.MIDNIGHT);
        } else {
            endDateTime = LocalDateTime.of(endYmd.toLocalDate(), LocalTime.parse(endHm, DateTimeFormatter.ofPattern("HHmm")));
        }

        if (now.isBefore(startDateTime)) {
            this.status = "예정";
        } else if (now.isAfter(endDateTime)) {
        	this.status = "과거";
        } else {
        	this.status = "진행";
        }
        
        return status;
    }
}
