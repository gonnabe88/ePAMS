package epams.domain.com.eai.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.i18n.LocaleContextHolder;

import epams.model.vo.BaseVO;
import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/***
 * @author 140024
 * @implNote EAI 전송을 위한 DTO
 * @since 2024-09-05
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EaiDTO extends BaseVO{

    /***
     * @author 140024
     * @implNote 온라인 연계 파라미터
     * @since 2024-09-05
     */
    private String system = "EAI";
    private String templId;         //템플릿 ID 
    private String emplNum;         //수신자 행번 (퇴직자 금지)
    private String reqCh;           //수신자 채널 (번호/이메일/DM, UMS전용)
    private Long grpNo;
    private Long crsTermSeq;
    private String umData1;
    private String umData2;
    private String umData3;
    private String umData4;
    private String umData5;
    private String umData6;
    private String umData7;

    //DB 발송
    private String sendDt;      //발송일자
    private String sendTime;    //발송시간
    private String sendType;

    //TBL_MEMIBER
    private Long trnCtrMbrSeq;
    private String nm;
    private String deptKey;
    private String deptNm;

    //TBL_NOTIFICATION_SEND_HISTORY
    private Long armSendHstSeq;
    private Long rcvTrnCtrMbrSeq;
    private Long armTplSeq;
    //private CodeNotifSendDiv notifSendDivCd;
    private String notifTitle;
    private String notifCnts;
    private String sendPhone;
    private String sendNm;
    private String sendEmail;
    private String sendDttm;
    private String sendRsrvtnYn;
    private String rcvPhone;
    private String rcvEmail;
    private String sendScsYn;
    private String rcvScsYn;
    private String autoSendYn;
    private String readYn;
    private Long sendGrpSeq;
    private String ifNo;
    //private String umsBzDttId;
    private String sendIfDivNm;
    //private String delYn;

    //UMS Interface Layout
    private String umsBzDttId = ""; //UMS_BZ_DTT_ID	        UMS업무구분ID
    private String trDt;            //TR_DT                 거래일자
    private String umsTrSno = "";   //UMS_TR_SNO	        UMS거래일련번호
    private String umsRetNo;        //UMS_RET_NO	        UMS접수번호
    private String cno;             //CNO	                고객번호
    private String cstNm;           //CST_NM	            고객명
    private String sectEmlCnfmNo;   //SECT_EML_CNFM_NO	    보안이메일확인번호
    private String umsSdChnNo;      //UMS_SD_CHN_NO	        UMS발송채널번호
    private String umsSdChnAddrCone;//UMS_SD_CHN_ADDR_CONE	UMS발송채널주소내용
    private String umsSdMsgClsfTc;  //UMS_SD_MSG_CLSF_TC	UMS발송메세지분류구분코드
    private String umsSdMplDt = new SimpleDateFormat("yyyyMMdd", LocaleContextHolder.getLocale()).format(new Date());;      //UMS_SD_MPL_DT	        UMS발송예정일자
    private String umsSdMplTm;              //UMS_SD_MPL_TM	    UMS발송예정시각
    private String appC = "HUR";            //APP_C	            어플리케이션코드
    private String appBzLv1C = "XH";        //APP_BZ_LV1_C	    어플리케이션업무1레벨코드
    private String reqUsid = "SYSTEM";      //REQ_USID	        요청사용자ID
    private String reqUsrNm = "관리자";     //REQ_USR_NM	     요청사용자명
    private String reqBbrC = "182";         //REQ_BBR_C	        요청부점코드
    private String reqBbrNm = "디지털금융부";//REQ_BBR_NM	     요청부점명
    private String umsCkgC = "N";           //UMS_CKG_C	        UMS점검코드
    private String umsSdMsgInf;     //UMS_SD_MSG_INF	        UMS발송메시지정보
    private String trTm = new SimpleDateFormat("HHmmss", LocaleContextHolder.getLocale()).format(new Date()); //R_TM    거래시각
    private String umsSdRltpC;      //UMS_SD_RLT_C	        UMS발송결과코드
    private String umsSdChnTpC;     //UMS_SD_CHN_TP_C	    UMS발송채널유형코드
    private String chnReqTpC = "10";//CHN_REQ_TP_C	        채널요청유형코드
    private String chnSdRltDt;      //CHN_SD_RLT_DT	        채널발송결과일자
    private String chnSdRltTm;      //CHN_SD_RLT_TM	        채널발송결과시각
    private String umsRltSno;       //UMS_RLT_SNO	        UMS결과일련번호
    private String bzDcmInfCone;    //BZ_DCM_INF_CONE	    업무식별정보내용
    private String umsBzRfrCone;    //UMS_BZ_RFR_CONE	    UMS업무참조내용
    private String eaiFwdiYn;       //EAI_FWDI_YN	        EAI전송여부
    private String eaiPrcDtm;       //EAI_PRC_DTM	        EAI처리일시
    private String eaiRmsSysC;      //EAI_RMS_SYS_C	        EAI수신시스템코드
    private String apgFlCone;       //APG_FL_CONE	        첨부파일내용
    private String umsTmeChnNo;     //UMS_TME_CHN_NO	    UMS 송신채널번호
    private String umsReqSysC;      //UMS_REQ_SYS_C	        UMS 요청시스템코드
    private String umsReqLink;      //UMS_REQ_LINK	        UMS결과 링크

    private String bzCS3 = "EHR";   //업무코드_S3??? 

    private String ifSysC;          //인터페이스 시스템코드
    private String ifId = "";       //인터페이스 ID
}