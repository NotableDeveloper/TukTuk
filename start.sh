#!/bin/bash
sed -i 's/\r//' docker/create_database.sql
sed -i 's/\r//' gradlew

#프로젝트 빌드
./gradlew clean
./gradlew bootJar

#권한 설정 및 JAR 파일 복사
chmod 755 build/libs/*.jar
cp build/libs/*.jar ./docker/app.jar

#도커 컨테이너 실행
cd docker
docker compose up -d