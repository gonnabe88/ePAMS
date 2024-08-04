CREATE OR REPLACE PROCEDURE OHR.PRE_CHECK_P(
    p_empId IN NUMBER,
    p_staYmd IN DATE,
    p_endYmd IN DATE,
    p_result OUT VARCHAR2
)
    IS
    v_count NUMBER;
BEGIN
    -- 직원 ID와 날짜 범위에 따른 조건을 사용한 데이터 조회
    SELECT COUNT(*)
    INTO v_count
    FROM OHR.DTM_HIS
    WHERE EMP_ID = p_empId
      AND STA_YMD >= p_staYmd
      AND END_YMD <= p_endYmd;

    -- 조회 결과에 따른 반환값 설정
    IF v_count > 0 THEN
        p_result := 'FAILURE';
    ELSE
        p_result := 'SUCCESS';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        p_result := 'FAILURE';
END PRE_CHECK_P;