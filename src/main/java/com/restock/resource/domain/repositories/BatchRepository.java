package com.restock.resource.domain.repositories;

import com.restock.resource.domain.model.Batch;

import java.util.List;
import java.util.Optional;

/** Port — domain boundary for batch inventory persistence. */
public interface BatchRepository {
    List<Batch> findAll();
    Optional<Batch> findById(String id);
    List<Batch> findByBranchId(String branchId);
    List<Batch> findBySupplyId(String supplyId);
    Batch save(Batch batch);
    void deleteById(String id);
    long count();
}
