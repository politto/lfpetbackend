FROM ubuntu:latest AS build
RUN apt-get update && apt-get install openjdk-21-jdk -y
COPY . .
RUN chmod +x ./gradlew
RUN ls -l ./gradlew
RUN sudo ./gradlew bootrun

FROM openjdk:21-jdk
EXPOSE 8080
COPY --from=build /build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]