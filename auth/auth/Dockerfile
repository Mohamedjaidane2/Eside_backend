FROM openjdk:17-jdk

WORKDIR /app

ADD target/auth-*.jar /app/app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]