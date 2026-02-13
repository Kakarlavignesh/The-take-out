package com.takeout.model;

import jakarta.persistence.*;

@Entity
public class Employee {
    @Id
    @Column(length = 50)
    private String id; // Flexible ID length

    private String name;
    private String role; // e.g., Head, Vice Head, Staff
    private String department;

    private Double salary;

    // Performance Tracking
    private Double performanceRating = 0.0; // Yearly performance
    private Integer goodStandingCount = 0; // Count of "Excellent" feedbacks

    // Attendance
    private Integer daysPresent = 0;

    // For Department Heads
    private String password;

    public Employee() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Double getPerformanceRating() {
        return performanceRating;
    }

    public void setPerformanceRating(Double performanceRating) {
        this.performanceRating = performanceRating;
    }

    public Integer getGoodStandingCount() {
        return goodStandingCount;
    }

    public void setGoodStandingCount(Integer goodStandingCount) {
        this.goodStandingCount = goodStandingCount;
    }

    public Integer getDaysPresent() {
        return daysPresent;
    }

    public void setDaysPresent(Integer daysPresent) {
        this.daysPresent = daysPresent;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", department='" + department + '\'' +
                ", salary=" + salary +
                ", performanceRating=" + performanceRating +
                ", goodStandingCount=" + goodStandingCount +
                ", daysPresent=" + daysPresent +
                '}';
    }
}
