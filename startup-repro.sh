export POSTGRES_URL=localhost:5432/flyway-test-db
export POSTGRES_USERNAME=postgres
export POSTGRES_PASSWORD=memleak1
nohup java -javaagent:./newrelic/newrelic.jar -jar ./app.jar >stdout.log &
