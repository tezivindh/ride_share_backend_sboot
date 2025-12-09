package com.tezivindh.ride_share_backend.controller;

import com.tezivindh.ride_share_backend.dto.CreateRideRequest;
import com.tezivindh.ride_share_backend.model.Ride;
import com.tezivindh.ride_share_backend.service.RideService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1")
public class RideController {

    private final RideService service;

    public RideController(RideService service) {
        this.service = service;
    }

    @PostMapping("/rides")
    public Ride requestRide(@RequestBody CreateRideRequest req, Principal principal) {
        return service.requestRide(principal.getName(), req);
    }

    @PostMapping("/rides/{id}/complete")
    public Ride complete(@PathVariable String id) {
        return service.completeRide(id);
    }
}