package com.restock.profiles.application.internal.commandservices;

import com.restock.profiles.domain.model.Profile;
import com.restock.profiles.domain.repositories.ProfileRepository;
import com.restock.profiles.interfaces.rest.resources.CreateProfileResource;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileCommandService {

    private final ProfileRepository profileRepository;

    public ProfileCommandService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile create(CreateProfileResource resource) {
        Profile profile = Profile.builder()
            .userId(resource.userId())
            .name(resource.name())
            .lastName(resource.lastName())
            .phoneNumber(resource.phoneNumber())
            .avatarUrl(resource.avatarUrl())
            .gender(resource.gender())
            .birthDate(resource.birthDate())
            .build();
        return profileRepository.save(profile);
    }

    public Optional<Profile> update(String id, CreateProfileResource resource) {
        return profileRepository.findById(id).map(existing -> {
            existing.setUserId(resource.userId());
            existing.setName(resource.name());
            existing.setLastName(resource.lastName());
            existing.setPhoneNumber(resource.phoneNumber());
            existing.setAvatarUrl(resource.avatarUrl());
            existing.setGender(resource.gender());
            existing.setBirthDate(resource.birthDate());
            return profileRepository.save(existing);
        });
    }

    public void delete(String id) {
        profileRepository.deleteById(id);
    }
}
