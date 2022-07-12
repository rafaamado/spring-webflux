package com.app.controller;

import com.app.entity.Employee;
import com.app.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    @PostMapping
    public Employee save(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable String id){
        return employeeRepository.getEmployeeById(id);
    }

    @DeleteMapping("/{id}")
    public Employee delete(@PathVariable String id){
        return employeeRepository.delete(id);
    }

    @PutMapping("/{id}")
    public String save(@PathVariable String id, @RequestBody Employee employee){
        return employeeRepository.update(id, employee);
    }

}
