package com.abrahamakam.spring.assignment.persistence.dao;

import com.abrahamakam.spring.assignment.persistence.model.Employee;

import java.util.Collection;
import java.util.Set;

// EmployeeGateWay Interface
public interface EmployeeDAO {

    Employee findById(Long id);

    Collection<Employee> findAll();

    Set<Employee> find(String condition);

    void save(Employee employee);

    void delete(Long id);

    Long count();

    int deleteAll();
}
