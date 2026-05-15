package com.restock.profiles.infrastructure.persistence;

import com.restock.profiles.domain.model.Business;
import com.restock.profiles.domain.repositories.BusinessRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BusinessRepositoryImpl implements BusinessRepository {

    private final MongoBusinessRepository mongo;

    public BusinessRepositoryImpl(MongoBusinessRepository mongo) {
        this.mongo = mongo;
    }

    @Override public List<Business> findAll() { return mongo.findAll(); }
    @Override public Optional<Business> findById(String id) { return mongo.findById(id); }
    @Override public List<Business> findByUserId(String userId) { return mongo.findByUserId(userId); }
    @Override public Business save(Business business) { return mongo.save(business); }
    @Override public void deleteById(String id) { mongo.deleteById(id); }
    @Override public long count() { return mongo.count(); }
}
