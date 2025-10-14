# 1. Java image
FROM openjdk:17-jdk-slim

# 2. JAR faylını konteynerə kopyala
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# 3. Port aç
EXPOSE 8080

# 4. App-i işə sal
ENTRYPOINT ["java", "-jar", "/app.jar"]
