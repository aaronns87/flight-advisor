FROM adoptopenjdk:14-jre-openj9
MAINTAINER aaronns87@gmail.com

RUN apt-get update && apt-get install -y --no-install-recommends curl \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

ARG APP_NAME=flight-advisor
ARG APP_VERSION=0.0.1-SNAPSHOT

RUN adduser --system "$APP_NAME" && mkdir /log  && chown "$APP_NAME" /log

USER $APP_NAME

COPY build/libs/"$APP_NAME"-"$APP_VERSION".jar app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
