# 베이스 이미지 선택 (Java 17 사용)
FROM openjdk:17

# 작업 디렉토리 설정
WORKDIR /app

# Maven 빌드 아티팩트를 컨테이너로 복사
COPY build/libs/ShortUrlProject-0.0.1-SNAPSHOT.jar /app/ShortUrlProject-0.0.1-SNAPSHOT.jar

# APM agent 파일 복사
COPY ./agent/elastic-apm-agent-1.52.1.jar /elastic-apm-agent-1.52.1.jar

EXPOSE 9000

# 컨테이너가 시작될 때 실행할 명령어
CMD ["java", "-javaagent:/elastic-apm-agent-1.52.1.jar", "-Delastic.apm.service_name=short-url-local" ,"-Delastic.apm.server_url=http://fleet-server:8200" ,"-Delastic.apm.environment=short-url"  ,"-Delastic.apm.application_packages=com.hyujikoh.ShortUrl" ,"-Delastic.apm.secret_token=1697b5ddea00af390d32b8fca3ccbd00", "-jar", "/app/ShortUrlProject-0.0.1-SNAPSHOT.jar"]