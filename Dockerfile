FROM openjdk:17-jdk-alpine

ARG JAR_FILE=target/*.jar

COPY ./target/AtbuPastQuestion-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]
EXPOSE 8080



#C:\Users\TAIWO\.jdks\corretto-21.0.2

#FROM maven:3.8.5-openjdk-17 AS build
#COPY . .
#RUN mvn clean package -DskipTests
#
#FROM openjdk:17.0.1-jdk-slim
#COPY --from=build/target/
