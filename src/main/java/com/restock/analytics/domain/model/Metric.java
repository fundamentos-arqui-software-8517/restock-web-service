package com.restock.analytics.domain.model;

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
@Document(collection = "metrics")
public class Metric extends AggregateRoot {
    private String businessId;
    private String name;
    private double value;
    private String unit;
    private String period;
    private String recordedAt;
}
