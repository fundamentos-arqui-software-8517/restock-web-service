package com.restock.profiles.interfaces.rest.transform;

import com.restock.profiles.domain.model.Profile;
import com.restock.profiles.interfaces.rest.resources.CreateProfileResource;
import com.restock.profiles.interfaces.rest.resources.ProfileResource;
import org.springframework.stereotype.Component;

@Component
public class ProfileAssembler {

    public ProfileResource toResource(Profile profile) {
        return new ProfileResource(
            profile.getId(),
            profile.getUserId(),
            profile.getName(),
            profile.getLastName(),
            profile.getPhoneNumber(),
            profile.getAvatarUrl(),
            profile.getGender(),
            profile.getBirthDate()
        );
    }

    public Profile toEntity(CreateProfileResource resource) {
        return Profile.builder()
            .userId(resource.userId())
            .name(resource.name())
            .lastName(resource.lastName())
            .phoneNumber(resource.phoneNumber())
            .avatarUrl(resource.avatarUrl())
            .gender(resource.gender())
            .birthDate(resource.birthDate())
            .build();
    }
}
