package com.restock.resource.infrastructure.persistence;

import com.restock.resource.domain.model.Batch;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

interface MongoBatchRepository extends MongoRepository<Batch, String> {
    List<Batch> findByBranchId(String branchId);
    List<Batch> findBySupplyId(String supplyId);
}
