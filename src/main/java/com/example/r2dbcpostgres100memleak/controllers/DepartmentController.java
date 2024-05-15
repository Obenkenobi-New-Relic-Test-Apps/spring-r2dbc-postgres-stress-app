package com.example.r2dbcpostgres100memleak.controllers;

import com.example.r2dbcpostgres100memleak.models.Department;
import com.example.r2dbcpostgres100memleak.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    final
    DepartmentService departmentService;

    public DepartmentController(@Autowired DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    Flux<Department> getAll() {
        return departmentService.getAll();
    }
}
