package com.restock.iam.infrastructure.persistence;

import com.restock.iam.domain.model.User;
import com.restock.iam.domain.repositories.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final MongoUserRepository mongo;

    public UserRepositoryImpl(MongoUserRepository mongo) {
        this.mongo = mongo;
    }

    @Override public Optional<User> findByEmail(String email) { return mongo.findByEmail(email); }
    @Override public boolean existsByEmail(String email) { return mongo.existsByEmail(email); }
    @Override public User save(User user) { return mongo.save(user); }
    @Override public Optional<User> findById(String id) { return mongo.findById(id); }
}
