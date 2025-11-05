FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x gradlew && ./gradlew clean bootJar -x test
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "build/libs/Digital-Wallet-0.0.1-SNAPSHOT.jar"]