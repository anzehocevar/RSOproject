
FROM maven:3.9.8-eclipse-temurin-21 AS build
COPY ./ /app
WORKDIR /app
RUN mvn clean package

FROM openjdk:21
RUN mkdir /app
WORKDIR /app
COPY --from=build ./app/target/prijava-1.0.0-SNAPSHOT.jar /app
EXPOSE 8081
CMD ["java", "-jar", "prijava-1.0.0-SNAPSHOT.jar"]


#Za DockerHub:
# create docker repository nakupovalko-prijava

#docker login
# docker build -t vm0012/nakupovalko-prijava:1.0.0 .
#  docker push vm0012/nakupovalko-prijava:1.0.0
# preveri docker hub ce se je uspesno pushalo

#test usage:
#docker pull vm0012/nakupovalko-prijava:1.0.0
#docker run -p 8081:8081 vm0012/nakupovalko-prijava:1.0.0