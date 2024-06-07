FROM openjdk:17-jdk

ADD ./target/EmailSender-*.jar /app/app.jar
CMD ["java", "-Xmx200m", "-jar", "/app/app.jar","app.jar"]
