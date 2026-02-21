package com.hospital.discharge.controller;

import com.hospital.discharge.model.Patient;
import com.hospital.discharge.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "http://localhost:3000")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    // ========== GET ALL PATIENTS ==========
    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        try {
            List<Patient> patients = patientRepository.findAll();
            return new ResponseEntity<>(patients, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ========== GET PATIENT BY ID ==========
    @GetMapping("/{patientId}")
    public ResponseEntity<?> getPatientByPatientId(@PathVariable String patientId) {
        try {
            Optional<Patient> patient = patientRepository.findByPatientId(patientId);
            if (patient.isPresent()) {
                return new ResponseEntity<>(patient.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Patient not found with ID: " + patientId, 
                                          HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error: " + e.getMessage(), 
                                      HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ========== ADD NEW PATIENT ==========
    @PostMapping("/add")
    public ResponseEntity<?> addPatient(@RequestBody Patient patient) {
        try {
            System.out.println("📝 Adding new patient: " + patient.getName());
            
            // Check if patient already exists
            if (patientRepository.existsByPatientId(patient.getPatientId())) {
                return new ResponseEntity<>("Patient ID already exists", 
                                          HttpStatus.BAD_REQUEST);
            }
            
            // Set discharge date to current date if not provided
            if (patient.getDischargeDate() == null) {
                patient.setDischargeDate(LocalDate.now());
            }
            
            Patient savedPatient = patientRepository.save(patient);
            System.out.println("✅ Patient saved with ID: " + savedPatient.getId());
            
            return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
            
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error adding patient: " + e.getMessage(), 
                                      HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ========== UPDATE PATIENT ==========
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        try {
            Optional<Patient> existingPatient = patientRepository.findById(id);
            if (existingPatient.isPresent()) {
                patient.setId(id);
                Patient updatedPatient = patientRepository.save(patient);
                return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Patient not found with ID: " + id, 
                                          HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error updating patient: " + e.getMessage(), 
                                      HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ========== DELETE PATIENT ==========
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable Long id) {
        try {
            if (patientRepository.existsById(id)) {
                patientRepository.deleteById(id);
                return new ResponseEntity<>("Patient deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Patient not found with ID: " + id, 
                                          HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error deleting patient: " + e.getMessage(), 
                                      HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}