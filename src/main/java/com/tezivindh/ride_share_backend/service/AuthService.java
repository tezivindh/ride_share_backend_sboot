package com.tezivindh.ride_share_backend.service;

import com.tezivindh.ride_share_backend.dto.AuthRequest;
import com.tezivindh.ride_share_backend.dto.AuthResponse;
import com.tezivindh.ride_share_backend.model.User;
import com.tezivindh.ride_share_backend.repository.UserRepository;
import com.tezivindh.ride_share_backend.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwt;

    public AuthService(UserRepository userRepo,
                       PasswordEncoder encoder,
                       JwtUtil jwt) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    // REGISTER USER
    public String register(User user) {

        if (userRepo.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }

        // Encode password
        user.setPassword(encoder.encode(user.getPassword()));

        // Force roles to start with ROLE_
        if (!user.getRole().startsWith("ROLE_")) {
            user.setRole("ROLE_" + user.getRole());
        }

        userRepo.save(user);
        return "User registered!";
    }

    // LOGIN USER
    public AuthResponse login(AuthRequest req) {

        // Fetch user from database
        User user = userRepo.findByUsername(req.getUsername());

        if (user == null) {
            throw new RuntimeException("Invalid username/password");
        }

        // Validate password manually
        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username/password");
        }

        // Generate JWT
        String token = jwt.generateToken(user.getUsername(), user.getRole());
        return new AuthResponse(token);
    }
}
