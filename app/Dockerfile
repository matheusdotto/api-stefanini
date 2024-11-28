FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/springsecurity-0.0.1-SNAPSHOT.jar /app/app-stefanini.jar

EXPOSE 10000

CMD ["java", "-jar", "/app/app-stefanini.jar"]
