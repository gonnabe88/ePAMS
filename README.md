# DATABASE
### Local : Oracle XE 18c
### Production : Oracle 19c

# 사용자 정보 Import 방법
1) OHR 스키마 생성
2) OHR Table 생성
 - create user ohr identified by "kdb1234!";
 - 권한설정(https://www.notion.so/KDB-ePAMS-20b58157b2a54640a475edc3d0c40052#3fd3164d907f44399cdf9817c1e47f75)
3) 사용자 정보는 OHR.IF_IAM_USER
 - src/main/resources/ohr_schema.sql 참조
 - 위 스크립트 실행 시 sys 계정 (sysdba 권한)으로 접속
4) Test 코드 실행 (멤버 5,000명 등록함)
 - src/test/java/com/kdb/IamUserRegistTests.java
