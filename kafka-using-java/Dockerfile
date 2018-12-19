FROM openjdk:8-jre-alpine

RUN apk add --update \
    libc6-compat

# copy WAR into image
COPY target/kafka-using-java-1.0.0-jar-with-dependencies.jar /app.war
COPY start.sh /start.sh
RUN chmod +x /start.sh

# run application with this command line

CMD ["/start.sh"]