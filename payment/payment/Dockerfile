FROM openjdk:17-jdk

WORKDIR /app

ADD target/payment-*.jar /app/app.jar

ENTRYPOINT ["java","-jar","app.jar"]