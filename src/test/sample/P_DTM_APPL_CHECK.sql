CREATE OR REPLACE PROCEDURE OHR.P_DTM_APPL_CHECK(
    av_company_cd IN VARCHAR2,
    av_locale_cd IN VARCHAR2,
    an_emp_id IN NUMBER,
    av_reason_cd IN VARCHAR2,
    ad_sta_ymd IN DATE,
    ad_end_ymd IN DATE,
    an_his_oid IN NUMBER,
    av_auth_type IN VARCHAR2,
    av_dtm_type IN VARCHAR2,
    av_child_no IN VARCHAR2, --?????
    av_mod_user_id IN NUMBER, --?????
    av_tz_cd IN VARCHAR2,
    av_ret_code OUT VARCHAR2,
    av_ret_message OUT VARCHAR2
)
    IS
    v_count NUMBER;
BEGIN
    -- 직원 ID와 날짜 범위에 따른 조건을 사용한 데이터 조회
    SELECT COUNT(*)
    INTO v_count
    FROM OHR.DTM_HIS
    WHERE EMP_ID = an_emp_id
      AND STA_YMD >= ad_sta_ymd
      AND END_YMD <= ad_end_ymd
      AND STAT_CD = '132';

    -- 조회 결과에 따른 반환값 설정
    IF v_count > 0 THEN
        av_ret_code := 'FAILURE!';
        av_ret_message := '중복된 데이터가 존재합니다.';
    ELSE
        av_ret_code := 'SUCCESS!';
        av_ret_message := '성공적으로 처리되었습니다.';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        av_ret_message := 'FAILURE!';
        av_ret_message := '알 수 없는 이유로 실패하였습니다.';
END P_DTM_APPL_CHECK;