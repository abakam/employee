package com.abrahamakam.spring.assignment.persistence.service.impl;

import com.abrahamakam.spring.assignment.persistence.dao.EmployeeDAO;
import com.abrahamakam.spring.assignment.persistence.model.Employee;
import com.abrahamakam.spring.assignment.persistence.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeDAO employeeDAO;

    public EmployeeServiceImpl() {}

    /**
     * {@inheritDoc}
     */
    @Override
    public Employee findById(Long id) {
        return employeeDAO.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Employee> findAll() {
        return employeeDAO.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Employee> find(String condition) {
        return employeeDAO.find(condition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Employee employee) {
        employeeDAO.save(employee);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) {
        employeeDAO.delete(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteAll() {
        return employeeDAO.deleteAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long count() {
        return employeeDAO.count();
    }

    @Autowired
    public void setEmployeeDAO(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }
}
