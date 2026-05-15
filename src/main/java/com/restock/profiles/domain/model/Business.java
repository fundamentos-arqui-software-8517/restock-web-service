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
@Document(collection = "businesses")
public class Business extends AggregateRoot {
    private String userId;
    private String ruc;
    private String pictureUrl;
    private String companyName;
    private String mainLocation;
}
