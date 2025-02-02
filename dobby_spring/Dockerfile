# 베이스 이미지 설정
FROM openjdk:17-jdk-slim

# 애플리케이션 디렉토리 설정
WORKDIR /app

# JAR 파일과 CSV 파일을 이미지에 복사
COPY dobby.jar /app/app.jar
COPY test.csv /app/test.csv
# COPY url_data.csv /app/url_data.csv

# 애플리케이션 실행
CMD ["java", "-jar", "/app/app.jar"]
