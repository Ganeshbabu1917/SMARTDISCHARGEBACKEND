package com.hospital.discharge.controller;

import com.hospital.discharge.model.User;
import com.hospital.discharge.dto.LoginRequest;
import com.hospital.discharge.dto.SignupRequest;
import com.hospital.discharge.dto.UserResponse;
import com.hospital.discharge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // ✅ TEST ENDPOINT - Add this!
    @GetMapping("/test")
    public String test() {
        return "Auth API is working!";
    }

    // SIGN UP - Create new user
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        try {
            System.out.println("📝 Signup request received: " + signupRequest.getEmail());
            
            // Check if email already exists
            if (userRepository.existsByEmail(signupRequest.getEmail())) {
                return new ResponseEntity<>(
                    new UserResponse(null, null, signupRequest.getEmail(), "Email already registered"),
                    HttpStatus.BAD_REQUEST
                );
            }
            
            // Create new user
            User user = new User();
            user.setName(signupRequest.getName());
            user.setEmail(signupRequest.getEmail());
            user.setPassword(signupRequest.getPassword());
            user.setCreatedAt(LocalDateTime.now());
            
            User savedUser = userRepository.save(user);
            System.out.println("✅ User saved with ID: " + savedUser.getId());
            
            UserResponse response = new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                "User created successfully"
            );
            
            return new ResponseEntity<>(response, HttpStatus.CREATED);
            
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(
                new UserResponse(null, null, null, "Error: " + e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    // LOGIN - Authenticate user
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
            
            if (userOptional.isEmpty()) {
                return new ResponseEntity<>(
                    new UserResponse(null, null, loginRequest.getEmail(), "User not found"),
                    HttpStatus.NOT_FOUND
                );
            }
            
            User user = userOptional.get();
            
            // Check password
            if (!user.getPassword().equals(loginRequest.getPassword())) {
                return new ResponseEntity<>(
                    new UserResponse(null, null, loginRequest.getEmail(), "Invalid password"),
                    HttpStatus.UNAUTHORIZED
                );
            }
            
            // Update last login
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);
            
            UserResponse response = new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                "Login successful"
            );
            
            return new ResponseEntity<>(response, HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<>(
                new UserResponse(null, null, null, "Error: " + e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}