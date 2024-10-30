# VSCode 설정
## 환경변수
VSCode는 GRADLE_USER_HOME을 절대경로로 지정해줘야 함 
ex) D:\workspace\ePAMS

## 심볼릭 링크
Ctrl+클릭 이동 시 경로 이슈 해결을 위해 심볼릭 링크 설정
ex) 프로젝트가 D:\workspace\ePAMS\ 경로에 있는 경우

```bash
C:\Windows\System32>mklink /D "D:\workspace\ePAMS\js" "D:\workspace\ePAMS\src\main\resources\static\js"
D:\workspace\ePAMS\js <<===>> D:\workspace\ePAMS\src\main\resources\static\js에 대한 기호화된 링크를 만들었습니다.

C:\Windows\System32>mklink /D "D:\workspace\ePAMS\extensions" "D:\workspace\ePAMS\src\main\resources\static\extensions"
D:\workspace\ePAMS\extentions <<===>> D:\workspace\ePAMS\src\main\resources\static\extensions에 대한 기호화된 링크를 만들었습니다.

C:\Windows\System32>mklink /D "D:\workspace\ePAMS\css" "D:\workspace\ePAMS\src\main\resources\static\css"
D:\workspace\ePAMS\css <<===>> D:\workspace\ePAMS\src\main\resources\static\css에 대한 기호화된 링크를 만들었습니다.

C:\Windows\System32>mklink /D "D:\workspace\ePAMS\images" "D:\workspace\ePAMS\src\main\resources\static\images"
D:\workspace\ePAMS\images <<===>> D:\workspace\ePAMS\src\main\resources\static\images에 대한 기호화된 링크를 만들었습니다.
```

# DATABASE
### Local : Oracle XE 18c
### Production : Oracle 19c

# 사용자 정보 Import 방법 테스트222222
1) OHR 스키마 생성
2) OHR Table 생성
 - create user ohr identified by "kdb1234!";
 - 권한설정(https://www.notion.so/KDB-ePAMS-20b58157b2a54640a475edc3d0c40052#3fd3164d907f44399cdf9817c1e47f75)
3) 사용자 정보는 OHR.IF_IAM_USER
 - src/main/resources/ohr_schema.sql 참조
 - 위 스크립트 실행 시 sys 계정 (sysdba 권한)으로 접속
4) Test 코드 실행 (멤버 5,000명 등록함)
 - src/test/java/com/kdb/IamUserRegistTests.java
