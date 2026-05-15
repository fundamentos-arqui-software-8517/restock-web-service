package com.restock.arm.infrastructure.persistence;

import com.restock.arm.domain.model.CustomSupply;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

interface MongoCustomSupplyRepository extends MongoRepository<CustomSupply, String> {
    List<CustomSupply> findByBusinessId(String businessId);
    List<CustomSupply> findBySupplyId(String supplyId);
}
