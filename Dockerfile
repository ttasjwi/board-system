FROM amazoncorretto:21-alpine3.20-jdk
ARG JAR_FILE=build/libs/*.jar
ARG PROFILES
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-DSpring.profiles.active=${PROFILES}", "-jar", "app.jar"]
