package com.restock.resource.infrastructure.persistence;

import com.restock.resource.domain.model.Supply;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

interface MongoSupplyRepository extends MongoRepository<Supply, String> {
    List<Supply> findByCategory(String category);
}
