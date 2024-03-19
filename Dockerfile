FROM amazoncorretto:17

# 작업 디렉토리 설정
WORKDIR /app

# 애플리케이션을 실행할 포트
EXPOSE 8080

# 빌드된 JAR 파일을 가져오기
COPY /build/libs/*.jar app.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]