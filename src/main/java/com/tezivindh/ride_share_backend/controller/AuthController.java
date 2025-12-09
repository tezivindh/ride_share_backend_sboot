package com.tezivindh.ride_share_backend.controller;

import com.tezivindh.ride_share_backend.dto.AuthRequest;
import com.tezivindh.ride_share_backend.dto.AuthResponse;
import com.tezivindh.ride_share_backend.model.User;
import com.tezivindh.ride_share_backend.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return service.register(user);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest req) {
        return service.login(req);
    }
}