package com.restock.profiles.infrastructure.persistence;

import com.restock.profiles.domain.model.Profile;
import com.restock.profiles.domain.repositories.ProfileRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProfileRepositoryImpl implements ProfileRepository {

    private final MongoProfileRepository mongo;

    public ProfileRepositoryImpl(MongoProfileRepository mongo) {
        this.mongo = mongo;
    }

    @Override public List<Profile> findAll() { return mongo.findAll(); }
    @Override public Optional<Profile> findById(String id) { return mongo.findById(id); }
    @Override public List<Profile> findByUserId(String userId) { return mongo.findByUserId(userId); }
    @Override public Profile save(Profile profile) { return mongo.save(profile); }
    @Override public void deleteById(String id) { mongo.deleteById(id); }
    @Override public long count() { return mongo.count(); }
}
