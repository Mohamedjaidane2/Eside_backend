FROM openjdk:17-jdk

WORKDIR /app

ADD target/config-server-*.jar /app/app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]