package com.takeout.controller;

import com.takeout.model.Employee;
import com.takeout.model.Feedback;
import com.takeout.repository.EmployeeRepository;
import com.takeout.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin(origins = "*")
public class FeedbackController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping
    public ResponseEntity<?> submitFeedback(@RequestBody Feedback feedback) {
        if (feedback.getEmployee() != null && feedback.getEmployee().getId() != null) {
            Employee emp = employeeRepository.findById(feedback.getEmployee().getId()).orElse(null);
            if (emp != null) {
                feedback.setEmployee(emp);

                // Salary Hike Logic: excellent (9-10) -> count towards bonus
                if (feedback.getRating() >= 9.0) {
                    emp.setGoodStandingCount(emp.getGoodStandingCount() + 1);
                    // In a real app, we'd check date/month-end here.
                    // For demo, we just track the count.
                    // "2.5% Hike" logic would typically be a batch job.
                }
                employeeRepository.save(emp);
            }
        }

        Feedback saved = feedbackRepository.save(feedback);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/employee/{empId}")
    public List<Feedback> getEmployeeFeedback(@PathVariable String empId) {
        return feedbackRepository.findByEmployeeId(empId);
    }
}
