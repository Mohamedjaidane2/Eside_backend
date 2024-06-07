FROM openjdk:17-jdk

WORKDIR /app

ADD target/*.jar /app/app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]