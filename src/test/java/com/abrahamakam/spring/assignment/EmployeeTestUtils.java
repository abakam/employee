package com.abrahamakam.spring.assignment;

import com.abrahamakam.spring.assignment.api.form.EmployeeForm;

import java.util.Arrays;
import java.util.Collection;

class EmployeeTestUtils {

    static EmployeeForm createEmployee() {
        EmployeeForm employee = new EmployeeForm();
        employee.setAge(36);
        employee.setSalary(120000L);
        employee.setName("John Doe");
        employee.setEmail("johndoe@example.com");

        return employee;
    }

    static Collection<EmployeeForm> createEmployees(){
        EmployeeForm employee1 = new EmployeeForm();
        employee1.setAge(20);
        employee1.setName("Tope");
        employee1.setSalary(100000L);
        employee1.setEmail("tope@example.com");

        EmployeeForm employee2 = new EmployeeForm();
        employee2.setAge(21);
        employee2.setName("Ndubisi");
        employee2.setSalary(110000L);
        employee2.setEmail("ndubisi@example.com");

        return Arrays.asList(employee1, employee2);
    }
}
