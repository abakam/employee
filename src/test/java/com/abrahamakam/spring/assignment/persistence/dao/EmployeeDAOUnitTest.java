package com.abrahamakam.spring.assignment.persistence.dao;

import com.abrahamakam.spring.assignment.api.exception.EmployeeException;
import com.abrahamakam.spring.assignment.config.UnitTestConfig;
import com.abrahamakam.spring.assignment.factory.EmployeeFactory;
import com.abrahamakam.spring.assignment.persistence.model.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UnitTestConfig.class, loader = AnnotationConfigContextLoader.class)
public class EmployeeDAOUnitTest {

    @Mock
    private EmployeeDAO dao;

    @Autowired
    private EmployeeFactory factory;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testWhen_GetEmployeeFromFactory_EmployeeIsNotNull() {
        Employee emp = factory.getObject();

        assertNotNull(emp);
        assertNotNull(emp.getAge());
        assertNotNull(emp.getName());
        assertNotNull(emp.getEmail());
        assertNotNull(emp.getGender());
        assertNotNull(emp.getSalary());

        // Id will be null since its not added by the object factory
    }

    @Test
    public void testWhen_SaveEmployee_ReturnEmployeeWithId() {
        long id = 1L;

        Employee newEmp = factory.getObject();
        newEmp.setId(id);

        /*
         * Mocking the save method to return newEmp
         */
        doNothing().when(dao).save(newEmp);

        /*
         * Performing the actual save
         */
        dao.save(newEmp);

        /*
         * Verify the number of executions that took place.
         * Should be 1.
         * The #copyEmployee is necessary so that the called by
         * #findById in the next line won't refer to the same object
         * as newEmp
         */
        verify(dao, times(1)).save(copyEmployee(newEmp));

        /*
         * Mock the #findById to verify if the employee
         * was actually saved
         */
        when(dao.findById(id)).thenReturn(newEmp);

        /*
         * Verification
         */
        assertEquals(Long.valueOf(id), dao.findById(id).getId());
    }

    @Test(expected = EmployeeException.class)
    public void testWhen_SaveEmployeeWithNullSalary_ThrowAnEmployeeException() {
        Employee newEmp = factory.getObject();
        newEmp.setSalary(null);

        doThrow(EmployeeException.class).when(dao).save(newEmp);

        dao.save(newEmp);
    }

    @Test
    public void testWhen_SaveEmployee_VerifyValueSaved() {
        Long id = 10L;

        Employee newEmp = factory.getObject();
        newEmp.setId(id);

        doNothing().when(dao).save(newEmp);

        dao.save(newEmp);

        verify(dao, times(1)).save(newEmp);

        when(dao.findById(id)).thenReturn(copyEmployee(newEmp));

        Employee savedEmp = dao.findById(id);

        assertEquals(savedEmp, newEmp);
    }

    @Test
    public void testWhen_FindEmployeeWithIdExist_ReturnEmployee() {
        Employee savedEmp = factory.getObject();
        savedEmp.setId(10L);

        when(dao.findById(savedEmp.getId())).thenReturn(savedEmp);

        Employee foundEmp = dao.findById(savedEmp.getId());

        assertEquals(savedEmp, foundEmp);
    }

    @Test
    public void testWhen_FindEmployeeWithIdNotExist_ReturnNull() {
        when(dao.findById(anyLong())).thenReturn(null);

        Employee employee = dao.findById(10L);

        assertNull(employee);
    }

    @Test
    public void testWhen_DeleteEmployee_FindEmployeeByIdShouldBeNull() {
        long id = 10L;

        doNothing().when(dao).delete(id);

        dao.delete(id);

        verify(dao, times(1)).delete(id);

        when(dao.findById(id)).thenReturn(null);

        assertNull(dao.findById(id));
    }

    @Test
    public void testWhen_FindAllEmployees_EmployeesShouldMatch() {
        /*
         * Generate Employee objects from the Employee object factory
         */
        List<Employee> employeeList = Stream.generate(factory::getObject)
                                            .limit(10)
                                            .collect(Collectors.toList());

        /* To prevent reference to employeeList */
        List<Employee> employees = employeeList.stream()
                                            .map(this::copyEmployee)
                                            .collect(Collectors.toList());

        when(dao.findAll()).thenReturn(employees);

        /* Method call */
        List<Employee> fetchedEmps = new ArrayList<>(dao.findAll());

        Stream.iterate(0, i -> i + 1)
                .limit(10)
                .forEach(index -> assertEquals(fetchedEmps.get(index), employeeList.get(index)));
    }

    @Test
    public void testWhen_RecordsAreSaved_TheirCountShouldMatch() {
        List<Employee> employeeList = Stream.generate(factory::getObject)
                                            .limit(10)
                                            .collect(Collectors.toList());
        employeeList.forEach(emp -> {

            doNothing().when(dao).save(emp);

            /*
             * Performing the actual save
             */
            dao.save(emp);

            /*
             * Verify the number of executions that took place.
             * Should be 1.
             * The #copyEmployee is necessary so that the called by
             * #findById in the next line won't refer to the same object
             * as newEmp
             */
            verify(dao, times(1)).save(copyEmployee(emp));
        });

        List<Employee> employees = employeeList.stream()
                                            .map(this::copyEmployee)
                                            .collect(Collectors.toList());

        when(dao.count()).thenReturn((long) employeeList.size());

        assertEquals(dao.count(), Long.valueOf(employees.size()));
    }

    @Test
    public void testWhen_FindEmployeeWithEmail_ReturnEmployees() {
        Employee emp = factory.getObject();

        String condition = "email = '" + emp.getEmail() + "'";

        when(dao.find(condition)).thenReturn(Collections.singleton(copyEmployee(emp)));

        assertTrue(dao.find(condition).contains(emp));
    }

    @Test
    public void testWhen_FindEmployeeOlderThan20Years_ReturnEmployees() {
        String condition = "age > 20";

        Set<Employee> employeesOlderThan20Years = Stream.generate(factory::getObject)
                .limit(10)
                .filter(e -> e.getAge() > 20)
                .collect(Collectors.toSet());

        when(dao.find(condition)).thenReturn(
                employeesOlderThan20Years.stream()
                        .map(this::copyEmployee)
                        .collect(Collectors.toSet())
        );

        Set<Employee> fetchedEmployeesOlderThan20Years = dao.find(condition);

        assertEquals(employeesOlderThan20Years, fetchedEmployeesOlderThan20Years);
    }

    private Employee copyEmployee(Employee employee) {
        Employee newEmp = new Employee();
        newEmp.setId(employee.getId());
        newEmp.setAge(employee.getAge());
        newEmp.setName(employee.getName());
        newEmp.setEmail(employee.getEmail());
        newEmp.setGender(employee.getGender());
        newEmp.setSalary(employee.getSalary());

        return newEmp;
    }
}
