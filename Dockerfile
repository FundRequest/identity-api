FROM dmreiland/alpine-oraclejdk10:slim

VOLUME /tmp
ADD target/identity-api-0.0.1-SNAPSHOT.jar identity-api.jar
ADD libs/dd-java-agent.jar dd-java-agent.jar
RUN sh -c 'touch /identity-api.jar' && \
    mkdir config

ENV JAVA_OPTS=""

EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -javaagent:/dd-java-agent.jar -Djava.security.egd=file:/dev/./urandom -Duser.timezone=UTC -jar /identity-api.jar" ]
