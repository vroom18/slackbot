FROM openjdk:8-jre-alpine
COPY target/slackbot-0.0.1-SNAPSHOT.war slackbot-0.0.1-SNAPSHOT.war
CMD ["java","-jar","slackbot-0.0.1-SNAPSHOT.war"]
EXPOSE 8080
