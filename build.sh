#!/bin/bash

# 프로젝트 루트 디렉토리로 이동
cd "$(dirname "$0")"

# Gradle 사용자 홈 디렉토리를 프로젝트 내로 설정
export GRADLE_USER_HOME="$(pwd)/.gradle"

# Gradle 버전 확인
./gradlew --version

# 기존 빌드 디렉토리 삭제
./gradlew clean

# 빌드
echo "프로젝트를 빌드하는 중..."
./gradlew build --offline

# 결과 확인
if [ $? -eq 0 ]; then
    echo "빌드 및 실행 성공"
else
    echo "빌드 또는 실행 중 오류 발생"
    exit 1
fi