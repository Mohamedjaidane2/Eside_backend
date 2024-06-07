FROM openjdk:17-jdk

WORKDIR /app

ADD target/Order-0.0.1-SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["java","-jar","app.jar","app/app.jar"]