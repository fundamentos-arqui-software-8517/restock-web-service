package com.restock.profiles.domain.repositories;

import com.restock.profiles.domain.model.Profile;

import java.util.List;
import java.util.Optional;

/** Port — domain boundary for profile persistence. No framework dependency. */
public interface ProfileRepository {
    List<Profile> findAll();
    Optional<Profile> findById(String id);
    List<Profile> findByUserId(String userId);
    Profile save(Profile profile);
    void deleteById(String id);
    long count();
}
