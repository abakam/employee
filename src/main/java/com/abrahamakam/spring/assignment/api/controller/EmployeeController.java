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

    /**
     * Retrieves all saved employees from the database
     *
     * @return the saved employees
     */
    @GetMapping("/employees")
    public ResponseEntity<Collection<Employee>> getEmployees() {

        try {
            Collection<Employee> list = employeeService.findAll();

            return ResponseEntity.ok().body(list);
        } catch (Exception e) {
            throw new EmployeeInternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Retrieves all employees which fulfils the condition specified
     * in the query.
     *
     * @param query the condition to check.
     *              <br/>Examples:<br/>
     *              <code>String query = "salary > 2000"</code><br/>
     *              <code>String query = "age > 20 AND salary = 2000"</code>
     *
     * @return the employees which fulfils the condition
     */
    @GetMapping("/employees/search")
    public ResponseEntity<Set<Employee>> searchEmployees(@RequestParam("query") String query) {

        try {
            Set<Employee> list = employeeService.find(query);

            return ResponseEntity.ok().body(list);
        } catch (Exception e) {
            throw new EmployeeInternalServerErrorException(e.getMessage());
        }

    }

    /**
     * Retrieves the employee object with the specified id.
     *
     * @param employeeId a non integer representing the employee's id
     * @return the employee with the specified id
     */
    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long employeeId) {
        try {
            Employee employee = employeeService.findById(employeeId);

            if (employee == null) {
                throw new EmployeeNotFoundException(String.format("Employee with id %s not found", employeeId));
            }

            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch (Exception e) {
            throw new EmployeeInternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Persists an employee object to the database
     *
     * @param form the employee form with validation rules which
     *             contains the employee object to be persisted
     *
     * @return the saved employee object with the assigned id
     */
    @PostMapping("/employees")
    public ResponseEntity<Employee> saveEmployee(@Valid @RequestBody EmployeeForm form) {
        try {
            Set<Employee> savedEmp = employeeService.find("email = '" + form.getEmail() + "'");

            if (!savedEmp.isEmpty()) {
                throw new EmployeeException(String.format("Employee with email %s already exists ", form.getEmail()));
            }

            Employee employee = new Employee();
            form.copy(form, employee);

            employeeService.save(employee);

            return new ResponseEntity<>(employee, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new EmployeeInternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Updates an employee
     *
     * @param form the employee form with validation rules which
     *             contains the employee object to be updated
     *
     * @return the updated employee object
     */
    @PutMapping("/employees")
    public ResponseEntity<Employee> updateEmployee(@Valid @RequestBody EmployeeForm form) {
        try {
            Employee savedEmp = getEmployee(form.getId()).getBody();

            // Same object. No need for update
            if (form.equals(savedEmp)) {
                return new ResponseEntity<>(savedEmp, HttpStatus.OK);
            }

            form.copy(form, savedEmp);

            employeeService.save(savedEmp);

            return new ResponseEntity<>(savedEmp, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new EmployeeInternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Deletes a saved employee. Deleting an employee that does
     * not exist does not throw an exception. The <code>employeeId</code>
     * supplied will be returned regardless.
     *
     * @param employeeId the id of the employee to be deleted
     *
     * @return the id of the deleted employee.
     */
    @DeleteMapping("/employees/{employeeId}")
    public ResponseEntity<Long> deleteEmployee(@PathVariable Long employeeId) {
        try {
            employeeService.delete(employeeId);

            return new ResponseEntity<>(employeeId, HttpStatus.OK);
        } catch (Exception e) {
            throw new EmployeeInternalServerErrorException(e.getMessage());
        }
    }

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
}
