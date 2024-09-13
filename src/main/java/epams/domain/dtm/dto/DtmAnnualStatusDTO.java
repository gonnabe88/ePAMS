package epams.domain.dtm.dto;

import java.lang.reflect.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/***
 * @author 140024
 * @implNote 년간근태조회 DTO
 * @since 2024-06-09
 */
@Slf4j
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DtmAnnualStatusDTO {

    /***
     * @author 140024
     * @implNote 연차발생일수(yycnt)
     * @since 2024-09-11
     */
    private String AnnualDayTotalCnt;

    /***
     * @author 140024
     * @implNote 연차발생시간(hhcnt)
     * @since 2024-09-11
     */
    private String AnnualHourTotalCnt;
    
        /***
     * @author 140024
     * @implNote 선사용연차발생일수(ad_use_yycnt)
     * @since 2024-09-11
     */
    private String AdAnnualDayTotalCnt;

    /***
     * @author 140024
     * @implNote 선사용연차발생시간(ad_use_hhcnt)
     * @since 2024-09-11
     */
    private String AdAnnualHourTotalCnt;

    /***
     * @author 140024
     * @implNote 연차저축일수(SAVE_YYCNT)
     * @since 2024-09-11
     */
    private String SaveDayTotalCnt;

    /***
     * @author 140024
     * @implNote 연차저축시간(SAVE_HHCNT)
     * @since 2024-09-11
     */
    private String SaveHourTotalCnt;


    /***
     * @author 140024
     * @implNote 과거 연차저축일수(N_SAVE_HHCNT)
     * @since 2024-09-11
     */
    private String SaveDayPastTotalCnt;

    /***
     * @author 140024
     * @implNote 과거 연차저축시간(HH_YEAR5) - 5년 이상
     * @since 2024-09-11
     */
    private String SaveHour5YearCnt;

    /***
     * @author 140024
     * @implNote 과거 연차저축시간(HH_YEAR4) - 4년
     * @since 2024-09-11
     */
    private String SaveHour4YearCnt;

    /***
     * @author 140024
     * @implNote 과거 연차저축시간(HH_YEAR3) - 3년
     * @since 2024-09-11
     */
    private String SaveHour3YearCnt;

    /***
     * @author 140024
     * @implNote 과거 연차저축시간(HH_YEAR2) - 2년
     * @since 2024-09-11
     */
    private String SaveHour2YearCnt;

    /***
     * @author 140024
     * @implNote 과거 연차저축시간(HH_YEAR1) - 1년
     * @since 2024-09-11
     */
    private String SaveHour1YearCnt;

    /***
     * @author 140024
     * @implNote 과거 연차저축일수(YY_YEAR5) - 5년 이상
     * @since 2024-09-11
     */
    private String SaveDay5YearCnt;

    /***
     * @author 140024
     * @implNote 과거 연차저축일수(YY_YEAR4) - 4년
     * @since 2024-09-11
     */
    private String SaveDay4YearCnt;

    /***
     * @author 140024
     * @implNote 과거 연차저축일수(YY_YEAR3) - 3년
     * @since 2024-09-11
     */
    private String SaveDay3YearCnt;

    /***
     * @author 140024
     * @implNote 과거 연차저축일수(YY_YEAR2) - 2년
     * @since 2024-09-11
     */
    private String SaveDay2YearCnt;

    /***
     * @author 140024
     * @implNote 과거 연차저축일수(YY_YEAR1) - 1년
     * @since 2024-09-11
     */
    private String SaveDay1YearCnt;

    /***
     * @author 140024
     * @implNote 연차사용일수(yycnt_used_cnt)
     * @since 2024-09-11
     */
    private String AnnualDayUsedCnt;

    /***
     * @author 140024
     * @implNote 연차사용시간(HHCNT_USED_CNT)
     * @since 2024-09-11
     */
    private String AnnualHourUsedCnt;

    /***
     * @author 140024
     * @implNote 연차잔여일수(YYCNT_REMAIN_CNT)
     * @since 2024-09-11
     */
    private String AnnualDayRemainCnt;

    /***
     * @author 140024
     * @implNote 연차잔여시간(HHCNT_REMAIN_CNT)
     * @since 2024-09-11
     */
    private String AnnualHourRemainCnt;

    /***
     * @author 140024
     * @implNote 연차잔여일수(YYCNT_REMAIN_CNT2) - 저축 제외
     * @since 2024-09-11
     */
    private String AnnualDayNoSaveRemainCnt;

    /***
     * @author 140024
     * @implNote 연차잔여시간(HHCNT_REMAIN_CNT2) - 저축 제외
     * @since 2024-09-11
     */
    private String AnnualHourNoSaveRemainCnt;

    /***
     * @author 140024
     * @implNote 의무사용연차일수(duty_yycnt)
     * @since 2024-09-11
     */
    private String DutyAnnualDayTotalCnt;

    /***
     * @author 140024
     * @implNote 의무사용연차사용일수(duty_used_yycnt)
     * @since 2024-09-11
     */
    private String DutyAnnualDayUsedCnt;

    /***
     * @author 140024
     * @implNote 의무사용연차잔여일수(duty_remain_cnt)
     * @since 2024-09-11
     */
    private String DutyAnnualDayRemainCnt;

    /***
     * @author 140024
     * @implNote 연차저축예외일수(EXP_CNT)
     * @since 2024-09-11
     */
    private String ExpSaveDayTotalCnt;

    /***
     * @author 140024
     * @implNote 연차저축예외시간(EXP_HHCNT)
     * @since 2024-09-11
     */
    private String ExpSaveHourTotalCnt;

    /***
     * @author 140024
     * @implNote 사용 예정 연차잔여일수(AF_YYCNT_USED_CNT)
     * @since 2024-09-11
     */
    private String AnnualDayDueCnt;

    /***
     * @author 140024
     * @implNote 사용 예정 연차잔여시간(AF_HHCNT_USED_CNT)
     * @since 2024-09-11
     */
    private String AnnualHourDueCnt;

    /***
     * @author 140024
     * @implNote 사용 완료 연차일수(BF_YYCNT_USED_CNT)
     * @since 2024-09-11
     */
    private String AnnualDayPastCnt;

    /***
     * @author 140024
     * @implNote 사용 완료 연차시간(BF_HHCNT_USED_CNT)
     * @since 2024-09-11
     */
    private String AnnualHourPastCnt;

    /***
     * @author 140024
     * @implNote 1일 근무 기준시간(STD_TIME)
     * @since 2024-09-11
     */
    private String stdTime;

    /***
     * @author 140024
     * @implNote ????(S_YEAR)
     * @since 2024-09-11
     */
    private String sYear;


    /***
     * @author 140024
     * @implNote 모든 String 변수에 포함된 특수문자 '(', ')' 제거
     * @since 2024-09-11
     * @param DtmAnnualStatusDTO 년간근태조회 DTO
     */
    public static void removeBracket(DtmAnnualStatusDTO dto) {
        Field[] fields = dto.getClass().getDeclaredFields();
        for(Field field : fields) {
            // String 타입 변수만 처리
            if(field.getType().equals(String.class)) {
                field.setAccessible(true); // Private Field에 접근 가능하도록 설정
                try {
                    String value = (String) field.get(dto);
                    if(value != null) {
                        value = value.replace("(", "");
                        value = value.replace(")", "");
                        field.set(dto, value);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
