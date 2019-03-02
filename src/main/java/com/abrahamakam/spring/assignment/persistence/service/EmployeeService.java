package com.abrahamakam.spring.assignment.persistence.service;

import com.abrahamakam.spring.assignment.persistence.model.Employee;

import java.util.Collection;
import java.util.Set;
import com.abrahamakam.spring.assignment.persistence.dao.EmployeeDAO;

/**
 * The employee service class which delegates
 * calls to the {@link EmployeeDAO} class
 */
public interface EmployeeService {

    /**
     * @see EmployeeDAO#findById(Long)
     */
    Employee findById(Long id);

    /**
     * @see EmployeeDAO#findAll()
     */
    Collection<Employee> findAll();

    /**
     * @see EmployeeDAO#find(String)
     */
    Set<Employee> find(String condition);

    /**
     * @see EmployeeDAO#save(Employee)
     */
    void save(Employee employee);

    /**
     * @see EmployeeDAO#delete(Long)
     */
    void delete(Long id);

    /**
     * @see EmployeeDAO#deleteAll()
     */
    int deleteAll();

    Long count();
}
