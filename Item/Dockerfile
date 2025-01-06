# Build stage
FROM maven:3.9.0-eclipse-temurin-17 AS build
COPY ./ /app
WORKDIR /app
# Copy all project files into the container's build directory
# Run Maven to build the project
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:17-jdk-slim
RUN mkdir /app
WORKDIR /app
# Copy the built JAR from the build stage to the runtime container
COPY --from=build ./app/target/Item-0.0.1-SNAPSHOT.jar /app
# Expose the application's port
EXPOSE 8089
# Start the application
ENTRYPOINT ["java", "-jar", "Item-0.0.1-SNAPSHOT.jar"]


## Use a lightweight base image with OpenJDK
#FROM openjdk:17-jdk-slim
#
## Set the working directory
#WORKDIR /app
#
## Copy the built JAR file from the target directory
#COPY target/Item-0.0.1-SNAPSHOT.jar app.jar
#
## Expose the port that the application runs on
#EXPOSE 8089
#
## Define the entry point for the container
#ENTRYPOINT ["java", "-jar", "/app.jar"]
