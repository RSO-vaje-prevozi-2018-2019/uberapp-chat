FROM openjdk:8-jre-slim

RUN mkdir /app

WORKDIR /app

ADD ./api/target/chat-api-1.0.0-SNAPSHOT.jar /app

EXPOSE 8080

CMD java -jar chat-api-1.0.0-SNAPSHOT.jar