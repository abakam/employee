package com.abrahamakam.spring.assignment.api.controller;

import com.abrahamakam.spring.assignment.api.exception.EmployeeException;
import com.abrahamakam.spring.assignment.api.exception.EmployeeNotFoundException;
import com.abrahamakam.spring.assignment.persistence.model.Employee;
import com.abrahamakam.spring.assignment.persistence.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;

@RestController
public class EmployeeController {

    private EmployeeService employeeService;

    @GetMapping("/employees")
    public Collection<Employee> getEmployees() {
        return employeeService.findAll();
    }

    @GetMapping("/employee/{employeeId}")
    public Employee getEmployee(@PathVariable Long employeeId) {
        Employee employee = employeeService.findById(employeeId);

        if (employee == null) {
            throw new EmployeeNotFoundException("Employee with id not found - " + employeeId);
        }

        return employee;
    }

    @PostMapping("/employee")
    public Employee addEmployee(@RequestBody Employee employee) {
        employee.setId(null);

        Set<Employee> savedEmp = employeeService.find("email = '" + employee.getEmail() + "'");
        if (!savedEmp.isEmpty()) {
            throw new EmployeeException("Employee with email already exists - " + employee.getEmail());
        }

        employeeService.save(employee);

        return employee;
    }

    @PutMapping("/employee")
    public Employee updateEmployee(@RequestBody Employee employee) {
        Employee savedEmp = getEmployee(employee.getId());

        // Same object. No need for update
        if (savedEmp.equals(employee)) {
            return employee;
        }

        employeeService.save(employee);

        return employee;
    }

    @DeleteMapping("/employee/{employeeId}")
    public Long deleteEmployee(@PathVariable Long employeeId) {
        employeeService.delete(employeeId);
        return employeeId;
    }

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
}
