-- 사용자 계정 생성 시 제약조건 삭제
ALTER SESSION SET "_ORACLE_SCRIPT"=true;

-- 사용자 계정 및 스키마 생성
create user EHR identified by "kdb1234!";

-- 테이블 스페이스 할당
ALTER USER EHR DEFAULT TABLESPACE USERS QUOTA UNLIMITED ON USERS;

-- 권한부여
GRANT SELECT ANY TABLE, CREATE SESSION, CREATE VIEW to EHR;
GRANT CREATE ANY TABLE to EHR;
GRANT INSERT ANY TABLE to EHR;
GRANT UPDATE ANY TABLE to EHR;
GRANT DELETE ANY TABLE to EHR;
GRANT RESOURCE TO EHR;
GRANT CONNECT TO EHR;
GRANT SELECT ANY SEQUENCE TO EHR;
GRANT EXECUTE ANY PROCEDURE TO EHR;