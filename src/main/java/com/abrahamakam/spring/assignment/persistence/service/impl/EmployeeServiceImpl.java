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

    @Override
    public Employee findById(Long id) {
        return employeeDAO.findById(id);
    }

    @Override
    public Collection<Employee> findAll() {
        return employeeDAO.findAll();
    }

    @Override
    public Set<Employee> find(String condition) {
        return employeeDAO.find(condition);
    }

    @Override
    public void save(Employee employee) {
        employeeDAO.save(employee);
    }

    @Override
    public void delete(Long id) {
        employeeDAO.delete(id);
    }

    @Override
    public int deleteAll() {
        return employeeDAO.deleteAll();
    }

    @Override
    public Long count() {
        return employeeDAO.count();
    }

    @Autowired
    public void setEmployeeDAO(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }
}
