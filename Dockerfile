FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=/build/libs/*.jar
COPY ${JAR_FILE} notification-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/notification-0.0.1-SNAPSHOT.jar"]
CMD ["java", "-jar", "/notification-0.0.1-SNAPSHOT.jar", "--server.port=${PORT:9997}"]

#docker build --build-arg JAR_FILE=build/libs/*.jar -t myorg/myapp .

#docker run -p 8089:8089 -e JAVA_OPTS=-Dserver.port=9000 myorg/myapp
