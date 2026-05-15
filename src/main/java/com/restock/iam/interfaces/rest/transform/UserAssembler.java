package com.restock.iam.interfaces.rest.transform;

import com.restock.iam.domain.model.User;
import com.restock.iam.interfaces.rest.resources.AuthenticatedUserResource;
import org.springframework.stereotype.Component;

@Component
public class UserAssembler {

    public AuthenticatedUserResource toResource(User user) {
        return new AuthenticatedUserResource(
            user.getId(),
            user.getEmail(),
            user.getRole(),
            "token-" + user.getId()
        );
    }
}
