#!/bin/bash

BUILD_LOG_FILE="$HOME/cron.log"
WAS_LOG_FILE="$HOME/app.log"

# 프로젝트 루트 디렉토리로 이동
cd "$(dirname "$0")"

# 시간 기록 함수
record_time() {
    echo "[$(date '+%Y-%m-%d %H:%M:%S')] $1" >> "$BUILD_LOG_FILE"
}

# 환경 변수 설정 (cron으로 실행할 경우에만 필요)
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-17.0.11.0.9-2.el8.x86_64
export PATH=$JAVA_HOME/bin:$PATH
export GRADLE_USER_HOME=$(pwd)/.gradle

# Git 원격 저장소에서 최신 변경 사항 가져오기 (Git을 사용하는 외부 운영서버에서만 필요)
record_time "Git 원격 저장소에서 최신 변경 사항을 가져오는 중..."
/usr/bin/git fetch origin
/usr/bin/git reset --hard origin/main  # 필요한 경우 'main'을 실제 사용 중인 브랜치 이름으로 변경

# gradlew와 build.sh 파일에 실행 권한 부여 (Git을 사용하는 외부 운영서버에서만 필요)
record_time "gradlew와 build.sh 파일에 실행 권한을 부여하는 중..."
chmod +x ./gradlew
chmod +x ./build.sh

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

    # 기존 프로세스 종료
    record_time "기존 ePAMS 프로세스 종료 중..."
    pkill -f 'ePAMS' || true  # 프로세스 종료 시 실패해도 계속 진행

    # JAR 파일 위치
    JAR_FILE=$(find build/libs -name "*-SNAPSHOT.jar" -o -name "*.jar" | head -n 1)
    if [ -z "$JAR_FILE" ]; then
        record_time "JAR 파일을 찾을 수 없습니다. 빌드가 성공했는지 확인하세요."
        exit 1
    fi

    # JAR 파일 실행
    # Java 난수 생성 시 빠르고 효율적인 /dev/urandom 사용하도록 설정
    # JVM 초기 힙 메모리 크기 2GB
    # JVM 최대 힙 메모리 크기 4GB
    # G1 가비지 컬렉터 사용
    # 문자열 중복 제거 기능 활성화 (메모리 사용량 감소)
    record_time "JAR 파일을 실행하는 중..."
        nohup java \
           -Djava.security.egd=file:/dev/./urandom \
           -Xms1G \
           -Xmx2G \
           -XX:+UseG1GC \
           -XX:+UseStringDeduplication \
           -jar "$JAR_FILE" > "$WAS_LOG_FILE" 2>&1 &

    # 성공 메시지
    record_time "배포 및 실행 성공"
else
    record_time "빌드 또는 실행 중 오류 발생"
    exit 1
fi
