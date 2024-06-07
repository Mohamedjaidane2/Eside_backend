FROM openjdk:17-jdk

WORKDIR /app

ADD target/gateway-*.jar /app/app.jar

ENTRYPOINT ["java","-jar","app.jar"]