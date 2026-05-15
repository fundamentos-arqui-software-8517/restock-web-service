package com.restock.arm.domain.model;

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
@Document(collection = "custom_supplies")
public class CustomSupply extends AggregateRoot {
    private String supplyId;
    private String businessId;
    private String nameOverride;
    private String subtitle;
    private String category;
    private String uomLabel;
    private boolean perishable;
    private double minStock;
    private double maxStock;
}
