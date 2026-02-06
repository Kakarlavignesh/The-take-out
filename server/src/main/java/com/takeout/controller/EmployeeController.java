package com.takeout.controller;

import com.takeout.model.Employee;
import com.takeout.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable String id) {
        return employeeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Secure endpoint for Department Head to view salaries
    @PostMapping("/salaries")
    public ResponseEntity<?> getViewSalaries(@RequestBody Map<String, String> payload) {
        String secretCode = payload.get("secretCode");
        // Simple security check (In real app, use Spring Security)
        if ("admin".equals(secretCode) || "SECRET".equals(secretCode)) {
            return ResponseEntity.ok(employeeRepository.findAll());
        }
        return ResponseEntity.status(403).body("Unauthorized: Invalid Secret Code");
    }
}
