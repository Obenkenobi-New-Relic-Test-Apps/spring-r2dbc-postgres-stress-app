POSTGRES_URL=localhost:5432
POSTGRES_USERNAME=local
POSTGRES_PASSWORD=local
java -javaagent:newrelic/newrelic.jar -jar /app.jar >stdout.log &
