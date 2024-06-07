# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk

# Set the working directory
WORKDIR /app

# Set build argument for the JAR file name
ARG JAR_FILE=target/*.jar

# Copy the built JAR file into the container
COPY ${JAR_FILE} /app/app.jar

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]