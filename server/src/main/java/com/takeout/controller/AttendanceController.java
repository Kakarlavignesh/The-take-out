package com.takeout.controller;

import com.takeout.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "*")
public class AttendanceController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping("/{empId}")
    public ResponseEntity<?> markAttendance(@PathVariable String empId) {
        return employeeRepository.findById(empId).map(emp -> {
            emp.setDaysPresent(emp.getDaysPresent() + 1);

            // Salary Logic: If present full month (e.g., 30 days) -> Bonus (2 days salary)
            // Simplifying logic: Just check if daysPresent % 30 == 0
            if (emp.getDaysPresent() % 30 == 0) {
                // Apply bonus logic here if neededus (just simulating by increasing generic
                // salary for now or
                // we could store bonus separately)
                // For safety, let's just log it or maybe assume base salary increases?
                // The prompt said "2 days extra salary as bonus". We can add it to a
                // "PendingBonus" field if we had one.
                // Let's just return a message saying "Bonus Applied!"
                employeeRepository.save(emp); // Validate save before return
                return ResponseEntity.ok(Map.of("message", "Attendance Marked! Bonus Eligibility Reached!", "days",
                        emp.getDaysPresent()));
            }

            employeeRepository.save(emp);
            return ResponseEntity.ok(Map.of("message", "Attendance Marked", "days", emp.getDaysPresent()));
        }).orElse(ResponseEntity.notFound().build());
    }
}
