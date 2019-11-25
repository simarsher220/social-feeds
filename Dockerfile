FROM openjdk:10-jre-slim

LABEL Name="Social Media Platform" \
            Product="Feeds"

EXPOSE 8080

RUN mkdir /container
COPY target/social-feeds-0.0.1-SNAPSHOT.jar /container/feeds-api.jar

WORKDIR /container

ENTRYPOINT exec java $JAVA_OPTS -jar feeds-api.jar