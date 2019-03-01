package com.abrahamakam.spring.assignment.factory;

import com.abrahamakam.spring.assignment.persistence.model.Employee;
import org.springframework.beans.factory.FactoryBean;

import java.util.Random;

public class EmployeeFactory implements FactoryBean<Employee> {

    @Override
    public Employee getObject() {
        Random random = new Random();

        Employee newEmp = new Employee();
        newEmp.setAge(random.nextInt() % 30);
        newEmp.setName(NAMES[random.nextInt(MAX)]);
        newEmp.setEmail(EMAILS[random.nextInt(MAX)]);
        newEmp.setSalary(random.nextInt(MAX) * 2000L);
        newEmp.setGender(random.nextInt() % 2 == 0 ? Employee.Gender.MALE : Employee.Gender.FEMALE);

        return newEmp;
    }

    @Override
    public Class<?> getObjectType() {
        return Employee.class;
    }

    private static final String[] NAMES = {
            "Abraham", "Kumagar", "George", "Ekene", "Afolabi"
    };

    private static final String[] EMAILS = {
            "email_1@example.com", "email_2@example.com",
            "email_3@example.com", "email_4@example.com",
            "email_5@example.com"
    };

    private static final int MAX = NAMES.length;
}
