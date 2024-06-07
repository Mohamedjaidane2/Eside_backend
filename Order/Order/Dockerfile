FROM openjdk:17-jdk

WORKDIR /app

ADD target/Order-*.jar /app/app.jar

ENTRYPOINT ["java","-jar","app.jar"]