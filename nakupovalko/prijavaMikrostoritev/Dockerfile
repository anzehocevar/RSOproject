FROM eclipse-temurin:21

RUN mkdir /nakupovalko
WORKDIR /nakupovalko

ADD ./api/target/api-1.0.0-SNAPSHOT.jar /nakupovalko

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "api-1.0.0-SNAPSHOT.jar"]


#Za DockerHub:
#docker login
#mvn clean package
#docker build -t primerjalnikcen .
#docker tag primerjalnikcen {username}/primerjalnikcen
#docker push {username}/primerjalnikcen