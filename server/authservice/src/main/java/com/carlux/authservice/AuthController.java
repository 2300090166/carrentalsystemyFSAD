package com.carlux.authservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // Password validation function
    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody Auth auth) {
        // Enforce strong password validation
        if (!isValidPassword(auth.getPassword())) {
            return ResponseEntity.badRequest().body("Password must be at least 8 characters, include a number and a symbol.");
        }

        String message = authService.register(auth);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody Auth auth) {
        String token = authService.authenticate(auth.getUsername(), auth.getPassword());

        if (!token.equals("Invalid credentials!")) {
            return ResponseEntity.ok(new AuthResponse(token, "Login successful"));
        }

        return ResponseEntity.status(401).body(new AuthResponse(null, "Invalid credentials"));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(403).body("Invalid token!");
        }

        return ResponseEntity.ok("Logout successful!");
    }
}