package com.tezivindh.ride_share_backend.service;

import com.tezivindh.ride_share_backend.dto.CreateRideRequest;
import com.tezivindh.ride_share_backend.model.Ride;
import com.tezivindh.ride_share_backend.repository.RideRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RideService {

    private final RideRepository repo;

    public RideService(RideRepository repo) {
        this.repo = repo;
    }

    public Ride requestRide(String userId, CreateRideRequest req) {
        Ride ride = new Ride();
        ride.setUserId(userId);
        ride.setPickupLocation(req.getPickupLocation());
        ride.setDropLocation(req.getDropLocation());
        ride.setStatus("REQUESTED");
        return repo.save(ride);
    }

    public List<Ride> getUserRides(String userId) {
        return repo.findByUserId(userId);
    }

    public List<Ride> getPendingRides() {
        return repo.findByStatus("REQUESTED");
    }

    public Ride acceptRide(String rideId, String driverId) {
        Ride ride = repo.findById(rideId).orElseThrow();
        if (!ride.getStatus().equals("REQUESTED")) throw new RuntimeException("Already accepted");

        ride.setDriverId(driverId);
        ride.setStatus("ACCEPTED");
        return repo.save(ride);
    }

    public Ride completeRide(String rideId) {
        Ride ride = repo.findById(rideId).orElseThrow();
        if (!ride.getStatus().equals("ACCEPTED")) throw new RuntimeException("Not active");

        ride.setStatus("COMPLETED");
        return repo.save(ride);
    }
}