package com.abrahamakam.spring.assignment;

import com.abrahamakam.spring.assignment.api.controller.EmployeeController;
import com.abrahamakam.spring.assignment.api.form.EmployeeForm;
import com.abrahamakam.spring.assignment.config.TestConfig;
import com.abrahamakam.spring.assignment.persistence.model.Employee;
import com.abrahamakam.spring.assignment.persistence.service.EmployeeService;
import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.validation.*;
import javax.validation.spi.ValidationProvider;
import java.util.*;

import static com.abrahamakam.spring.assignment.EmployeeTestUtils.createEmployee;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class, loader = AnnotationConfigContextLoader.class)
public class EmployeeControllerTest {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeController controller;

    @Before
    public void setUp() {
        // Clean up the database before each test to avoid errors
        employeeService.deleteAll();
    }

    @Test
    public void testSaveEmployee() {
        EmployeeForm form = createEmployee();
        form.setAge(10);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<EmployeeForm>> violations = validator.validate(form);
        assertTrue("Invalid employee age", !violations.isEmpty());

        form.setAge(15);

        ResponseEntity<Employee> entity = controller.saveEmployee(form);

        assertNotNull("Saved entity cannot be null", entity.getBody());
        assertEquals("Correct status code", entity.getStatusCode(), HttpStatus.OK);

        Employee savedEmp = employeeService.findById(Objects.requireNonNull(entity.getBody()).getId());
        assertEquals("Fetched entity equals created entity", savedEmp, entity.getBody());
    }

    @Test
    public void testUpdateEmployee() {
        EmployeeForm form = createEmployee();

        ResponseEntity<Employee> entity = controller.saveEmployee(form);
        Employee employee = entity.getBody();
        employee.setName("Janet");

        form.copy(form, employee);

        ResponseEntity<Employee> entity1 = controller.updateEmployee(form);
        Employee employee1 = entity1.getBody();

        assertEquals(employee, employee1);
    }
}
















