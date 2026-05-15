package com.restock.profiles.infrastructure.persistence;

import com.restock.profiles.domain.model.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

interface MongoProfileRepository extends MongoRepository<Profile, String> {
    List<Profile> findByUserId(String userId);
}
