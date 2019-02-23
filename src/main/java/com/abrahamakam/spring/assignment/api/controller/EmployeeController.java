package com.abrahamakam.spring.assignment.api.controller;

import com.abrahamakam.spring.assignment.api.exception.EmployeeException;
import com.abrahamakam.spring.assignment.api.exception.EmployeeNotFoundException;
import com.abrahamakam.spring.assignment.api.form.EmployeeForm;
import com.abrahamakam.spring.assignment.persistence.model.Employee;
import com.abrahamakam.spring.assignment.persistence.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<Employee> getEmployee(@PathVariable Long employeeId) {
        Employee employee = employeeService.findById(employeeId);

        if (employee == null) {
            throw new EmployeeNotFoundException("Employee with id not found - " + employeeId);
        }

        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping("/employee")
    public ResponseEntity<Employee> saveEmployee(@Valid @RequestBody EmployeeForm form) {
        Set<Employee> savedEmp = employeeService.find("email = '" + form.getEmail() + "'");

        if (!savedEmp.isEmpty()) {
            throw new EmployeeException("Employee with email already exists - " + form.getEmail());
        }

        Employee employee = new Employee();
        form.copy(employee, form);

        employeeService.save(employee);

        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PutMapping("/employee")
    public ResponseEntity<Employee> updateEmployee(@Valid @RequestBody EmployeeForm form) {
        Employee savedEmp = getEmployee(form.getId()).getBody();

        // Same object. No need for update
        if (form.equals(savedEmp)) {
            return new ResponseEntity<>(savedEmp, HttpStatus.OK);
        }

        form.copy(savedEmp, form);

        employeeService.save(savedEmp);

        return new ResponseEntity<>(savedEmp, HttpStatus.OK);
    }

    @DeleteMapping("/employee/{employeeId}")
    public Long deleteEmployee(@PathVariable Long employeeId) {
        getEmployee(employeeId);
        employeeService.delete(employeeId);

        return employeeId;
    }

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
}
