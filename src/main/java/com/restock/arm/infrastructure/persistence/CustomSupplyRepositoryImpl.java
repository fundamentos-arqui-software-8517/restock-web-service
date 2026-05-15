package com.restock.arm.infrastructure.persistence;

import com.restock.arm.domain.model.CustomSupply;
import com.restock.arm.domain.repositories.CustomSupplyRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CustomSupplyRepositoryImpl implements CustomSupplyRepository {

    private final MongoCustomSupplyRepository mongo;

    public CustomSupplyRepositoryImpl(MongoCustomSupplyRepository mongo) {
        this.mongo = mongo;
    }

    @Override public List<CustomSupply> findAll() { return mongo.findAll(); }
    @Override public Optional<CustomSupply> findById(String id) { return mongo.findById(id); }
    @Override public List<CustomSupply> findByBusinessId(String businessId) { return mongo.findByBusinessId(businessId); }
    @Override public List<CustomSupply> findBySupplyId(String supplyId) { return mongo.findBySupplyId(supplyId); }
    @Override public CustomSupply save(CustomSupply cs) { return mongo.save(cs); }
    @Override public void deleteById(String id) { mongo.deleteById(id); }
    @Override public long count() { return mongo.count(); }
}
