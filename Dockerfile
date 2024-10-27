FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/gym-exercises-api-1.0.0.jar
COPY ${JAR_FILE} gym-exercises-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "gym-exercises-api.jar"]