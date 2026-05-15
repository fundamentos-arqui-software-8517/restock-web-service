package com.restock.sales.infrastructure.persistence;

import com.restock.sales.domain.model.Sale;
import com.restock.sales.domain.repositories.SaleRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SaleRepositoryImpl implements SaleRepository {

    private final MongoSaleRepository mongo;

    public SaleRepositoryImpl(MongoSaleRepository mongo) {
        this.mongo = mongo;
    }

    @Override public List<Sale> findAll() { return mongo.findAll(); }
    @Override public Optional<Sale> findById(String id) { return mongo.findById(id); }
    @Override public List<Sale> findByBusinessId(String businessId) { return mongo.findByBusinessId(businessId); }
    @Override public List<Sale> findByBranchId(String branchId) { return mongo.findByBranchId(branchId); }
    @Override public Sale save(Sale sale) { return mongo.save(sale); }
    @Override public long count() { return mongo.count(); }
}
