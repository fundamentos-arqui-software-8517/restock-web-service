package com.restock.iam.application.internal.commandservices;

import com.restock.iam.domain.model.User;
import com.restock.iam.domain.repositories.UserRepository;
import com.restock.iam.interfaces.rest.resources.SignInResource;
import com.restock.iam.interfaces.rest.resources.SignUpResource;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCommandService {

    private final UserRepository userRepository;

    public UserCommandService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(SignUpResource resource) {
        if (userRepository.existsByEmail(resource.email())) {
            throw new IllegalStateException("Email already registered: " + resource.email());
        }
        User user = User.builder()
            .email(resource.email())
            .passwordHash(resource.password())
            .build();
        return userRepository.save(user);
    }

    public Optional<User> authenticate(SignInResource resource) {
        return userRepository.findByEmail(resource.email())
            .filter(u -> u.getPasswordHash().equals(resource.password()));
    }
}
