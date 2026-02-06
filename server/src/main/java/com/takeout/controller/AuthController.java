package com.takeout.controller;

import com.takeout.model.Employee;
import com.takeout.model.User;
import com.takeout.repository.EmployeeRepository;
import com.takeout.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody Map<String, String> payload) {
        // payload.get("mobile"); // Logic to send OTP (Simulated)
        return ResponseEntity.ok(Map.of("message", "OTP sent", "otp", "1234"));
    }

    @PostMapping("/customer-login")
    public ResponseEntity<?> customerLogin(@RequestBody Map<String, String> payload) {
        String mobile = payload.get("mobile");
        String otp = payload.get("otp");

        if ("1234".equals(otp)) {
            User user = userRepository.findByMobile(mobile)
                    .orElseGet(() -> {
                        User newUser = new User();
                        newUser.setMobile(mobile);
                        return userRepository.save(newUser);
                    });
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(401).body("Invalid OTP");
    }

    @PostMapping("/staff-login")
    public ResponseEntity<?> staffLogin(@RequestBody Map<String, String> payload) {
        String id = payload.get("id");
        String pass = payload.get("password");

        Optional<Employee> emp = employeeRepository.findById(id);
        if (emp.isPresent() && emp.get().getPassword() != null && emp.get().getPassword().equals(pass)) {
            return ResponseEntity.ok(emp.get());
        }
        return ResponseEntity.status(401).body("Invalid Credentials");
    }

    // Phase 2: Staff Signup
    @PostMapping("/staff-signup")
    public ResponseEntity<?> staffSignup(@RequestBody Employee emp) {
        if (employeeRepository.existsById(emp.getId())) {
            return ResponseEntity.badRequest().body("Employee ID already exists");
        }
        // Initialize defaults
        emp.setPerformanceRating(0.0);
        emp.setGoodStandingCount(0);
        emp.setDaysPresent(0);

        return ResponseEntity.ok(employeeRepository.save(emp));
    }
}
