package com.restock.iam.domain.repositories;

import com.restock.iam.domain.model.User;

import java.util.Optional;

/** Port — domain boundary for user persistence. No framework dependency. */
public interface UserRepository {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    User save(User user);
    Optional<User> findById(String id);
}
