package com.restock.resource.infrastructure.persistence;

import com.restock.resource.domain.model.Batch;
import com.restock.resource.domain.repositories.BatchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BatchRepositoryImpl implements BatchRepository {

    private final MongoBatchRepository mongo;

    public BatchRepositoryImpl(MongoBatchRepository mongo) {
        this.mongo = mongo;
    }

    @Override public List<Batch> findAll() { return mongo.findAll(); }
    @Override public Optional<Batch> findById(String id) { return mongo.findById(id); }
    @Override public List<Batch> findByBranchId(String branchId) { return mongo.findByBranchId(branchId); }
    @Override public List<Batch> findBySupplyId(String supplyId) { return mongo.findBySupplyId(supplyId); }
    @Override public Batch save(Batch batch) { return mongo.save(batch); }
    @Override public void deleteById(String id) { mongo.deleteById(id); }
    @Override public long count() { return mongo.count(); }
}
