# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk

# Set the working directory
WORKDIR /app

# Copy the built JAR file into the container
COPY target/EmailSender-*.jar /app/app.jar

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]