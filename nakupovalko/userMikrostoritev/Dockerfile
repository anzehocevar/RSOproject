FROM maven:3.8.3-openjdk-17 AS build
COPY ./ /app
WORKDIR /app
RUN mvn clean package

FROM eclipse-temurin:17-jre
RUN mkdir /app
WORKDIR /app
COPY --from=build ./app/target/user-1.0.0-SNAPSHOT.jar /app
EXPOSE 8081
CMD ["java", "-jar", "user-1.0.0-SNAPSHOT.jar"]
