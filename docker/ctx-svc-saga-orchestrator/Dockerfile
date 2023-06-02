FROM openjdk:17-jdk-alpine3.14
RUN apk --no-cache add curl

COPY ./deployments/service.jar .

CMD java -jar service.jar