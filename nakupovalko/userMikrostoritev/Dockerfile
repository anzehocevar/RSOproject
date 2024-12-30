FROM maven:3.9.8-eclipse-temurin-21 AS build
COPY ./ /app
WORKDIR /app
RUN mvn clean package

FROM openjdk:21
RUN mkdir /app
WORKDIR /app
COPY --from=build ./app/target/user-1.0.0-SNAPSHOT.jar /app
EXPOSE 8082
CMD ["java", "-jar", "user-1.0.0-SNAPSHOT.jar"]


# docker build -t vm0012/nakupovalko-user:1.0.0 .
#  docker push vm0012/nakupovalko-user:1.0.0
# preveri docker hub ce se je uspesno pushalo
#test usage:
#docker pull vm0012/nakupovalko-user:1.0.0
#docker run -p 8082:8082 vm0012/nakupovalko-user:1.0.0