package com.restock.sales.infrastructure.persistence;

import com.restock.sales.domain.model.Sale;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

interface MongoSaleRepository extends MongoRepository<Sale, String> {
    List<Sale> findByBusinessId(String businessId);
    List<Sale> findByBranchId(String branchId);
}
