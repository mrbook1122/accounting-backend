FROM maven:3.6.3-jdk-11
WORKDIR /app
COPY . .
RUN mvn package

FROM openjdk:11
COPY --from=0 /app/target/*.jar /app.jar

CMD ["java", "-jar", "/app.jar"]