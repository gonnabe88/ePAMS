#!/bin/bash

# 프로젝트 루트 디렉토리로 이동
cd "$(dirname "$0")"

# 시간 기록 함수
record_time() {
    echo "[$(date '+%Y-%m-%d %H:%M:%S')] $1"
}

# Git 원격 저장소에서 최신 변경 사항 가져오기
record_time "Git 원격 저장소에서 최신 변경 사항을 가져오는 중..."
git fetch origin
git pull origin main  # 필요한 경우 'main'을 실제 사용 중인 브랜치 이름으로 변경

# Gradle 버전 확인
record_time "Gradle 버전 확인 중..."
./gradlew --version

# 기존 빌드 디렉토리 삭제
record_time "기존 빌드 디렉토리 삭제 중..."
./gradlew clean

# 빌드
record_time "프로젝트를 빌드하는 중..."
./gradlew build --offline

# 결과 확인
if [ $? -eq 0 ]; then
    record_time "빌드 성공"

    # WAR 파일 위치 (build/libs/ 디렉토리에 생성된 WAR 파일 가정)
    WAR_FILE=$(find build/libs -name "*.war" | head -n 1)
    if [ -z "$WAR_FILE" ]; then
        record_time "WAR 파일을 찾을 수 없습니다. 빌드가 성공했는지 확인하세요."
        exit 1
    fi

    # Tomcat 웹 애플리케이션 디렉토리로 WAR 파일 복사
    TOMCAT_WEBAPPS_DIR="/opt/tomcat/webapps"
    record_time "Tomcat 웹 애플리케이션 디렉토리로 WAR 파일을 복사하는 중..."
    cp "$WAR_FILE" "$TOMCAT_WEBAPPS_DIR/epams.war"

    # Tomcat 서비스 재시작
    record_time "Tomcat 서비스를 재시작하는 중..."
    sudo systemctl restart tomcat

    # 성공 메시지
    record_time "배포 성공"
else
    record_time "빌드 또는 실행 중 오류 발생"
    exit 1
fi
