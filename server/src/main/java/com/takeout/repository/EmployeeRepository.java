package com.takeout.repository;

import com.takeout.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    // ID is String (8 digits)
}
