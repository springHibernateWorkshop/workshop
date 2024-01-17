# Use the official OpenJDK base image
FROM openjdk:17-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container at /app
COPY target/workshop.jar /app

# Specify the command to run on container start
CMD ["java", "-jar", "workshop.jar"]