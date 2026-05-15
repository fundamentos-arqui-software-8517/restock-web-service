package com.restock.iam.infrastructure.persistence;

import com.restock.iam.domain.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/** Spring Data MongoDB adapter — hidden behind UserRepository domain port. */
interface MongoUserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
