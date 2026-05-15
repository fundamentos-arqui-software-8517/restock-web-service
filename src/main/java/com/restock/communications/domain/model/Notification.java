package com.restock.communications.domain.model;

import com.restock.shared.domain.model.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "notifications")
public class Notification extends AggregateRoot {
    private String businessId;
    private String title;
    private String message;
    private String type;
    private boolean read;
    @Builder.Default
    private String createdAt = Instant.now().toString();
    private String relatedEntityId;
}
