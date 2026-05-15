package com.restock.arm.domain.repositories;

import com.restock.arm.domain.model.CustomSupply;

import java.util.List;
import java.util.Optional;

/** Port — domain boundary for custom supply persistence. */
public interface CustomSupplyRepository {
    List<CustomSupply> findAll();
    Optional<CustomSupply> findById(String id);
    List<CustomSupply> findByBusinessId(String businessId);
    List<CustomSupply> findBySupplyId(String supplyId);
    CustomSupply save(CustomSupply customSupply);
    void deleteById(String id);
    long count();
}
