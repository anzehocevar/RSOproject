
FROM maven:3.8.3-openjdk-17 AS build
COPY ./ /app
WORKDIR /app
RUN mvn clean package

FROM eclipse-temurin:17-jre
RUN mkdir /app
WORKDIR /app
COPY --from=build ./app/api/target/api-1.0.0-SNAPSHOT.jar /app
EXPOSE 8081
CMD ["java", "-jar", "api-1.0.0-SNAPSHOT.jar"]


#Za DockerHub:
#docker login
#mvn clean package
#docker build -t primerjalnikcen .
#docker tag primerjalnikcen {username}/primerjalnikcen
#docker push {username}/primerjalnikcen