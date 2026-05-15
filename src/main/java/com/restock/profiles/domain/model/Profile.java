package com.restock.profiles.domain.model;

import com.restock.shared.domain.model.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "profiles")
public class Profile extends AggregateRoot {
    private String userId;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String avatarUrl;
    private String gender;
    private String birthDate;
}
