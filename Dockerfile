FROM eclipse-temurin:17-jdk-alpine

COPY target/file-transfer.jar /app/

WORKDIR /app

CMD ["java", "-jar", "file-transfer.jar"]
