package com.restock.resource.domain.repositories;

import com.restock.resource.domain.model.Supply;

import java.util.List;
import java.util.Optional;

/** Port — domain boundary for supply catalog persistence. */
public interface SupplyRepository {
    List<Supply> findAll();
    Optional<Supply> findById(String id);
    List<Supply> findByCategory(String category);
    Supply save(Supply supply);
    void deleteById(String id);
    long count();
}
