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

    // Simple in-memory OTP storage for demo purposes
    private static final java.util.concurrent.ConcurrentHashMap<String, String> otpStorage = new java.util.concurrent.ConcurrentHashMap<>();

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody Map<String, String> payload) {
        String mobile = payload.get("mobile");
        if (mobile == null || mobile.isEmpty()) {
            return ResponseEntity.badRequest().body("Mobile number is required");
        }

        // Generate Random 6-digit OTP
        String otp = String.valueOf((int) (Math.random() * 900000) + 100000);
        otpStorage.put(mobile, otp);

        // Simulate SMS Delivery by logging to server console (hidden from frontend
        // user)
        System.out.println("\n---------------------------------------------------");
        System.out.println(" [SMS GATEWAY SIMULATION] Sending SMS to: " + mobile);
        System.out.println(" [OTP MESSAGE] Your verification code is: " + otp);
        System.out.println("---------------------------------------------------\n");

        // Return success WITH the OTP in the response body (For Demo Simulation)
        return ResponseEntity.ok(Map.of(
                "message", "OTP sent successfully to mobile number",
                "otp", otp));
    }

    @PostMapping("/customer-login")
    public ResponseEntity<?> customerLogin(@RequestBody Map<String, String> payload) {
        String mobile = payload.get("mobile");
        String otp = payload.get("otp");

        String storedOtp = otpStorage.get(mobile);

        if (storedOtp != null && storedOtp.equals(otp)) {
            // Clear OTP after successful use
            otpStorage.remove(mobile);

            User user = userRepository.findByMobile(mobile)
                    .orElseGet(() -> {
                        User newUser = new User();
                        newUser.setMobile(mobile);
                        return userRepository.save(newUser);
                    });
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(401).body("Invalid or Expired OTP");
    }

    @PostMapping("/staff-login")
    public ResponseEntity<?> staffLogin(@RequestBody Map<String, String> payload) {
        String id = payload.get("id");
        String pass = payload.get("password");

        System.out.println("Login Attempt: ID=" + id); // Debug Log

        Optional<Employee> emp = employeeRepository.findById(id);
        if (emp.isPresent() && emp.get().getPassword() != null && emp.get().getPassword().equals(pass)) {
            // Generate Mock JWT Token (UUID for demo)
            String token = java.util.UUID.randomUUID().toString();

            // Return User + Token
            return ResponseEntity.ok(Map.of(
                    "user", emp.get(),
                    "token", token));
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

    @PostMapping("/google-login")
    public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String name = payload.get("name");

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setName(name);
                    newUser.setGoogleLogin(true);
                    return userRepository.save(newUser);
                });
        return ResponseEntity.ok(user);
    }
}
