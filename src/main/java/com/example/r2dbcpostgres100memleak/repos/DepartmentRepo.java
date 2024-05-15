package com.example.r2dbcpostgres100memleak.repos;

import com.example.r2dbcpostgres100memleak.models.Department;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface DepartmentRepo extends R2dbcRepository<Department, String> {
    Flux<Department> findAllByName(String name);

    @Query("select * FROM department where name = :name ")
    Flux<Department> findDocumentsByNameQuery(String name);

    @Query("CALL create_document(:name)")
    Mono<Void> createDocumentProcedure(String name);
}
