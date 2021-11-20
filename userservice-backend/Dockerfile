FROM adoptopenjdk/openjdk11:alpine-jre

RUN addgroup did

RUN adduser did -h /opt/did/ -G did -D

RUN apk --no-cache add curl

USER did:did

RUN mkdir /opt/did/config

ENV JAVA_OPTS="-XshowSettings:vm"

ARG JAR_FILE
COPY target/$JAR_FILE /opt/did/userservice.jar

WORKDIR /opt/did/
ENTRYPOINT exec java $JAVA_OPTS -jar userservice.jar