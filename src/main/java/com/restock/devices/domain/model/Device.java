package com.restock.devices.domain.model;

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
@Document(collection = "devices")
public class Device extends AggregateRoot {
    private String name;
    private String deviceKey;
    private String branchId;
    private String supplyId;
    @Builder.Default
    private String status = "ACTIVE";
    private String type;
    private String mqttTopic;
}
