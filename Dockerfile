FROM amazoncorretto:21-alpine3.20-jdk
ARG JAR_FILE=board-system-app/build/libs/board-system-app.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["sh", "-c", "java -jar app.jar"]
