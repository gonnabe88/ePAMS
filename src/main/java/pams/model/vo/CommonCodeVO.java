package pams.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/***
 * @author 140024
 * @implNote 코드 테이블 데이터 정의 DTO
 * @since 2024-06-09
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommonCodeVO {

    /***
     * @author 140024
     * @implNote 코드id(CD_ID)
     * @since 2024-06-09
     */
    private int codeId;

    /***
     * @author 140024
     * @implNote 코드(CD)
     * @since 2024-06-09
     */
    private String code;

    /***
     * @author 140024
     * @implNote 코드명(CD_NM)
     * @since 2024-06-09
     */
    private String codeName;

    /***
     * @author 140024
     * @implNote 시스템코드(SYSTEM_CD)
     * @since 2024-06-09
     */
    private String systemCode;

    /***
     * @author 140024
     * @implNote 코드약명(SHORT_NM)
     * @since 2024-06-09
     */
    private String shortName;

    /***
     * @author 140024
     * @implNote 다국어지역코드(LOCALE_CD)
     * @since 2024-06-09
     */
    private String localeCode;

    /***
     * @author 140024
     * @implNote 인사영역코드(COMPANY_CD)
     * @since 2024-06-09
     */
    private String companyCode;

    /***
     * @author 140024
     * @implNote 코드분류(CD_KIND)
     * @since 2024-06-09
     */
    private String codeKind;

    /***
     * @author 140024
     * @implNote 시작일자(STA_YMD)
     * @since 2024-06-09
     */
    private Date startYmd;

    /***
     * @author 140024
     * @implNote 종료일자(END_YMD)
     * @since 2024-06-09
     */
    private Date endYmd;

    /***
     * @author 140024
     * @implNote 외국어명(FOR_NM)
     * @since 2024-06-09
     */
    private String foreignName;

    /***
     * @author 140024
     * @implNote 외국어Full명(FOR_FULL_NM)
     * @since 2024-06-09
     */
    private String foreignFullName;

    /***
     * @author 140024
     * @implNote 출력명(PRINT_NM)
     * @since 2024-06-09
     */
    private String printName;

    /***
     * @author 140024
     * @implNote 정렬순서(ORD_NO)
     * @since 2024-06-09
     */
    private String orderNo;

    /***
     * @author 140024
     * @implNote 변환코드(CONV_CD)
     * @since 2024-06-09
     */
    private String convertCode;

    /***
     * @author 140024
     * @implNote 기타코드1(ETC_CD1)
     * @since 2024-06-09
     */
    private String etcCode1;

    /***
     * @author 140024
     * @implNote 기타코드2(ETC_CD2)
     * @since 2024-06-09
     */
    private String etcCode2;

    /***
     * @author 140024
     * @implNote 기타코드3(ETC_CD3)
     * @since 2024-06-09
     */
    private String etcCode3;

    /***
     * @author 140024
     * @implNote 기타코드4(ETC_CD4)
     * @since 2024-06-09
     */
    private String etcCode4;

    /***
     * @author 140024
     * @implNote 기타코드5(ETC_CD5)
     * @since 2024-06-09
     */
    private String etcCode5;

    /***
     * @author 140024
     * @implNote 기타코드6(ETC_CD6)
     * @since 2024-06-09
     */
    private String etcCode6;

    /***
     * @author 140024
     * @implNote 기타코드7(ETC_CD7)
     * @since 2024-06-09
     */
    private String etcCode7;

    /***
     * @author 140024
     * @implNote 기타코드8(ETC_CD8)
     * @since 2024-06-09
     */
    private String etcCode8;

    /***
     * @author 140024
     * @implNote 기타코드9(ETC_CD9)
     * @since 2024-06-09
     */
    private String etcCode9;

    /***
     * @author 140024
     * @implNote 기타코드10(ETC_CD10)
     * @since 2024-06-09
     */
    private String etcCode10;

    /***
     * @author 140024
     * @implNote 비고(NOTE)
     * @since 2024-06-09
     */
    private String note;

    /***
     * @author 140024
     * @implNote 변경자(MOD_USER_ID)
     * @since 2024-06-09
     */
    private int modUserId;

    /***
     * @author 140024
     * @implNote 변경일시(MOD_DATE)
     * @since 2024-06-09
     */
    private Date modDate;

    /***
     * @author 140024
     * @implNote 타임존코드(TZ_CD)
     * @since 2024-06-09
     */
    private String timezoneCode;

    /***
     * @author 140024
     * @implNote 타임존일시(TZ_DATE)
     * @since 2024-06-09
     */
    private Date timezoneDate;

    /***
     * @author 140024
     * @implNote 기타코드11(ETC_CD11)
     * @since 2024-06-09
     */
    private String etcCode11;
}
