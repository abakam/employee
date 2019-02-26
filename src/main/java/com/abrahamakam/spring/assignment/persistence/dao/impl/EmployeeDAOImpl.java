package com.abrahamakam.spring.assignment.persistence.dao.impl;

import com.abrahamakam.spring.assignment.persistence.dao.EmployeeDAO;
import com.abrahamakam.spring.assignment.persistence.model.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Repository
@Transactional
// EmployeeGateWay Implementation
public class EmployeeDAOImpl implements EmployeeDAO {

    private SessionFactory sessionFactory;

    public EmployeeDAOImpl() {

    }

    // takes Long and returns Employee instance with ID if it exists
    @Override
    public Employee findById(Long id) {
        return sessionFactory.getCurrentSession().get(Employee.class, id);
    }

    // returns all Employees
    @Override
    public Collection<Employee> findAll() {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = builder.createQuery(Employee.class);

        Root<Employee> root = criteriaQuery.from(Employee.class);
        criteriaQuery.select(root);

        Query<Employee> query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    /*
    * accepts a string parameter where that contains arbitrary condition
    * (e.g. "email like '%gmail.com and salary < 1000'") and returns Employees set
    * */
    @Override
    public Set<Employee> find(String condition) {
        // Recipe for SQL injection!!! A better way?
        Session session = sessionFactory.getCurrentSession();

        String hqlQuery = String.format("FROM %s WHERE %s", Employee.class.getName(), condition);

        Query<Employee> query = session.createQuery(hqlQuery, Employee.class);

        return new HashSet<>(query.getResultList());
    }

    /*
     * takes Employee instance as a parameter and
     * if it has an ID assigned it performs update otherwise it performs insert
     * (use if-else for INSERT or UPDATE) (your table should use ID autoincrement)
     * */
    @Override
    public void save(@NonNull Employee employee) {
        if (employee.getId() == null) {
            sessionFactory.getCurrentSession().save(employee);
        }
        else {
            sessionFactory.getCurrentSession().update(employee);
        }
    }


    // takes Long and removes Employee with ID instance if exists
    @Override
    public void delete(@NonNull Long id) {
        Employee employee = findById(id);

        if (employee != null) {
            sessionFactory.getCurrentSession().delete(employee);
        }
    }
    // returns the total number of employees.
    @Override
    public Long count() {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);

        Root<Employee> root = criteriaQuery.from(Employee.class);
        criteriaQuery.select(builder.count(root));

        Query<Long> query = session.createQuery(criteriaQuery);
        return query.getSingleResult();
    }

    @Override
    public int deleteAll() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(String.format("DELETE FROM %s", Employee.class.getName()));
        return query.executeUpdate();
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
