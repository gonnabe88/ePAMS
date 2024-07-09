package epams.dtm.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DtmHisDTO {

    private Long DTM_HIS_ID; // 근태내역ID
    private Long EMP_ID; // 직원ID
    private String DTM_KIND_CD; // 근태종류코드 [DTM_KIND_CD]
    private String DTM_REASON_CD; // 근태사유코드 [DTM_REASON_CD]
    private LocalDateTime STA_YMD; // 시작일
    private String STA_HM; // 시작시각
    private LocalDateTime END_YMD; // 종료일
    private String END_HM; // 종료시각
    private String DTM_REASON; // 근태사유
    private String DEST_PLC; // 행선지
    private String TELNO; // 연락처
    private String CHILD_NO; // 아이순번
    private Long APPL_ID; // 신청서ID
    private String STAT_CD; // 신청서상태코드 [ELA_STAT_CD]
    private LocalDateTime FINAL_APPR_YMD; // 최종승인일
    private String MODI_TYPE; // 수정구분(UPDATE:변경,DELETE:취소)
    private String MODI_REASON; // 수정사유
    private Long MODI_DTM_HIS_ID; // 수정근태내역ID
    private Long MOD_USER_ID; // 변경자
    private LocalDateTime MOD_DATE; // 변경일시
    private String TZ_CD; // 타임존코드
    private LocalDateTime TZ_DATE; // 타임존일시
    private String COMPANY_NM; // 회사명
    private String DOCUMENT; // 문서
    private String AD_USE_YN; // 선연차사용여부
    private String MAIL_SEND_YN; // 메일발송여부
    private String ESB_ASK_DT; // ESB요청일시
    private LocalDateTime CHILD_BIRTH_YMD; // 자녀출생일
    private String DTM_STORE_YN; // 연차저축여부
    private Long FILE_ID; // 첨부파일ID
    private String SEAL_MGR_YN; // 인장담당자여부
    private String SECU_MGR_YN; // 보안담당자여부
    private String INFO_MGR_YN; // 정보취급관리자
    private String SAFE_MGR_YN; // 보안관리자
    private String PLACE_CD; // 스마트워크 장소코드 [DTM_SMART_PLACE_CD]

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
