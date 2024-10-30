FROM amazoncorretto:21-alpine3.20-jdk
ARG JAR_FILE=board-system-container/build/libs/board-system-container.jar
ARG PROFILES
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["sh", "-c", "java -DSpring.profiles.active=${PROFILES} -Dspring.config.location=classpath:/,file:/app/config/board-system/ -jar app.jar"]
