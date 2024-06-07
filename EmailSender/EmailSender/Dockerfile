FROM openjdk:17-jdk

WORKDIR /app

ADD target/EmailSender-*.jar /app/app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]