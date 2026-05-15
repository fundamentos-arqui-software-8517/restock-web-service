package com.restock.sales.domain.model;

import com.restock.shared.domain.model.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "sales")
public class Sale extends AggregateRoot {
    private String businessId;
    private String branchId;
    @Builder.Default
    private String soldAt = Instant.now().toString();
    private double totalAmount;
    private List<SaleItem> items;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SaleItem {
        private String supplyId;
        private String supplyName;
        private double quantity;
        private double unitPrice;
        private double subtotal;
    }
}
