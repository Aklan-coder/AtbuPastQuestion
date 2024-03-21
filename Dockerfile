FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY AtbuPastQuestion/*.jar app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]
EXPOSE 8080