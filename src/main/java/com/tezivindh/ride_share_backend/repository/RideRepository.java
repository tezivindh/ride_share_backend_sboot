package com.tezivindh.ride_share_backend.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.tezivindh.ride_share_backend.model.Ride;

import java.util.List;

public interface RideRepository extends MongoRepository<Ride, String> {

    List<Ride> findByUserId(String userId);

    List<Ride> findByStatus(String status);
}
