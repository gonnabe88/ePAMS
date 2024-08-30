CREATE OR REPLACE PROCEDURE OHR.P_DTM01_APPL_ELA_CHECK(
    an_appl_id IN NUMBER,
    av_mod_user_id IN VARCHAR2,
    av_company_cd IN VARCHAR2,
    av_locale_cd IN VARCHAR2,
    av_tz_cd IN VARCHAR2,
    av_ret_code OUT VARCHAR2,
    av_ret_message OUT VARCHAR2
)
    IS
    v_count NUMBER;
BEGIN
    -- 신청서 조회
    SELECT COUNT(*)
    INTO v_count
    FROM OHR.ELA_APPL_C
    WHERE APPL_ID = an_appl_id;

    -- 조회 결과에 따른 반환값 설정
    IF v_count > 0 THEN
        av_ret_code := 'SUCCESS!';
        av_ret_message := '성공적으로 검증되었습니다.';
    ELSE
        av_ret_code := 'FAILURE!';
        av_ret_message := '신청서 정보가 없습니다.';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        av_ret_code := 'FAILURE!';
        av_ret_message := '알 수 없는 이유로 실패하였습니다.';
END P_DTM01_APPL_ELA_CHECK;