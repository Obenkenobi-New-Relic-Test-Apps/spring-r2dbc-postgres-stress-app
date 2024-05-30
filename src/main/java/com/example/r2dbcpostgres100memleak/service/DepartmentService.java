package com.example.r2dbcpostgres100memleak.service;

import com.example.r2dbcpostgres100memleak.models.Department;
import com.example.r2dbcpostgres100memleak.repos.DepartmentRepo;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private static final Logger log = LoggerFactory.getLogger(DepartmentService.class);
    final DepartmentRepo departmentRepo;

    public DepartmentService(@Autowired DepartmentRepo departmentRepo) {
        this.departmentRepo = departmentRepo;
    }

    @Scheduled(fixedRate = 5)
    @Trace(dispatcher = true, metricName = "retrieveAndLogData")
    public void retrieveAndLogData() {
        log.info("start retrieveAndLogData txn {}", NewRelic.getAgent().getTransaction());
        Token token = NewRelic.getAgent().getTransaction().getToken();
        getAll().collectList().subscribe(list -> handleSubscribe(token, () -> {
            List<String> strVals = list.stream().map(Objects::toString).collect(Collectors.toList());
            log.info("Get every department {}", strVals);
            log.info("retrieveAndLogData txn {}", NewRelic.getAgent().getTransaction());
        }), err -> handleError(err, token));
    }

    @Scheduled(fixedRate = 5)
    @Trace(dispatcher = true, metricName = "retrieveAndLogCSData")
    public void retrieveAndLogCSData() {
        log.info("start retrieveAndLogCSData txn {}", NewRelic.getAgent().getTransaction());
        Token token = NewRelic.getAgent().getTransaction().getToken();
        departmentRepo.findAllByName("Computer Science")
                .collectList()
                .subscribe(list -> handleSubscribe(token, () -> {
                    List<String> strVals = list.stream().map(Objects::toString).collect(Collectors.toList());
                    log.info("Get every CS department {}", strVals);
                    log.info("retrieveAndLogCSData txn {}", NewRelic.getAgent().getTransaction());
                }), err -> handleError(err, token));
    }

    @Scheduled(fixedRate = 15)
    @Trace(dispatcher = true, metricName = "retrieveAndLogEngDataQuery")
    public void retrieveAndLogCSDataQuery() {
        log.info("start retrieveAndLogEngDataQuery txn {}", NewRelic.getAgent().getTransaction());
        Token token = NewRelic.getAgent().getTransaction().getToken();
        departmentRepo.findDocumentsByNameQuery("English")
                .collectList()
                .subscribe(list -> handleSubscribe(token, () -> {
                    List<String> strVals = list.stream().map(Objects::toString).collect(Collectors.toList());
                    log.info("Get every Eng department query {}", strVals);
                    log.info("retrieveAndLogEngDataQuery txn {}", NewRelic.getAgent().getTransaction());
                }), err -> handleError(err, token));
    }

    @Scheduled(fixedRate = 200)
    @Trace(dispatcher = true, metricName = "insertDocStoredProc")
    public void insertDocStoredProc() {
        log.info("start insertDocStoredProc txn {}", NewRelic.getAgent().getTransaction());
        Token token = NewRelic.getAgent().getTransaction().getToken();
        departmentRepo.createDocumentProcedure("English")
                .map(ignored -> 0)
                .defaultIfEmpty(0)
                .subscribe(ignored -> handleSubscribe(token, () -> {
                    log.info("Added english department");
                    log.info("insertDocStoredProc txn {}", NewRelic.getAgent().getTransaction());
                }), err -> handleError(err, token));
    }

    @Trace(dispatcher = true, metricName = "deleteData")
    @Scheduled(fixedRate = 500)
    public void deleteData() {
        log.info("start deleteData txn {}", NewRelic.getAgent().getTransaction());
        Token token = NewRelic.getAgent().getTransaction().getToken();
        deleteAll().subscribe(list -> handleSubscribe(token, () -> {
            log.info("deleteData {}", NewRelic.getAgent().getTransaction());
        }), err -> handleError(err, token));
    }

    @Trace(dispatcher = true, metricName = "bulkInsert")
    @Scheduled(fixedRate = 100)
    public void bulkInsert() {
        log.info("start bulkInsert txn {}", NewRelic.getAgent().getTransaction());
        Token token = NewRelic.getAgent().getTransaction().getToken();
        insertMany().collectList().subscribe(list -> handleSubscribe(token, () -> {
            List<String> strVals = list.stream().map(Objects::toString).collect(Collectors.toList());
            log.info("Saved data {}", strVals);
            log.info("bulkInsert txn (saved data {}", NewRelic.getAgent().getTransaction());
        }), err -> handleError(err, token));
    }

    public Mono<Void> deleteAll() {
        return departmentRepo.deleteAll();
    }

    public Flux<Department> insertMany() {
        List<Department> departments = List.of(
                new Department(null, "Computer Science"),
                new Department(null, "Biomedical Science"));

        return departmentRepo.saveAll(departments);
    }
    public Flux<Department> getAll() {
        return departmentRepo.findAll();
    }

    private <T> void handleSubscribe(Token token, Effect consumer) {
        token.linkAndExpire();
        consumer.accept();
    }

    private void handleError(Throwable t, Token token) {
        token.linkAndExpire();
        NewRelic.noticeError(t);
        log.error("r2dbc error", t);
    }

    @FunctionalInterface
    private interface Effect {
        void accept();
    }
}
