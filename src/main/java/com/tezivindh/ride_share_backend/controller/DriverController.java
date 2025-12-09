package com.tezivindh.ride_share_backend.controller;

import com.tezivindh.ride_share_backend.model.Ride;
import com.tezivindh.ride_share_backend.service.RideService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/driver")
public class DriverController {

    private final RideService service;

    public DriverController(RideService service) {
        this.service = service;
    }

    @GetMapping("/rides/requests")
    public List<Ride> getPending() {
        return service.getPendingRides();
    }

    @PostMapping("/rides/{id}/accept")
    public Ride accept(@PathVariable String id, Principal principal) {
        return service.acceptRide(id, principal.getName());
    }
}