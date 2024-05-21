FROM openjdk:21-jdk

WORKDIR /app

ADD target/config-server-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8888

ENTRYPOINT ["java","-jar","app.jar"]