package com.abrahamakam.spring.assignment.api.controller;

import com.abrahamakam.spring.assignment.api.exception.EmployeeException;
import com.abrahamakam.spring.assignment.api.exception.EmployeeInternalServerErrorException;
import com.abrahamakam.spring.assignment.api.exception.EmployeeNotFoundException;
import com.abrahamakam.spring.assignment.api.form.EmployeeForm;
import com.abrahamakam.spring.assignment.persistence.model.Employee;
import com.abrahamakam.spring.assignment.persistence.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Set;

@RestController
public class EmployeeController {

    private EmployeeService employeeService;

    @GetMapping("/employees")
    public ResponseEntity<Collection<Employee>> getEmployees() {

       try {
           Collection<Employee> list = employeeService.findAll();

           return  ResponseEntity.ok().body(list);
       }
        catch (Exception e) {
            throw  new EmployeeInternalServerErrorException("Internal Server Error");
        }
    }

    @GetMapping("/employees/search")
    public  ResponseEntity<Set<Employee>> searchEmployees(@RequestParam("query") String query) {

        try {
            Set<Employee> list = employeeService.find(query);

            return  ResponseEntity.ok().body(list);
        }
        catch (Exception e) {
            throw  new EmployeeInternalServerErrorException("Internal Server Error");
        }

    }

    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long employeeId) {
        try {
            Employee employee = employeeService.findById(employeeId);

            if (employee == null) {
                throw new EmployeeNotFoundException(String.format("Employee with id %s not found", employeeId));
            }

            return new ResponseEntity<>(employee, HttpStatus.OK);
        }
        catch (Exception e) {
            throw  new EmployeeInternalServerErrorException("Internal Server Error");
        }
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> saveEmployee(@Valid @RequestBody EmployeeForm form) {
        try {
            Set<Employee> savedEmp = employeeService.find("email = '" + form.getEmail() + "'");

            if (!savedEmp.isEmpty()) {
                throw new EmployeeException(String.format("Employee with email %s already exists ", form.getEmail()));
            }

            Employee employee = new Employee();
            form.copy(employee, form);

            employeeService.save(employee);

            return new ResponseEntity<>(employee, HttpStatus.OK);
        }
        catch (Exception e) {
            throw  new EmployeeInternalServerErrorException("Internal Server Error");
        }
    }

    @PutMapping("/employees")
    public ResponseEntity<Employee> updateEmployee(@Valid @RequestBody EmployeeForm form) {
        try {
            Employee savedEmp = getEmployee(form.getId()).getBody();

            // Same object. No need for update
            if (form.equals(savedEmp)) {
                return new ResponseEntity<>(savedEmp, HttpStatus.OK);
            }

            form.copy(savedEmp, form);

            employeeService.save(savedEmp);

            return new ResponseEntity<>(savedEmp, HttpStatus.OK);
        }
        catch (Exception e) {
            throw  new EmployeeInternalServerErrorException("Internal Server Error");
        }
    }

    @DeleteMapping("/employees/{employeeId}")
    public ResponseEntity<Long> deleteEmployee(@PathVariable Long employeeId) {
        try {
            employeeService.delete(employeeId);

            return new ResponseEntity<>(employeeId, HttpStatus.OK);
        }
        catch (Exception e) {
            throw  new EmployeeInternalServerErrorException("Internal Server Error");
        }
    }

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
}
