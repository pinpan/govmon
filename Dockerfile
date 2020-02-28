FROM adoptopenjdk/openjdk11:latest
VOLUME /tmp

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} govmon.jar
ENTRYPOINT ["java","-jar","/govmon.jar"]


