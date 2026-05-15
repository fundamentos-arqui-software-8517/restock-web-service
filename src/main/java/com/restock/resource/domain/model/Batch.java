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
@Document(collection = "batches")
public class Batch extends AggregateRoot {
    private String supplyId;
    private String branchId;
    private double currentQuantity;
    private String expirationDate;
    private double minStock;
    private double maxStock;
}
