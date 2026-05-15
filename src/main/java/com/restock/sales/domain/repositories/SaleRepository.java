package com.restock.sales.domain.repositories;

import com.restock.sales.domain.model.Sale;

import java.util.List;
import java.util.Optional;

/** Port — domain boundary for sale persistence. */
public interface SaleRepository {
    List<Sale> findAll();
    Optional<Sale> findById(String id);
    List<Sale> findByBusinessId(String businessId);
    List<Sale> findByBranchId(String branchId);
    Sale save(Sale sale);
    long count();
}
