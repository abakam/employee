package com.abrahamakam.spring.assignment.persistence.dao;

import com.abrahamakam.spring.assignment.persistence.model.Employee;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;
import com.abrahamakam.spring.assignment.persistence.service.EmployeeService;
import com.abrahamakam.spring.assignment.api.controller.EmployeeController;

/**
 * Data access layer which provides the business interaction between
 * with the {@link EmployeeController} class and the {@link EmployeeService}
 */
public interface EmployeeDAO {

    /**
     * Finds an employee via the specified id. If the
     * employee does not exist, <code>null</code> is returned.
     *
     * @param id the id of the employee to find
     * @return the found employee or null
     */
    Employee findById(Long id);

    /**
     * Retrieves all employees saved in the database.
     *
     * @return a collection of all the saved employees.
     */
    Collection<Employee> findAll();

    /**
     * Retrieves all the unique employees which match the
     * specified condition. An {@link IllegalArgumentException}
     * exception will be thrown if the condition is not valid.
     * Valid conditions follow the SQL where statements.
     *
     * <br><br>For example:<br>
     * <code>String condition = "email = johndoe@example.com"</code>
     *
     * @param condition the condition to check.
     * @return the employees which match the specified condition
     * or an empty set
     */
    Set<Employee> find(String condition);

    /**
     * Persists the specified employee object to the database.
     * If an id is specified in the employee object, an update
     * operation will be performed else a save operation will
     * be performed instead.
     *
     * Throws an {@link SQLException} if all the fields
     * except the id filled is <code>null</code> or empty
     * or the email already exists.
     *
     * @param employee the employee object to be saved
     */
    void save(Employee employee);

    /**
     * Deletes an employee from the database.
     * This method checks if the employee with the specified id
     * exists. If the employee is found, the employee is then
     * deleted.
     *
     * @param id the id of the employee to be deleted.
     */
    void delete(Long id);

    /**
     * Retrieves the total number of employees saved in the database.
     *
     * @return the total number of employees saved.
     */
    Long count();

    int deleteAll();
}
