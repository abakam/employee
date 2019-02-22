package com.abrahamakam.spring.assignment.service;

import com.abrahamakam.spring.assignment.config.AppConfig;
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

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class, loader = AnnotationConfigContextLoader.class)
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Before
    public void cleanUp() {
        // Clean up the database before each test to avoid errors
        employeeService.deleteAll();
    }

    @Test
    public void testSaveEmployee() {
        Employee employee = createEmployee();
        employeeService.save(employee);

        // Employee should have an id after saving
        assertNotNull("Saved employee must have an id", employee.getId());

        assertEquals("Employee id must be 1", employee.getId().longValue(), 1L);

        Employee savedEmp = employeeService.findById(employee.getId());
        assertEquals("Saved employee must equal same fetched employee", savedEmp, employee);
    }

    @Test
    public void testUpdateEmployee() {
        Employee employee = createEmployee();
        employeeService.save(employee);

        employee.setAge(35);
        employee.setSalary(130000L);
        employee.setName("Jane Doe");
        employee.setEmail("janedoe@example.com");

        // Update
        employeeService.save(employee);

        Employee employee1 = createEmployee();

        assertNotEquals("New employee is different from updated employee", employee1, employee);
    }

    @Test
    public void testFindEmployee() {
        Collection<Employee> employees = createEmployees();
        employees.forEach(employee -> employeeService.save(employee));

        Set<Employee> employeeSet = employeeService.find("salary < 100050");
        assertEquals("Employees with salaries less than 100050 should be 1", employeeSet.size(), 1);

        Set<Employee> employeeSet1 = employeeService.find("email like '%%example.com'");
        assertEquals("Employees with email like example.com should be 2", employeeSet1.size(), 2);

        Set<Employee> employeeSet2 = employeeService.find("email like '%%example.com' and salary < 1000000");
        assertEquals(employeeSet2.size(), 2);
    }

    @Test
    public void testFindAllEmployees() {
        Collection<Employee> employees = createEmployees();
        employees.forEach(employee -> employeeService.save(employee));

        Collection<Employee> savedEmployees = employeeService.findAll();

        assertEquals("Total number of employees should be 2", savedEmployees, employees);
    }

    @Test
    public void testDeleteEmployee() {
        Employee employee = createEmployee();
        employeeService.save(employee);

        employeeService.delete(employee.getId());

        Employee savedEmp = employeeService.findById(employee.getId());
        assertNull(savedEmp);
    }

    private static Employee createEmployee() {
        Employee employee = new Employee();
        employee.setAge(36);
        employee.setSalary(120000L);
        employee.setName("John Doe");
        employee.setEmail("johndoe@example.com");

        return employee;
    }

    private static Collection<Employee> createEmployees(){
        Employee employee1 = new Employee();
        employee1.setAge(20);
        employee1.setName("Tope");
        employee1.setSalary(100000L);
        employee1.setEmail("tope@example.com");

        Employee employee2 = new Employee();
        employee2.setAge(21);
        employee2.setName("Ndubisi");
        employee2.setSalary(110000L);
        employee2.setEmail("ndubisi@example.com");

        return Arrays.asList(employee1, employee2);
    }
}


