export POSTGRES_URL=localhost:5432
export POSTGRES_USERNAME=postgres
export POSTGRES_PASSWORD=memleak1
nohup java -javaagent:./newrelicfix/newrelic.jar -jar ./app.jar >stdout.log &