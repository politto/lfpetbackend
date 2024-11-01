FROM ubuntu:latest AS build
RUN apt-get update && apt-get install openjdk-21-jdk -y
COPY . .
RUN ./gradlew build

FROM openjdk:21-jre-slim-buster
EXPOSE 8080
COPY --from=build /build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]