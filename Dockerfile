FROM maven:3.8-eclipse-temurin-21-alpine as build
WORKDIR /
COPY /src /src
COPY checkstyle-suppressions.xml /
COPY pom.xml /
RUN mvn -f /pom.xml clean package

FROM openjdk:21-jdk-slim
WORKDIR /
COPY /src /src
COPY --from=build /target/*.jar application.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","application.jar"]