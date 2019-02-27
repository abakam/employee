package com.abrahamakam.spring.assignment;

import com.abrahamakam.spring.assignment.api.form.EmployeeForm;
import com.abrahamakam.spring.assignment.config.TestConfig;
import com.abrahamakam.spring.assignment.persistence.model.Employee;
import com.abrahamakam.spring.assignment.persistence.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static com.abrahamakam.spring.assignment.EmployeeTestUtils.createEmployee;
import static com.abrahamakam.spring.assignment.EmployeeTestUtils.createEmployees;
import static org.junit.Assert.*;

// EmployeeGateway service test
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class, loader = AnnotationConfigContextLoader.class)
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Before
    public void setUp() {
        // Clean up the database before each test to avoid errors
        employeeService.deleteAll();
    }

    @Test
    public void testSaveEmployee() {
        Employee employee = new Employee();

        EmployeeForm form = createEmployee();
        form.copy(employee, form);

        employeeService.save(employee);

        // Employee should have an id after saving
        assertNotNull("Saved employee must have an id", employee.getId());


        // Saved Employee must be equal
        Employee savedEmp = employeeService.findById(employee.getId());
        assertEquals("Saved employee must equal same fetched employee", savedEmp, employee);
    }

    @Test
    public void testUpdateEmployee() {
        Employee employee = new Employee();

        EmployeeForm form = createEmployee();
        form.copy(employee, form);

        employeeService.save(employee);

        employee.setAge(35);
        employee.setSalary(130000L);
        employee.setName("Jane Doe");
        employee.setEmail("janedoe@example.com");

        // Update
        employeeService.save(employee);

        Employee employee1 = new Employee();

        EmployeeForm form1 = createEmployee();
        form.copy(employee1, form1);

        assertNotEquals("New employee is different from updated employee", employee1, employee);
    }

    @Test
    public void testFindEmployee() {
        Collection<EmployeeForm> employees = createEmployees();
        employees.forEach(empForm -> {
            Employee emp = new Employee();
            empForm.copy(emp, empForm);

            employeeService.save(emp);
        });

        Set<Employee> employeeSet = employeeService.find("salary < 100050");
        assertEquals("Employees with salaries less than 100050 should be 1", employeeSet.size(), 1);

        Set<Employee> employeeSet1 = employeeService.find("email like '%%example.com'");
        assertEquals("Employees with email like example.com should be 2", employeeSet1.size(), 2);

        Set<Employee> employeeSet2 = employeeService.find("email like '%%example.com' and salary < 1000000");
        assertEquals(employeeSet2.size(), 2);
    }

    @Test
    public void testFindAllEmployees() {
        Collection<EmployeeForm> employeesForm = createEmployees();
        Collection<Employee> employees = employeesForm.stream().map(form -> {
            Employee employee = new Employee();
            form.copy(employee, form);
            return employee;
        }).collect(Collectors.toList());
        employees.forEach(emp -> employeeService.save(emp));

        Collection<Employee> savedEmployees = employeeService.findAll();

        assertEquals("Total number of employees should be 2", savedEmployees, employees);
    }

    @Test
    public void testDeleteEmployee() {
        Employee employee = new Employee();

        EmployeeForm form = createEmployee();
        form.copy(employee, form);

        employeeService.save(employee);
        employeeService.delete(employee.getId());

        Employee savedEmp = employeeService.findById(employee.getId());
        assertNull(savedEmp);
    }

    @Test
    public void testCountEmployee() {

    }
}


