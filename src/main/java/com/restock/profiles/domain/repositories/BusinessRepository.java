package com.restock.profiles.domain.repositories;

import com.restock.profiles.domain.model.Business;

import java.util.List;
import java.util.Optional;

/** Port — domain boundary for business persistence. No framework dependency. */
public interface BusinessRepository {
    List<Business> findAll();
    Optional<Business> findById(String id);
    List<Business> findByUserId(String userId);
    Business save(Business business);
    void deleteById(String id);
    long count();
}
