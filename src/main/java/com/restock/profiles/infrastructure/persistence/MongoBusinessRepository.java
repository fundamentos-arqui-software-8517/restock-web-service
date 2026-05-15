package com.restock.profiles.infrastructure.persistence;

import com.restock.profiles.domain.model.Business;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

interface MongoBusinessRepository extends MongoRepository<Business, String> {
    List<Business> findByUserId(String userId);
}
