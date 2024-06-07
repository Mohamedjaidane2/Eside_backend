# Build stage
FROM maven:3.8.7-openjdk-18 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17-jdk

ARG APP_VERSION=0.0.1

WORKDIR /app
COPY --from=build /build/target/EmailSender-*.jar /app/

ENV JAR_VERSION=${APP_VERSION}


CMD java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 -${JAR_VERSION}.jar