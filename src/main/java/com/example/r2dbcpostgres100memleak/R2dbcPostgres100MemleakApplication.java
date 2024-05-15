package com.example.r2dbcpostgres100memleak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableR2dbcRepositories
@EnableWebFlux
@EnableScheduling
public class R2dbcPostgres100MemleakApplication {

    public static void main(String[] args) {
        SpringApplication.run(R2dbcPostgres100MemleakApplication.class, args);
    }

}
