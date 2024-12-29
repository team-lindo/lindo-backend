# Build: docker build -t lindo-backend:laetst .
# Run: docker run --name lindo-backend -d -p 8080:8080 lindo-backend:latest
# Stop: docker stop lindo-backend
# Remove: docker rm lindo-backend

# Build stage
FROM gradle:8.7.0-jdk21 as build
WORKDIR /app

# 먼저 gradle 의존성을 모두 설치
COPY build.gradle.kts .
COPY settings.gradle.kts .
RUN gradle dependencies --no-daemon

# 루트 디렉토리 복사
COPY . .

# 빌드
RUN gradle build -x test --no-daemon

# Run stage
FROM ghcr.io/graalvm/jdk-community:21 as prod
WORKDIR /app

# 빌드 단계에서 생성된 JAR 파일을 복사
COPY --from=build /app/build/libs/backend-0.0.1.jar app.jar

# 컨테이너가 시작될 때 애플리케이션 실행
CMD ["java", "-jar", "-Duser.timezone=Asia/Seoul", "app.jar"]
