package com.tezivindh.ride_share_backend.repository;

import com.tezivindh.ride_share_backend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
}
