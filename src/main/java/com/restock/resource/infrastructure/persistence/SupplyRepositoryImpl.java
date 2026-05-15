package com.restock.resource.infrastructure.persistence;

import com.restock.resource.domain.model.Supply;
import com.restock.resource.domain.repositories.SupplyRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SupplyRepositoryImpl implements SupplyRepository {

    private final MongoSupplyRepository mongo;

    public SupplyRepositoryImpl(MongoSupplyRepository mongo) {
        this.mongo = mongo;
    }

    @Override public List<Supply> findAll() { return mongo.findAll(); }
    @Override public Optional<Supply> findById(String id) { return mongo.findById(id); }
    @Override public List<Supply> findByCategory(String category) { return mongo.findByCategory(category); }
    @Override public Supply save(Supply supply) { return mongo.save(supply); }
    @Override public void deleteById(String id) { mongo.deleteById(id); }
    @Override public long count() { return mongo.count(); }
}
