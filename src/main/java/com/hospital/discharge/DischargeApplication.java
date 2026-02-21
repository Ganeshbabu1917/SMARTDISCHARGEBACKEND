package com.hospital.discharge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DischargeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DischargeApplication.class, args);
        System.out.println("\n" +
            "╔════════════════════════════════════════════════════════════╗\n" +
            "║     🏥 SMART DISCHARGE SUMMARY GENERATOR 🏥              ║\n" +
            "║                                                            ║\n" +
            "║   ✅ Backend Server Started Successfully!                 ║\n" +
            "║   📍 API URL: http://localhost:8086                       ║\n" +
            "║   🔗 Health Check: http://localhost:8086/api/health       ║\n" +
            "║   💾 Database: MySQL Connected                            ║\n" +
            "║                                                            ║\n" +
            "║   📋 Available Endpoints:                                 ║\n" +
            "║   • GET  /api/patients                                    ║\n" +
            "║   • GET  /api/patients/{patientId}                        ║\n" +
            "║   • POST /api/patients/add                                ║\n" +
            "║   • GET  /api/generate-summary/{patientId}                ║\n" +
            "║   • GET  /api/export-pdf/{patientId}                      ║\n" +
            "║                                                            ║\n" +
            "╚════════════════════════════════════════════════════════════╝\n");
    }

    @GetMapping("/")
    public String home() {
        return "<h1>🏥 Smart Discharge Summary Generator API</h1>" +
               "<p>Server is running. Use <a href='/api/health'>/api/health</a> to check status.</p>";
    }
}