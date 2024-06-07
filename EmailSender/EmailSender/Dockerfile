
# Use the specific version of OpenJDK
FROM openjdk:17-jdk

# Set the working directory
WORKDIR /app

# Copy the contents of the target directory into the container
COPY target /app/

# Rename the JAR file using a RUN command
RUN mv /app/EmailSender-*.jar /app/app.jar

# Set the entry point for the container
ENTRYPOINT ["java","-jar","/app/app.jar"]