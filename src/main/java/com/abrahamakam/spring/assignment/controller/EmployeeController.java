package com.abrahamakam.spring.assignment.controller;

import com.abrahamakam.spring.assignment.persistence.model.Employee;
import com.abrahamakam.spring.assignment.persistence.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class EmployeeController {

    private EmployeeService employeeService;

    @GetMapping("/employees")
    public Collection<Employee> getEmployees() {
        return employeeService.findAll();
    }

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
}
