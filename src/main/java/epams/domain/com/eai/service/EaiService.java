package epams.domain.com.eai.service;

import epams.domain.com.eai.dto.EaiDTO;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.util.HtmlUtils;

import java.io.ByteArrayOutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;

/**
 * @author K140024
 * @implNote 게시판 service
 * @since 2024-04-26
 */
@Slf4j
@Transactional
@Service
//@RequiredArgsConstructor
public class EaiService {


    //private final EaiMapper eaiMapper;

    @Value("${kdb.eaiUrl}")
    private String eaiUrl;

    @Value("${spring.profiles.active}")
    private String profile;


    public EaiDTO sendEAI(EaiDTO request) {
        byte[] reqData; // 요청 데이터(표준전문) 생성.

        try {
            reqData = getReqData(request);
        } catch (IndexOutOfBoundsException e) {
            log.info("sendEAI, 01");
            //log.info("{}", LogUtil.getFullLogMessage(e));
            return null;
        }

        String urlStr = eaiUrl;

        byte[] rtnData = null;
        HttpURLConnection conn = null;
        OutputStream os = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;


        //log.warn("reqData (70) : " + new String(reqData, StandardCharsets.UTF_8));
        //log.warn("reqData (70) : " + new String(reqData));


        try {
            // 요청 처리
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3 * 1000); // 연결 타임아웃 3 sec
            conn.setReadTimeout(3 * 1000); // 거래 타임아웃
            conn.addRequestProperty("Content-Type", "application/octet-stream");
            conn.addRequestProperty("Content-Length", "" + reqData.length);
            System.out.println("HTTP LEGNTH" + reqData.length);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            os = conn.getOutputStream();
            System.out.println("HTTP REQ[" + urlStr + "][" + new String(reqData) + "]");
            os.write(reqData);
            os.flush();
            System.out.println("HTTP Response [" + conn.getResponseCode() + "]" + conn.getResponseMessage());

            // 응답 처리
            is = conn.getInputStream();
            int readLen = -1;
            byte[] buffer = new byte[1024];
            baos = new ByteArrayOutputStream();
            do {
                readLen = is.read(buffer);
                if (readLen < 0) {
                    break;
                }
                baos.write(buffer, 0, readLen);
            } while (readLen != -1);

            rtnData = baos.toByteArray();
            System.out.println("HTTP RES[" + urlStr + "][" + new String(rtnData) + "]");
        } catch (IOException e) {
            log.info("sendEAI, 02");
            log.info("{}", e.getMessage());
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException ee) {
                    log.info("sendEAI, 03");
                    log.info("{}", ee.getMessage());
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException ee) {
                    log.info("sendEAI, 04");
                    log.info("{}", ee.getMessage());
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ee) {
                    log.info("sendEAI, 05");
                    log.info("{}", ee.getMessage());
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return new EaiDTO();
    }
    private byte[] getReqData(EaiDTO request) throws IndexOutOfBoundsException {
        String param01 = getParam01();          //01. 시스템공통부
        String param02 = getParam02(request);   //02. 거래공통부
        String param03 = getParam03();          //03. 채널공통부
        String param04 = getParam04();          //04. 책임자승인공통부
        String param05 = getParam05();          //05. 메시지공통부
        String param06 = getParam06();          //06. 출력매체부
        String param07 = getParam07(request);   //07. 개별부(입력데이터)
        String param08 = "@@";                  //08. 종료부

        // System.out.println(param01.getBytes().length);
        // System.out.println(param02.getBytes().length);
        // System.out.println(param03.getBytes().length);
        // System.out.println(param04.getBytes().length);
        // System.out.println(param05.getBytes().length);
        // System.out.println(param06.getBytes().length);
        // System.out.println(param07.getBytes().length);
        // System.out.println(param08.getBytes().length);
        // System.out.println(param01.getBytes().length + param02.getBytes().length + param03.getBytes().length + param04.getBytes().length + param05.getBytes().length + param06.getBytes().length + param07.getBytes().length + param08.getBytes().length);
        

        //전체전문길이 (01 ~ 08)
        String whlTgrLe = lpad("N", 8, String.valueOf((param01 + param02 + param03 + param04 + param05 + param06 + param07 + param08).getBytes().length));

        //헤더길이 (01 ~ 06)
        String herLen = lpad("N", 8, String.valueOf((param01 + param02 + param03 + param04 + param05 + param06).getBytes().length));

        //출력매체부길이 (06)
        String proMdaLen = lpad("N", 8, String.valueOf(param06.getBytes().length));

        String param = "";
        param += whlTgrLe + herLen + proMdaLen + param01.substring(24); //앞에서 24자리 교체
        param += param02;
        param += param03;
        param += param04;
        param += param05;
        param += param06;
        param += param07;
        param += param08;

        return param.getBytes();
    }

    //01. 시스템공통부
    private String getParam01() throws IndexOutOfBoundsException {
        //guid
        String dt = new SimpleDateFormat("yyyyMMdd", LocaleContextHolder.getLocale()).format(new Date());
        String dt2 = new SimpleDateFormat("HHmmssSSS", LocaleContextHolder.getLocale()).format(new Date());
        String num = getRandomNum(9);
        String num2 = getRandomNum(9);
        String guid = "EHR" + dt + dt2 + num + num2;

        //IP주소, MAC주소
        String ipAddr = getIpAddr();
        String macAddr = getMacAddr();

        String param = "";
        param += lpad("N", 8, "");      //WHL_TGR_LEN		전체전문길이
        param += lpad("N", 8, "");      //HER_LEN			헤더길이
        param += lpad("N", 8, "");      //PRO_MDA_LEN		출력매체부길이
        param += "1.0";                                //TGR_VRS_INF		전문버전정보
        param += "ko";                                 //MLAN_TC			다국어구분코드
        param += "prod".equals(profile) ? "P" : "L";   //SYS_ENV_TC		    시스템환경구분코드
        param += ipAddr;                               //IP_ADDR			IP주소
        param += macAddr;                              //MAC_ADDR			MAC주소
        param += guid;                                 //GUID				GUID
        param += "0001";                               //GUID_PRG_SNO		GUID진행일련번호
        param += guid;                                 //FST_GUID			최초GUID
        param += "EHR";                                //FWDI_SYS_C		    전송시스템코드
        param += "EHR";                                //FST_FWDI_SYS_C	    최초전송시스템코드
        param += lpad("C", 12, "");     //SYS_CO_RSRV		시스템공통부 예비

        return param;
    }

    //02. 거래공통부
    private String getParam02(EaiDTO request) throws IndexOutOfBoundsException {
        //요청일시, 거래영업일자
        String reqDtm = new SimpleDateFormat("yyyyMMddHHmmssSSS", LocaleContextHolder.getLocale()).format(new Date());
        String trSlsDt = new SimpleDateFormat("yyyyMMdd", LocaleContextHolder.getLocale()).format(new Date());

        //수신시스템코드, 인터페이스ID
        String rmsSysC = request.getSystem();
        String ifId = request.getIfId();

        String param = "";                              //필드명	                    필드설명	                Type	Length	Offset  값(예제로 받은 값, 알맞게 변경)
        param += lpad("C", 10, "");     //TR_ID						거래 ID					C		10		180
        param += lpad("C", 3, rmsSysC);     //RMS_SYS_C				    수신시스템코드				C		3		190		EAI
        param += lpad("C", 10, "");     //SRE_ID					화면ID					C		10		193
        param += lpad("C", 10, "");     //LKG_SRE_ID				연계화면ID				C		10		203
        param += lpad("C", 1, "");      //SRE_CNTR_TC				화면제어구분코드			C		1		213
        param += "Q";                                   //REQ_RPD_TC				요청/응답구분코드			C		1		214		Q
        param += "2";                                   //DTLS_TP_TC				세부유형구분코드			C		1		215		1
        param += "TR";                                  //CHN_TP_C					채널유형코드				C		2		216		TR
        param += lpad("C", 2, "");      //MSG_CHN_C					메시지채널코드				C		2		218
        param += "S";                                   //SYNC_PRC_TC				동기처리구분코드			C		1		220		S
        param += lpad("C", 1, "");      //RLT_TC					결과구분코드				C		1		221
        param += "00000";                               //PAGE_ROW_COUNT			원페이지당조회건수			N		5		222		00000
        param += lpad("C", 1, "");      //NEXT_PAGE_YN				다음페이지여부				C		1		227
        param += "00000";                               //REQ_PAGE_NO				요청페이지번호				N		5		228		00000
        param += reqDtm;                                //REQ_DTM					요청일시					C		17		233		20220406093532813
        param += lpad("C", 17, "");     //RPD_DTM					응답일시					C		17		250
        param += trSlsDt;                               //TR_SLS_DT					거래영업일자				C		8		267		20220406
        param += "0";                                   //TR_SLS_DT_TC				거래영업일자구분코드		C		1		275		0
        param += "0";                                   //SML_TC					시뮬레이션구분코드			C		1		276		0
        param += lpad("C", 8, "");      //SML_SLS_YMD				시뮬레이션영업년월일		C		8		277
        param += "N";                                   //MSK_CCC_YN				마스킹해지여부				C		1		285		N
        param += "N";                                   //ATH_TES_ALY_YN			권한테스트적용여부			C		1		286		N
        param += "N";                                   //LQN_TR_YN					대량거래여부				C		1		287		N
        param += lpad("C", 1, "");      //RE_TR_FLAG				재거래플래그				C		1		288
        param += "01";                                  //CSG_TC					마감전후구분코드			C		2		289		01
        param += "10";                                  //FSC_DT_BSE_TC				회계일자기준구분코드		C		2		291		10
        param += lpad("C", 3, "");      //BLGT_BBR_C				소속부점코드				C		3		293
        param += lpad("C", 14, "");     //USID						사용자ID					C		14		296
        param += lpad("C", 4, "");      //PSC_C						직급코드					C		4		310
        param += lpad("C", 4, "");      //DTS_C						직무코드					C		4		314
        param += lpad("C", 3, "");      //TMN_ITL_BBR_C				단말설치부점코드			C		3		318
        param += lpad("C", 10, "");     //TMN_NO					단말번호					C		10		321
        param += lpad("C", 3, "");      //AAP_BBR_C					대행부점코드				C		3		331
        param += lpad("C", 10, "");     //NML_PRC_RMS_TR_ID			정상처리수신거래ID			C		10		334
        param += lpad("C", 10, "");     //FRM_ERR_RMS_TR_ID			포멧오류수신거래ID			C		10		344
        param += lpad("C", 10, "");     //NRPD_RMS_TR_ID			무응답수신거래ID			C		10		354
        param += lpad("C", 4, "");      //FOOE_IST_C				대외기관코드				C		4		364
        param += lpad("C", 4, "");      //FOOE_NEK_TC				대외망구분코드				C		4		368
        param += lpad("C", 3, "");      //FOOE_HED_TP_TC			대외헤더유형구분코드		C		3		372
        param += lpad("C", 8, "");      //FOOE_REQ_BYCL_C			대외요청종별코드			C		8		375
        param += lpad("C", 20, "");     //FOOE_REQ_TR_TC			대외요청거래구분코드		C		20		383
        param += lpad("C", 1, "");      //ONLD_CNTR_TP_TC			온렌딩제어구분코드			C		1		403
        param += lpad("C", 4, "");      //ADN_FOOE_IST_CD			부가대외기관코드			C		4		404
        param += lpad("C", 12, ifId);       //IF_ID		                인터페이스ID				C		12		408		CBKO00025384
        param += "00";                                  //EAI_FWDI_SVR_NO			EAI전송서버번호			N		2		420		00
        param += "11";                                  //MCI_FWDI_SVR_NO			MCI전송서버번호			N		2		422		11
        param += lpad("C", 10, "");     //MCI_SES_ID				MCI세션ID				C		10		424
        param += lpad("C", 10, "");     //CC_ID						센터컷ID					C		10		434
        param += lpad("C", 8, "");      //CC_PRC_DT					센터컷처리일자				C		8		444
        param += "00000";                               //CC_PRC_TO					센터컷처리회차				N		5		452		00000
        param += "000000000";                           //CC_TD_SNO					센터컷회차별일련번호		N		9		457		000000000
        param += "00";                                  //CC_PRC_TC					센터컷처리구분코드			C		2		466		00
        param += lpad("C", 10, "");     //CC_CALL_TR_ID				센터컷호출거래ID			C		10		468
        param += lpad("C", 1, "");      //RSD_CC_YN					상주센터컷여부				C		1		478
        param += lpad("C", 10, "");     //DTL_CC_ID					상세CC_ID				C		10		479
        param += lpad("C", 2, "");      //CC_PRC_RLT_C				센터컷처리결과코드			C		2		489
        param += "0000000000000000.000";                //CC_ACL_TFR_AMT			센터컷실제이체금액			N		20		491		0000000000000000.000
        param += "0000000000000000.000";                //CC_TFR_TGT_AMT			센터컷이체대상금액			N		20		511		0000000000000000.000
        param += lpad("C", 1, "");      //CC_XTR_DETS_RE_ROM_YN     센터컷별도예금재입금여부	C		1		531
        param += lpad("C", 8, "");      //CST_NO					고객번호					C		8		532
        param += lpad("C", 40, "");     //TR_CO_RSRV				거래공통부 예비			C		40		540

        return param;
    }

    //03. 채널공통부
    private String getParam03() throws IndexOutOfBoundsException {
        String param = "";                              //필드명	                필드설명	                Type	Length	Offset  값(예제로 받은 값, 알맞게 변경)
        param += "2";                                   //BKB_TC				통장구분코드				C		1		580		2
        param += lpad("C", 20, "");     //BKB_CWT_NO			통장증서번호				C		20		581
        param += lpad("C", 129, "");    //CSF_CHN_RSRV			대면채널예비				C		129		601
        param += lpad("C", 2, "");      //NFF_CNC_MDA_KD_C		비대면접속매체종류코드		C		2		730
        param += "00";                                  //CHN_TP_ADN_C			채널유형부가코드			C		2		732		00
        param += lpad("C", 64, "");     //NFF_CNC_MCN_ID		비대면접속기기ID			C		64		734
        param += lpad("C", 2, "");      //NFF_ISO_NTN_SYM_C		비대면ISO국가기호코드		C		2		798
        param += lpad("C", 5, "");      //NFF_CIR_NO			비대면회선번호				C		5		800
        param += lpad("C", 4, "");      //NFF_CHN_BZ_TC			비대면채널업무구분코드		C		4		805
        param += lpad("C", 10, "");     //IB_SRE_MNU_ID			인터넷뱅킹화면메뉴ID		C		10		809
        param += lpad("C", 3, "");      //TELB_SVC_C			텔레뱅킹서비스코드			C		3		819
        param += lpad("C", 1, "");      //XP_LGN_TR_YN			비로그인거래여부			C		1		822
        param += lpad("C", 1, "");      //NFF_CST_TC			비대면고객구분코드			C		1		823
        param += lpad("C", 8, "");      //USR_NO				사용자번호				C		8		824
        param += lpad("C", 1, "");      //ADMR_ATH_YN			관리인권한여부				C		1		832
        param += lpad("C", 2, "");      //SECT_MDA_TC			보안매체구분코드			C		2		833
        param += lpad("C", 1, "");      //CFA_KPN_MDA_TC		인증서저장매체구분코드		C		1		835
        param += lpad("C", 2, "");      //MCT_TC				주계약구분코드				C		2		836
        param += lpad("C", 1, "");      //USR_CER_MANR_C		사용자인증방법코드			C		1		838
        param += "N";                                   //CSR_TPR_CNFM_FLAG     상담원본인확인플래그		C		1		839		N
        param += lpad("C", 38, "");     //CSR_TPR_CNFM_NO		상담원본인확인번호			C		38		840
        param += lpad("C", 102, "");    //NFF_CHN_RSRV			비대면채널예비				C		102		878

        return param;
    }

    //04. 책임자승인공통부
    private String getParam04() throws IndexOutOfBoundsException {
        String param = "";                          //필드명                필드설명	               Type     Length  Offset  값(예제로 받은 값, 알맞게 변경)
        param += "00";                              //RSPR_APV_STS_TC		책임자승인상태구분코드     C		2		980		00
        param += "00";                              //RSPR_APV_LEV_NBR		책임자승인레벨수		   N		2		982		00
        param += lpad("C", 2, "");  //RSPR_TR_TC			책임자거래구분코드		   C		2		984
        param += "00";                              //RSPR_APV_MSG_CNT		책임자승인메시지건수	   N		2		986		00
        param += "00";                              //RSPR_CNT				책임자건수			      N		2		988		00
        param += "00";                              //RSPR_APV_DKEY_CNT     책임자승인문서키건수	   N		2		990		00
        param += "00";                              //APV_RSPR_CNT			승인책임자건수		       N		2		992		00
        param += lpad("C", 18, ""); //APV_TR_SRE_DKEY		승인거래화면문서키		   C		18		994
        param += lpad("C", 29, ""); //RSPR_APV_RSRV			책임자승인예비		       C		29		1012
        param += "00";                              //IDFC_MNG_CNT			신분증관리건수		       N		2		1041	00

        return param;
    }

    //05. 메시지공통부
    private String getParam05() throws IndexOutOfBoundsException {
        String param = "";                              //필드명	             필드설명	                Type    Length	Offset  값(예제로 받은 값, 알맞게 변경)
        param += lpad("C", 1, "");      //MSG_IDCT_TC	        메시지표시방법구분코드	    C	    1	    1043
        param += lpad("C", 50, "");     //ERR_OCC_TGR_ITM       오류발생전문항목	        C	    50	    1044
        param += "000";                                 //MSG_CNT	            메시지건수	                N	    3	    1094	000
        param += "00";                                  //ETC_PRO_DAT_CNT	    기타출력데이터건수	        N	    2	    1097	00

        return param;
    }

    //06. 출력매체부
    private String getParam06() {
        String param = "";                      //필드명	        필드설명        Type	Length	Offset  값(예제로 받은 값, 알맞게 변경)
        param += "000";                         //PRO_MDA_CNT   출력매체건수	   N	3	    1099	000

        return param;
    }

    //07. 개별부(입력데이터)
    private String getParam07(EaiDTO request) throws IndexOutOfBoundsException {
        String param = "";

        if ("UMS".equals(request.getSystem())) {
            param = getParamUMS(request);
        } else {
            //param = getParamGWE(request);
        }

        return param;
    }

    private String getParamUMS(EaiDTO request) throws IndexOutOfBoundsException {
        //request
        String umsBzDttId = request.getUmsBzDttId();   //템플릿 ID (TBL_NOTIFICATION_TEMPLATE.TPL_ID)
        String reqUsid = request.getEmplNum();      //수신자 행번 (퇴직자 금지)
        String umsSdChnNo = request.getReqCh();     //수신자 채널 (번호/이메일/DM, UMS전용)
        String sendDt = request.getSendDt();        //발송일자
        String sendTime = request.getSendTime();    //발송시간

        //거래일자, 거래시각
        String trDt = new SimpleDateFormat("yyyyMMdd", LocaleContextHolder.getLocale()).format(new Date());
        String trTm = new SimpleDateFormat("HHmmss", LocaleContextHolder.getLocale()).format(new Date());

        //UMS거래일련번호, UMS접수번호
        String umsTrSno = request.getUmsTrSno();
        String umsRetNo = umsBzDttId + trDt + String.format("%08d", Integer.parseInt(umsTrSno));    //템플릿ID(7) + 연월일(8) + 일련번호(8)UMS_TR_SNO

        //UMS송신채널번호 (SMS~, ALT~, EML~)
        String umsTmeChnNo = "1588-1500";
        if ("E".equals(umsBzDttId.substring(0, 1))) { //이메일
            umsTmeChnNo = "hrd@kdb.co.kr";
        }

        //요청사용자명, 요청부점코드, 요청부점명
        //EaiDTO user = eaiMapper.getMember(request);
        String reqUsrNm = request.getCstNm();
        String reqBbrC = request.getDeptKey();
        String reqBbrNm = request.getDeptNm();

        //UMS발송채널유형코드 (SMS(S), ALT(A), EML(M))
        String umsSdChnTpC = umsBzDttId.substring(0, 1);
        if ("E".equals(umsSdChnTpC)) {
            umsSdChnTpC = "M";
        }

        //가변데이터_S0, 가변데이터길이_N9
        String variDatS0 = setVariDatS0(request);
        String variDatLenN9 = String.valueOf(variDatS0.getBytes().length);

        String param = "";  
        param += lpad("C", 7, umsBzDttId);  //UMS_BZ_DTT_ID UMS업무구분ID
        param += trDt;  //TR_DT 거래일자
        param += lpad("N", 10, umsTrSno);   //UMS_TR_SNO    UMS거래일련번호	
        param += lpad("C", 23, umsRetNo);   //UMS_RET_NO    UMS접수번호
        param += lpad("C", 8, reqUsid); //CNO   고객번호
        param += lpad("C", 100, reqUsrNm);  //CST_NM    고객명
        param += lpad("C", 30, ""); //SECT_EML_CNFM_NO  보안이메일확인번호
        param += lpad("C", 200, umsSdChnNo);    //UMS_SD_CHN_NO UMS발송채널번호
        param += lpad("C", 4000, "");   //UMS_SD_CHN_ADDR_CONE  UMS발송채널주소내용
        param += lpad("C", 8, Objects.isNull(sendDt) ? trDt : sendDt);  //UMS_SD_MPL_DT UMS발송예정일자
        param += lpad("C", 6, (Objects.isNull(sendTime) ? "" : sendTime));  //UMS_SD_MPL_TM UMS발송예정시각
        param += lpad("C", 100, umsTmeChnNo);   //UMS_TME_CHN_NO    UMS송신채널번호
        param += "HUR"; //APP_C 어플리케이션코드
        param += "XH";  //APP_BZ_LV1_C  어플리케이션업무1레벨코드
        param += lpad("C", 14, reqUsid);    //REQ_USID  요청사용자ID
        param += lpad("C", 100, reqUsrNm);  //REQ_USR_NM    요청사용자명
        param += lpad("C", 3, reqBbrC); //REQ_BBR_C 요청부점코드
        param += lpad("C", 100, reqBbrNm);  //REQ_BBR_NM    요청부점명
        param += "N";   //UMS_CKG_C UMS점검코드
        param += lpad("C", 4000, "");   //APG_FL_CONE   첨부파일내용
        param += lpad("C", 6, trTm);    //TR_TM 거래시각
        param += umsSdChnTpC;   //UMS_SD_CHN_TP_C   UMS발송채널유형코드
        param += "10";  //CHN_REQ_TP_C  채널요청유형코드
        param += lpad("C", 100, "");    //BZ_DCM_INF_CONE   업무식별정보내용
        param += lpad("C", 1000, "");   //UMS_BZ_RFR_CONE   UMS업무참조내용
        param += lpad("C", 1, "N"); //DEL_YN    삭제여부
        param += lpad("C", 14, "SYSTEM");   //LST_CHG_USID  최종변경사용자ID
        param += "EHR"; //BZ_C_S3   UMS요청시스템코드
        param += lpad("N", 9, variDatLenN9);    //VARI_DAT_LEN_N9   가변데이터길이_N9
        param += variDatS0; //VARI_DAT_S0   가변데이터_S0

        return param;
    }

    public static String lpad(String type, int offset, String str) throws IndexOutOfBoundsException {
        String tmpStr = "";
        tmpStr = str;

        if (tmpStr == null) {
            tmpStr = "";
        }

        String pad = "C".equals(type) ? " " : "0";
        int len = offset - tmpStr.getBytes().length;

        if (len < 0) {
            throw new IndexOutOfBoundsException();
        }

        for (int i = 0; i < len; i++) {
            tmpStr = pad + tmpStr;
        }

        return tmpStr;
    }

    private String getIpAddr() {
        String ipAddr = String.format("%40s", "");

        try {
            ipAddr = String.format("%40s", InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            log.info("getIpAddr, 01");
        }

        return ipAddr;
    }

    private String getMacAddr() {
        String macAddr = String.format("%12s", "");

        try {
            InetAddress local = InetAddress.getLocalHost();
            NetworkInterface ni = NetworkInterface.getByInetAddress(local);

            if (ni != null) {
                byte[] mac = ni.getHardwareAddress();

                if (mac != null) {
                    macAddr = "";
                    for (int i = 0; i < mac.length; i++) {
                        macAddr += String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : "");
                    }
                }

                macAddr = macAddr.replaceAll("-", "");
                macAddr = String.format("%12s", macAddr);
            }
        } catch (UnknownHostException e) {
            log.info("getMacAddr, 01");
        } catch (SocketException e) {
            log.info("getMacAddr, 02");
        }

        return macAddr;
    }

    private String setVariDatS0(EaiDTO request) {
        JSONObject entries = new JSONObject();
        if (!ObjectUtils.isEmpty(request.getUmData1())) {
            entries.put("UM_DATA_1", request.getUmData1());
        }
        if (!ObjectUtils.isEmpty(request.getUmData2())) {
            entries.put("UM_DATA_2", request.getUmData2());
        }
        if (!ObjectUtils.isEmpty(request.getUmData3())) {
            entries.put("UM_DATA_3", request.getUmData3());
        }
        if (!ObjectUtils.isEmpty(request.getUmData4())) {
            entries.put("UM_DATA_4", request.getUmData4());
        }
        if (!ObjectUtils.isEmpty(request.getUmData5())) {
            entries.put("UM_DATA_5", request.getUmData5());
        }
        if (!ObjectUtils.isEmpty(request.getUmData6())) {
            entries.put("UM_DATA_6", request.getUmData6());
        }
        if (!ObjectUtils.isEmpty(request.getUmData7())) {
            entries.put("UM_DATA_7", request.getUmData7());
        }

        JSONObject json = new JSONObject();
        json.put("type", "dataSet");
        json.put("entries", entries);

        return json.toJSONString();
    }

    /*
    private void insertNotificationSendHistory(EaiDTO request, String sendIfDivNm, String ifNo) {
        //유저 가져오기
        EaiDTO user = eaiMapper.getMember(request);

        //템플릿 가져오기
        EaiDTO templ = eaiMapper.getNotificationTemplate(request);

        //발송타입 (IMD: 즉시, RSV: 예약)
        String sendType = (Objects.isNull(request.getSendType()) ? "IMD" : request.getSendType());

        //알림내용
        String notifCnts = HtmlUtils.htmlUnescape(templ.getNotifCnts());
        if (!ObjectUtils.isEmpty(request.getUmData1())) {
            notifCnts = notifCnts.replaceAll("UM_DATA_1", request.getUmData1());
        }
        if (!ObjectUtils.isEmpty(request.getUmData2())) {
            notifCnts = notifCnts.replaceAll("UM_DATA_2", request.getUmData2());
        }
        if (!ObjectUtils.isEmpty(request.getUmData3())) {
            notifCnts = notifCnts.replaceAll("UM_DATA_3", request.getUmData3());
        }
        if (!ObjectUtils.isEmpty(request.getUmData4())) {
            notifCnts = notifCnts.replaceAll("UM_DATA_4", request.getUmData4());
        }
        if (!ObjectUtils.isEmpty(request.getUmData5())) {
            notifCnts = notifCnts.replaceAll("UM_DATA_5", request.getUmData5());
        }
        if (!ObjectUtils.isEmpty(request.getUmData6())) {
            notifCnts = notifCnts.replaceAll("UM_DATA_6", request.getUmData6());
        }
        if (!ObjectUtils.isEmpty(request.getUmData7())) {
            notifCnts = notifCnts.replaceAll("UM_DATA_7", request.getUmData7());
        }

        //발송번호, 발송이메일
        String rcvPhone = "";
        String rcvEmail = "";
        if (templ.getNotifSendDivCd().equals(CodeNotifSendDiv.SMS)) {
            rcvPhone = request.getReqCh();
        } else if (templ.getNotifSendDivCd().equals(CodeNotifSendDiv.EMAIL)) {
            rcvEmail = request.getReqCh();
        } else if (templ.getNotifSendDivCd().equals(CodeNotifSendDiv.ALIMTALK)) {
            rcvPhone = request.getReqCh();
        }

        //알림발송이력 저장
        EaiDTO dto = new EaiDTO();
        dto.setTrnCtrSeq(1L);
        dto.setArmTplSeq(templ.getArmTplSeq());
        dto.setRcvTrnCtrMbrSeq(user != null ? user.getTrnCtrMbrSeq() : null);
        dto.setNotifSendDivCd(new CodeNotifSendDiv(templ.getNotifSendDivCd().getCdKey()));
        dto.setNotifTitle(templ.getNotifTitle());
        dto.setNotifCnts(notifCnts);
        dto.setSendPhone("1588-1500");
        dto.setSendNm("관리자");
        dto.setSendEmail("hrd@kdb.co.kr");
        dto.setSendDttm(request.getSendDttm());
        dto.setSendRsrvtnYn("IMD".equals(sendType) ? "N" : "Y");
        dto.setRcvPhone(rcvPhone);
        dto.setRcvEmail(rcvEmail);
        dto.setSendScsYn("Y");
        dto.setRcvScsYn("Y");
        dto.setAutoSendYn("N");
        dto.setReadYn("Y");
        dto.setSendGrpSeq(request.getGrpNo());
        dto.setIfNo(ifNo);
        dto.setUmsBzDttId(request.getTemplId());
        dto.setSendIfDivNm(sendIfDivNm);
        dto.setCrsTermSeq(request.getCrsTermSeq());
        dto.setDelYn("N");

        //eaiMapper.insertNotificationSendHistory(dto);
    }
 */
    private String getRandomNum(int len) {
        Random numGen = null;
        String num = "";

        try {
            numGen = SecureRandom.getInstance("SHA1PRNG");

            if (numGen != null) {
                if (len == 8) {
                    num = String.format("%08d", numGen.nextInt(99999999) + 1);
                } else if (len == 9) {
                    num = String.format("%09d", numGen.nextInt(999999999) + 1);
                }
            }
        } catch (NoSuchAlgorithmException e) {
            log.info("getRandomNum, 01");
        }

        return num;
    }

}

    /*
    public EaiDTO sendEAIDB(EaiDTO request) {
        //request
        String umsBzDttId = request.getTemplId();   //템플릿 ID (TBL_NOTIFICATION_TEMPLATE.TPL_ID)
        String reqUsid = request.getEmplNum();      //수신자 행번 (퇴직자 금지)
        String umsSdChnNo = request.getReqCh();     //수신자 채널 (번호/이메일/DM, UMS전용)
        String umsSdMplDt = request.getSendDt();    //발신 예정일 (YYYYMMDD)
        String umsSdMplTm = request.getSendTime();  //발신 예정시간 (null-즉시, HHMMSS-예약)
        String chnReqTpC = request.getSendType();   //발신 유형 (10-즉시, 20-예약)

        //거래일자, 거래시각
        String trDt = new SimpleDateFormat("yyyyMMdd", LocaleContextHolder.getLocale()).format(new Date());

        //UMS거래일련번호, UMS접수번호
        String umsTrSno = eaiMapper.notificationSendHistoryCnt(request);
        String umsRetNo = umsBzDttId + trDt + String.format("%08d", Integer.parseInt(umsTrSno));    //템플릿ID(7) + 연월일(8) + 일련번호(8)UMS_TR_SNO

        //UMS송신채널번호 (SMS~, ALT~, EML~)
        String umsTmeChnNo = "1588-1500";
        if ("E".equals(umsBzDttId.substring(0, 1))) { //이메일
            umsTmeChnNo = "hrd@kdb.co.kr";
        }

        //요청사용자명, 요청부점코드, 요청부점명
        EaiDTO user = eaiMapper.getMember(request);
        String reqUsrNm = user.getNm();
        String reqBbrC = user.getDeptKey();
        String reqBbrNm = user.getDeptNm();

        //UMS발송채널유형코드 (SMS(S), ALT(A), EML(M))
        String umsSdChnTpC = umsBzDttId.substring(0, 1);
        if ("E".equals(umsSdChnTpC)) {
            umsSdChnTpC = "M";
        }

        //가변데이터_S0, 가변데이터길이_N9
        String variDatS0 = setVariDatS0(request);
        String variDatLenN9 = String.valueOf(variDatS0.getBytes().length);

        //guid
        String dt = new SimpleDateFormat("yyyyMMdd", LocaleContextHolder.getLocale()).format(new Date());
        String dt2 = new SimpleDateFormat("HHmmssSSS", LocaleContextHolder.getLocale()).format(new Date());
        String num = getRandomNum(9);
        String num2 = getRandomNum(9);
        String guid = "ECP" + dt + dt2 + num + num2;

        EaiDTO dto = new EaiDTO();
        dto.setUmsRetNo(umsRetNo);          //UMS접수번호
        dto.setUmsBzDttId(umsBzDttId);      //UMS업무구분ID
        dto.setTrDt(trDt);                  //거래일자
        dto.setUmsTrSno(umsTrSno);          //UMS거래일련번호

        //dto.setCno("SYSTEM");             //고객번호
        //dto.setCstNm("관리자");            //고객명
        dto.setCno(reqUsid);                //고객번호
        dto.setCstNm(reqUsrNm);             //고객명

        dto.setSectEmlCnfmNo(null);         //보안이메일확인번호
        dto.setUmsSdChnNo(umsSdChnNo);      //UMS발송채널번호
        dto.setUmsSdChnAddrCone(null);      //UMS발송채널주소내용
        dto.setUmsSdMplDt(umsSdMplDt);      //UMS발송예정일자
        dto.setUmsSdMplTm(umsSdMplTm);      //UMS발송예정시각 (null-즉시, HHMMSS-예약)
        dto.setUmsTmeChnNo(umsTmeChnNo);    //UMS송신채널번호
        dto.setAppC("ECP");                 //어플리케이션코드
        dto.setAppBzLv1C("CX");             //어플리케이션업무1레벨코드

        //dto.setReqUsid(reqUsid);          //요청사용자ID
        //dto.setReqUsrNm(reqUsrNm);        //요청사용자명
        dto.setReqUsid("SYSTEM");           //요청사용자ID
        dto.setReqUsrNm("관리자");           //요청사용자명
        //dto.setReqBbrC(reqBbrC);          //요청부점코드
        //dto.setReqBbrNm(reqBbrNm);        //요청부점명

        dto.setUmsCkgC("N");                //UMS점검코드
        dto.setUmsSdMsgInf(variDatS0);      //UMS발송메시지정보
        dto.setApgFlCone(null);             //첨부파일내용
        dto.setTrTm(null);                  //거래시각
        dto.setUmsSdChnTpC(umsSdChnTpC);    //UMS발송채널유형코드
        dto.setChnReqTpC(chnReqTpC);        //채널요청유형코드  (10-실시간, 20-스케쥴)
        dto.setBzDcmInfCone(null);          //업무식별정보내용
        dto.setUmsBzRfrCone(null);          //UMS업무참조내용
        dto.setBzCS3("ECP");                //업무코드_S3
        dto.setUmsSdMsgClsfTc(null);        //UMS발송메세지분류구분코드
        dto.setEaiFwdiYn("N");              //EAI전송여부
        dto.setEaiPrcDtm(null);             //EAI처리일시
        dto.setIfSysC("ECP");               //인터페이스시스템코드
        dto.setDelYn("N");                  //삭제여부
        dto.setGuid(guid);                  //GUID
        dto.setGuidPrgSno("1");             //GUID진행일련번호
        dto.setLstChgUsid("SYSTEM");        //최종변경사용자ID
        //dto.setLstChgDtm();               //최종변경일시

        //DB 발송 INSERT
        eaiMapper.insertTecpesUmsbati(dto);

        //알림발송이력 저장
        request.setSendDttm(umsSdMplDt + umsSdMplTm);
        insertNotificationSendHistory(request, "UMS", umsRetNo);

        return request;
    }
 */

 
    /*
    private String getParamGWE(EaiDTO request) throws IndexOutOfBoundsException {
        //request
        String recvIds = request.getEmplNum();      //수신자 행번 (퇴직자 금지)

        //메시지키값
        String dt = new SimpleDateFormat("yyyyMMddHHmmss", LocaleContextHolder.getLocale()).format(new Date());
        String num = getRandomNum(8);
        String msgKey = "mailt" + "ECP" + "CX" + dt + num;

        //템플릿 가져오기
        //EaiDTO templ = eaiMapper.getNotificationTemplate(request);

        //알림내용
        String notifCnts = HtmlUtils.htmlUnescape(templ.getNotifCnts());
        if (!ObjectUtils.isEmpty(request.getUmData1())) {
            notifCnts = notifCnts.replaceAll("UM_DATA_1", request.getUmData1());
        }
        if (!ObjectUtils.isEmpty(request.getUmData2())) {
            notifCnts = notifCnts.replaceAll("UM_DATA_2", request.getUmData2());
        }
        if (!ObjectUtils.isEmpty(request.getUmData3())) {
            notifCnts = notifCnts.replaceAll("UM_DATA_3", request.getUmData3());
        }
        if (!ObjectUtils.isEmpty(request.getUmData4())) {
            notifCnts = notifCnts.replaceAll("UM_DATA_4", request.getUmData4());
        }
        if (!ObjectUtils.isEmpty(request.getUmData5())) {
            notifCnts = notifCnts.replaceAll("UM_DATA_5", request.getUmData5());
        }
        if (!ObjectUtils.isEmpty(request.getUmData6())) {
            notifCnts = notifCnts.replaceAll("UM_DATA_6", request.getUmData6());
        }
        if (!ObjectUtils.isEmpty(request.getUmData7())) {
            notifCnts = notifCnts.replaceAll("UM_DATA_7", request.getUmData7());
        }

        String subject = templ.getNotifTitle();
        String contents = notifCnts;

        String param = "";                      //필드명				필드설명				Type	Length	Offset	값(예제로 받은 값, 알맞게 변경)
        param += lpad("C", 32, msgKey);         //MSG_KEY			메시지키값			STRING	32				alert+어플리케이션코드(3)+업무레벨(2)+날짜(년월일시분초)+임의의수(8) (alertBICTQ2017120814152700000001)
        param += lpad("C", 1, "3");             //MSG_GUBUN			알림구분				STRING	1				1:메신저, 3:메일
        param += lpad("C", 50, "systemalert");  //SEND_ID			전송자 사원번호		STRING	50				전송자 행번(k+개인번호), 또는 부서 ID - 시스템알림의 경우 “systemalert” 으로 등록
        param += lpad("C", 100, "관리자");       //SEND_NAME			전송자 이름			STRING	100				-”직원명”,  “시스템명,  “업무명“ - 예) “000관리자”
        param += lpad("C", 1, "1");             //DEST_GUBUN		수신자 구분			STRING	1               1:사용자(Default), 2: 부서
        param += lpad("C", 4000, recvIds);      //RECV_IDS			수신자 정보			STRING	4000            수신자 정보 : 사원번호(emp_code) 또는 부서코드(dept_code) 목록  (구분자는 콤마(,)) 예) k0011,k0012 또는 kdb0001,kdb0002 (- 필수 : 사용자, 부서 혼용 불가. - 부서는 하위부서 포함되지 않음 - 공백 사용 불가 - 퇴직자행번 입력시 알림 발송 오류 발생)
        param += lpad("C", 500, "");            //CC_RECV_IDS		참조 수신자 정보		STRING	500             메일 전용
        param += lpad("C", 500, "");            //BCC_RECV_IDS      숨은참조 수신자 정보	STRING	500             메일 전용
        param += lpad("C", 200, subject);       //SUBJECT			제목					STRING	200
        param += lpad("C", 4000, contents);     //CONTENTS			내용					STRING	4000            HTML TAG 지원
        param += lpad("C", 500, "");            //URL				메신저 클릭URL		STRING	500             메신저 전용, 예) http://[URL정보]?user_id=(%USER_ID%)&username=(%USER_NAME%) (메신저 TAG 설명- 사원번호 : (%USER_ID%) - 사용자명 : (%USER_NAME%))
        param += lpad("C", 1, "");              //ATT_FLAG			첨부여부				STRING	1               메일 전용
        param += lpad("C", 4000, "");           //ATT				첨부정보				STRING	14000           메일 전용
        param += lpad("C", 3, "ECP");           //SYSTEM_CODE		발송요청 시스템코드	STRING	3

        //알림발송이력 저장
        request.setSendDttm(dt);
        insertNotificationSendHistory(request, "GWE", msgKey);

        return param;
    }
 */