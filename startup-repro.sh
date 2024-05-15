POSTGRES_URL=localhost:5432
POSTGRES_USERNAME=postgres
POSTGRES_PASSWORD=memleak1
nohup java -javaagent:./newrelic/newrelic.jar -jar ./app.jar >stdout.log &
