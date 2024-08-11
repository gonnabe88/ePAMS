package pams.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/***
 * @author 140024
 * @implNote 근태 이력 테이블 정의 VO
 * @since 2024-06-09
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DtmHisVO {

    /***
     * @author 140024
     * @implNote 근태내역ID
     * @since 2024-08-11
     */
    private Long dtmHisId;

    /***
     * @author 140024
     * @implNote 직원ID
     * @since 2024-08-11
     */
    private Long empId;

    /***
     * @author 140024
     * @implNote 근태종류코드 [DTM_KIND_CD]
     * @since 2024-08-11
     */
    private String dtmKindCd;

    /***
     * @author 140024
     * @implNote 근태사유코드 [DTM_REASON_CD]
     * @since 2024-08-11
     */
    private String dtmReasonCd;

    /***
     * @author 140024
     * @implNote 시작일
     * @since 2024-08-11
     */
    private LocalDateTime staYmd;

    /***
     * @author 140024
     * @implNote 시작시각
     * @since 2024-08-11
     */
    private String staHm = "0000";

    /***
     * @author 140024
     * @implNote 종료일
     * @since 2024-08-11
     */
    private LocalDateTime endYmd;

    /***
     * @author 140024
     * @implNote 종료시각
     * @since 2024-08-11
     */
    private String endHm = "2400";

    /***
     * @author 140024
     * @implNote 근태사유
     * @since 2024-08-11
     */
    private String dtmReason;

    /***
     * @author 140024
     * @implNote 행선지
     * @since 2024-08-11
     */
    private String destPlc;

    /***
     * @author 140024
     * @implNote 연락처
     * @since 2024-08-11
     */
    private String telno;

    /***
     * @author 140024
     * @implNote 아이순번
     * @since 2024-08-11
     */
    private String childNo;

    /***
     * @author 140024
     * @implNote 신청서ID
     * @since 2024-08-11
     */
    private Long applId = 0L;

    /***
     * @author 140024
     * @implNote 신청서상태코드 [ELA_STAT_CD]
     * @since 2024-08-11
     */
    private String statCd = "121";

    /***
     * @author 140024
     * @implNote 최종승인일
     * @since 2024-08-11
     */
    private LocalDateTime finalApprYmd;

    /***
     * @author 140024
     * @implNote 수정구분(UPDATE:변경,DELETE:취소)
     * @since 2024-08-11
     */
    private String modiType;

    /***
     * @author 140024
     * @implNote 수정사유
     * @since 2024-08-11
     */
    private String modiReason;

    /***
     * @author 140024
     * @implNote 수정근태내역ID
     * @since 2024-08-11
     */
    private Long modiDtmHisId;

    /***
     * @author 140024
     * @implNote 변경자
     * @since 2024-08-11
     */
    private Long modUserId;

    /***
     * @author 140024
     * @implNote 변경일시
     * @since 2024-08-11
     */
    private LocalDateTime modDate;

    /***
     * @author 140024
     * @implNote 타임존코드
     * @since 2024-08-11
     */
    private String tzCd = "KST";

    /***
     * @author 140024
     * @implNote 타임존일시
     * @since 2024-08-11
     */
    private LocalDateTime tzDate;

    /***
     * @author 140024
     * @implNote 회사명
     * @since 2024-08-11
     */
    private String companyNm;

    /***
     * @author 140024
     * @implNote 문서
     * @since 2024-08-11
     */
    private String document;

    /***
     * @author 140024
     * @implNote 선연차사용여부
     * @since 2024-08-11
     */
    private String adUseYn;

    /***
     * @author 140024
     * @implNote 메일발송여부
     * @since 2024-08-11
     */
    private String mailSendYn;

    /***
     * @author 140024
     * @implNote ESB요청일시
     * @since 2024-08-11
     */
    private String esbAskDt;

    /***
     * @author 140024
     * @implNote 자녀출생일
     * @since 2024-08-11
     */
    private LocalDateTime childBirthYmd;

    /***
     * @author 140024
     * @implNote 연차저축여부
     * @since 2024-08-11
     */
    private String dtmStoreYn;

    /***
     * @author 140024
     * @implNote 첨부파일ID
     * @since 2024-08-11
     */
    private Long fileId;

    /***
     * @author 140024
     * @implNote 인장담당자여부
     * @since 2024-08-11
     */
    private String sealMgrYn;

    /***
     * @author 140024
     * @implNote 보안담당자여부
     * @since 2024-08-11
     */
    private String secuMgrYn;

    /***
     * @author 140024
     * @implNote 정보취급관리자
     * @since 2024-08-11
     */
    private String infoMgrYn;

    /***
     * @author 140024
     * @implNote 보안관리자
     * @since 2024-08-11
     */
    private String safeMgrYn;

    /***
     * @author 140024
     * @implNote 스마트워크 장소코드 [DTM_SMART_PLACE_CD]
     * @since 2024-08-11
     */
    private String placeCd;
}
