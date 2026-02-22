package com.hospital.discharge.controller;

import com.hospital.discharge.model.DischargeSummary;
import com.hospital.discharge.service.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://ec2-13-203-67-191.ap-south-1.compute.amazonaws.com:3000")
public class SummaryController {

    @Autowired
    private SummaryService summaryService;

    @GetMapping("/generate-summary/{patientId}")
    public ResponseEntity<?> generateSummary(@PathVariable String patientId) {
        try {
            DischargeSummary summary = summaryService.generateSummary(patientId);
            return new ResponseEntity<>(summary, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Patient not found: " + e.getMessage(), 
                                      HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error generating summary: " + e.getMessage(), 
                                      HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/export-pdf/{patientId}")
    public ResponseEntity<?> exportPDF(@PathVariable String patientId) {
        try {
            // PDF generation logic would go here
            // For demo, we return a success message
            String message = "PDF generated successfully for patient: " + patientId;
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error generating PDF: " + e.getMessage(), 
                                      HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return new ResponseEntity<>("Smart Discharge Summary API is running!", HttpStatus.OK);
    }
}
