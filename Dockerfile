FROM openjdk:8-jre-slim

RUN mkdir /app

WORKDIR /app

ADD ./api/target/deliverers-api-1.0-SNAPSHOT.jar /app

EXPOSE 8083

CMD java -jar deliverers-api-1.0-SNAPSHOT.jar