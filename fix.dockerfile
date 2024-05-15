FROM --platform=linux/arm64 arm64v8/eclipse-temurin:21-jdk-alpine
VOLUME /tmp
COPY build/libs/r2dbc-postgres-100-memleak-0.0.1-SNAPSHOT.jar app.jar
COPY newrelicfix newrelicfix
ENTRYPOINT ["java", "-javaagent:newrelicfix/newrelic.jar", "-jar", "/app.jar"]