package com.restock.resource.interfaces.rest.transform;

import com.restock.resource.domain.model.Batch;
import com.restock.resource.domain.model.Supply;
import com.restock.resource.interfaces.rest.resources.BatchInventoryBatchResource;
import com.restock.resource.interfaces.rest.resources.BatchInventoryPayloadResource;
import com.restock.resource.interfaces.rest.resources.BatchInventoryRootResource;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Component
public class BatchInventoryAssembler {

    public BatchInventoryRootResource toRootResource(List<Batch> batches, Map<String, Supply> supplyMap) {
        List<BatchInventoryBatchResource> rows = batches.stream()
            .map(b -> toBatchRow(b, supplyMap.get(b.getSupplyId())))
            .toList();

        int total = rows.size();
        long nearExpiry = rows.stream()
            .filter(r -> r.isPerishable() && r.expirationDate() != null && daysUntil(r.expirationDate()) <= 30)
            .count();

        return new BatchInventoryRootResource(
            new BatchInventoryPayloadResource(total, 12.0, (int) nearExpiry, rows)
        );
    }

    public BatchInventoryBatchResource toBatchRow(Batch batch, Supply supply) {
        if (supply == null) {
            return new BatchInventoryBatchResource(batch.getId(), "Unknown", null,
                "Unknown", "units", batch.getExpirationDate(), batch.getCurrentQuantity(),
                false, "neutral", rowHighlight(batch), batch.getMinStock(), batch.getMaxStock());
        }
        return new BatchInventoryBatchResource(
            batch.getId(), supply.getName(), null, supply.getCategory(), supply.getUomLabel(),
            batch.getExpirationDate(), batch.getCurrentQuantity(), supply.isPerishable(),
            badgeTone(supply.isPerishable(), batch.getExpirationDate()),
            rowHighlight(batch), batch.getMinStock(), batch.getMaxStock()
        );
    }

    private String badgeTone(boolean perishable, String expirationDate) {
        if (!perishable || expirationDate == null) return "neutral";
        long days = daysUntil(expirationDate);
        if (days <= 7) return "warning";
        if (days <= 30) return "info";
        return "neutral";
    }

    private String rowHighlight(Batch batch) {
        return batch.getCurrentQuantity() < batch.getMinStock() ? "warning" : null;
    }

    private long daysUntil(String isoDate) {
        try { return ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.parse(isoDate)); }
        catch (Exception e) { return Long.MAX_VALUE; }
    }
}
