FROM adoptopenjdk/openjdk11:alpine-jre

RUN addgroup tsmiecho

RUN adduser tsmiecho -h /opt/tsmiecho/ -G tsmiecho -D

RUN apk --no-cache add curl

USER tsmiecho:tsmiecho

RUN mkdir /opt/tsmiecho/config

ENV JAVA_OPTS="-XshowSettings:vm"

ARG JAR_FILE
COPY target/$JAR_FILE /opt/tsmiecho/userservice.jar

WORKDIR /opt/tsmiecho/
ENTRYPOINT exec java $JAVA_OPTS -jar userservice.jar
