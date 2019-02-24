package com.abrahamakam.spring.assignment.api.form;

import com.abrahamakam.spring.assignment.persistence.model.Employee;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EmployeeForm {

    private Long id;

    @NotNull(message = "Age cannot be null")
    @Min(value = 15, message = "Minimum age is 15")
    private Integer age;

    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 1, message = "Minimum name length is 1")
    private String name;

    @NotEmpty(message = "Email cannot be empty")
    @Size(min = 1, message = "Minimum email length is 1")
    private String email;

    @NotNull(message = "Salary cannot be null")
    @Min(value = 1, message = "Minimum salary is 1")
    private Long salary;

    @NotNull(message = "Gender cannot be null")
    private String gender;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    @NotNull
    public Employee.Gender getGender() {
        return Employee.Gender.valueOf(gender);
    }

    public void setGender(@NotNull String gender) {
        this.gender = gender;
    }

    public boolean equals(Employee employee) {
        if (employee == null) {
            return false;
        }

        if (id != null ? !id.equals(employee.getId()) : employee.getId() != null) return false;
        if (name != null ? !name.equals(employee.getName()) : employee.getName() != null) return false;
        if (email != null ? !email.equals(employee.getEmail()) : employee.getEmail() != null) return false;
        if (!age.equals(employee.getAge())) return false;
        return salary.equals(employee.getSalary());
    }

    public void copy(Employee employee, EmployeeForm form) {
        employee.setId(form.id);
        employee.setAge(form.age);
        employee.setName(form.name);
        employee.setEmail(form.email);
        employee.setSalary(form.salary);
        employee.setGender(form.getGender());
    }

    public void copy(EmployeeForm form, Employee employee) {
        form.id = employee.getId();
        form.age = employee.getAge();
        form.name = employee.getName();
        form.email = employee.getEmail();
        form.salary = employee.getSalary();
        form.gender= employee.getGender().name();
    }
}
