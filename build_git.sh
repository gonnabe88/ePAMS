#!/bin/bash

BUILD_LOG_FILE="/log/ehr/build_shell.log"

# 프로젝트 루트 디렉토리로 이동
cd "$(dirname "$0")"

# 시간 기록 함수
record_time() {
    echo "[$(date '+%Y-%m-%d %H:%M:%S')] $1" >> "$BUILD_LOG_FILE"
}

# 환경 변수 설정
export JAVA_HOME=/sw/ehr/java-17-openjdk
export PATH=$JAVA_HOME/bin:$PATH
export GRADLE_USER_HOME=$(pwd)/.gradle

# [삭제] Git 원격 저장소에서 최신 변경 사항 가져오기 (Git을 사용하는 외부 운영서버에서만 필요)
record_time "Git 원격 저장소에서 최신 변경 사항을 가져오는 중..."
/usr/bin/git fetch origin
/usr/bin/git reset --hard origin/main  # 필요한 경우 'main'을 실제 사용 중인 브랜치 이름으로 변경

# [삭제] gradlew와 build.sh 파일에 실행 권한 부여 (Git을 사용하는 외부 운영서버에서만 필요)
record_time "gradlew와 build.sh 파일에 실행 권한을 부여하는 중..."
chmod +x ./gradlew
chmod +x ./build_git.sh

# Gradle 버전 확인
record_time "Gradle 버전 확인 중..."
./gradlew --version >> "$BUILD_LOG_FILE" 2>&1

# 기존 빌드 디렉토리 삭제
record_time "기존 빌드 디렉토리 삭제 중..."
./gradlew clean >> "$BUILD_LOG_FILE" 2>&1

# 빌드
record_time "프로젝트를 빌드하는 중..."
./gradlew build --offline >> "$BUILD_LOG_FILE" 2>&1

# 결과 확인
if [ $? -eq 0 ]; then
    record_time "빌드 성공"
else
    record_time "빌드 중 오류 발생"
    exit 1
fi
