package com.restock.resource.interfaces.rest.transform;

import com.restock.resource.domain.model.Batch;
import com.restock.resource.interfaces.rest.resources.BatchResource;
import com.restock.resource.interfaces.rest.resources.CreateBatchResource;
import org.springframework.stereotype.Component;

@Component
public class BatchAssembler {

    public BatchResource toResource(Batch batch) {
        return new BatchResource(batch.getId(), batch.getSupplyId(), batch.getBranchId(),
            batch.getCurrentQuantity(), batch.getExpirationDate(), batch.getMinStock(), batch.getMaxStock());
    }

    public Batch toEntity(CreateBatchResource resource) {
        return Batch.builder()
            .supplyId(resource.supplyId()).branchId(resource.branchId())
            .currentQuantity(resource.currentQuantity()).expirationDate(resource.expirationDate())
            .minStock(resource.minStock()).maxStock(resource.maxStock()).build();
    }
}
