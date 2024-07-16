package epams.domain.dtm.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DtmHisDTO {

    private Long dtmHisId; // 근태내역ID
    private Long empId; // 직원ID
    private String dtmKindCd; // 근태종류코드 [DTM_KIND_CD]
    private String dtmReasonCd; // 근태사유코드 [DTM_REASON_CD]
    private LocalDateTime staYmd; // 시작일
    private String staHm = "0000"; // 시작시각
    private LocalDateTime endYmd; // 종료일
    private String endHm = "2400"; // 종료시각
    private String dtmReason; // 근태사유
    private String destPlc; // 행선지
    private String telno; // 연락처
    private String childNo; // 아이순번
    private Long applId = 0L; // 신청서ID
    private String statCd = "131"; // 신청서상태코드 [ELA_STAT_CD]
    private LocalDateTime finalApprYmd; // 최종승인일
    private String modiType; // 수정구분(UPDATE:변경,DELETE:취소)
    private String modiReason; // 수정사유
    private Long modiDtmHisId; // 수정근태내역ID
    private Long modUserId; // 변경자
    private LocalDateTime modDate; // 변경일시
    private String tzCd = "KR"; // 타임존코드
    private LocalDateTime tzDate; // 타임존일시
    private String companyNm; // 회사명
    private String document; // 문서
    private String adUseYn; // 선연차사용여부
    private String mailSendYn; // 메일발송여부
    private String esbAskDt; // ESB요청일시
    private LocalDateTime childBirthYmd; // 자녀출생일
    private String dtmStoreYn; // 연차저축여부
    private Long fileId; // 첨부파일ID
    private String sealMgrYn; // 인장담당자여부
    private String secuMgrYn; // 보안담당자여부
    private String infoMgrYn; // 정보취급관리자
    private String safeMgrYn; // 보안관리자
    private String placeCd; // 스마트워크 장소코드 [DTM_SMART_PLACE_CD]

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
