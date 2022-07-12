package com.app.controller;

import com.app.entity.Employee;
import com.app.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    @GetMapping
    public Flux<Employee> findAll(){
        return employeeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Employee> getById(@PathVariable String id){
        return employeeRepository.getEmployeeById(id);
    }

    @GetMapping("/email")
    public Mono<Employee> getByEmail(@RequestParam String email){
        return employeeRepository.findByEmail(email);
    }

    @GetMapping("/name")
    public Mono<Employee> getByName(@RequestParam String name){
        return employeeRepository.findByName(name);
    }


    @PostMapping
    public Mono<Employee> save(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }


    @DeleteMapping("/{id}")
    public Mono<Employee> delete(@PathVariable String id){
        return employeeRepository.delete(id);
    }

    @PutMapping("/{id}")
    public Mono<Employee> save(@PathVariable String id, @RequestBody Employee employee){
        return employeeRepository.update(id, employee);
    }

}
