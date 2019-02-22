package com.abrahamakam.spring.assignment.persistence.service;

import com.abrahamakam.spring.assignment.persistence.model.Employee;

import java.util.Collection;
import java.util.Set;

public interface EmployeeService {

    Employee findById(Long id);

    Collection<Employee> findAll();

    Set<Employee> find(String condition);

    void save(Employee employee);

    void delete(Long id);

    int deleteAll();

    Long count();
}
