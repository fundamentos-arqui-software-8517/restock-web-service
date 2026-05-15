package com.restock.resource.domain.model;

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
@Document(collection = "supplies")
public class Supply extends AggregateRoot {
    private String name;
    private String description;
    private String category;
    private String uomLabel;
    private boolean perishable;
}
