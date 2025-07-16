package com.carlux.authservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    // Password validation regex (Min 8 characters, 1 number, 1 symbol)
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");

    public String register(Auth auth) {
        if (authRepository.findByUsername(auth.getUsername()).isPresent()) {
            throw new RuntimeException("User already exists!");
        }

        // Enforce strong password validation
        if (!PASSWORD_PATTERN.matcher(auth.getPassword()).matches()) {
            throw new RuntimeException("Password must be at least 8 characters, include a number and a symbol.");
        }

        auth.setPassword(passwordEncoder.encode(auth.getPassword()));
        authRepository.save(auth);
        return "Registration successful!";
    }

    public String authenticate(String username, String password) {
        Optional<Auth> user = authRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            throw new RuntimeException("Invalid credentials!");
        }

        return jwtUtil.generateToken(username);
    }
}