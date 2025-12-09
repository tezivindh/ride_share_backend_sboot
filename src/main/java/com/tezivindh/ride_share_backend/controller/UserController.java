package com.tezivindh.ride_share_backend.controller;

import com.tezivindh.ride_share_backend.model.Ride;
import com.tezivindh.ride_share_backend.service.RideService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final RideService service;

    public UserController(RideService service) {
        this.service = service;
    }

    @GetMapping("/rides")
    public List<Ride> myRides(Principal principal) {
        return service.getUserRides(principal.getName());
    }
}