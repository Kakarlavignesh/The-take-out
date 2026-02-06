package com.takeout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TheTakeoutApplication {

    @org.springframework.context.annotation.Bean
    public org.springframework.boot.CommandLineRunner demo(com.takeout.repository.EmployeeRepository repository) {
        return (args) -> {
            // Seed Admin User
            if (repository.findById("admin").isEmpty()) {
                com.takeout.model.Employee admin = new com.takeout.model.Employee();
                admin.setId("admin");
                admin.setName("Administrator");
                admin.setPassword("admin");
                admin.setRole("Head"); // or Manager
                admin.setDepartment("Management");
                admin.setSalary(0.0);
                repository.save(admin);
                System.out.println("----------------------------------------");
                System.out.println("   DEMO ADMIN CREATED: ID=admin PASS=admin");
                System.out.println("----------------------------------------");
            }
        };
    }

    public static void main(String[] args) {
        System.out.println("----------------------------------------");
        System.out.println("   THE TAKEOUT SERVER IS STARTING...    ");
        System.out.println("   PLEASE WAIT FOR 'STARTED' MESSAGE    ");
        System.out.println("----------------------------------------");
        SpringApplication.run(TheTakeoutApplication.class, args);
        System.out.println("----------------------------------------");
        System.out.println("   SERVER IS RUNNING ON PORT 9091       ");
        System.out.println("   OPEN BROWSER: http://localhost:9091  ");
        System.out.println("----------------------------------------");
    }

}
