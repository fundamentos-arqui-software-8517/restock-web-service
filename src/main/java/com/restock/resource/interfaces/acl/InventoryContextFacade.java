package com.restock.resource.interfaces.acl;

import com.restock.analytics.application.outboundservices.acl.ExternalInventoryContextFacade;
import com.restock.resource.domain.model.Batch;
import com.restock.resource.domain.model.Supply;
import com.restock.resource.domain.repositories.BatchRepository;
import com.restock.resource.domain.repositories.SupplyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ACL inbound adapter — Resource domain implements Analytics domain's outbound port.
 * Translates analytics queries into resource domain language.
 */
@Service
public class InventoryContextFacade implements ExternalInventoryContextFacade {

    private final BatchRepository batchRepository;
    private final SupplyRepository supplyRepository;

    public InventoryContextFacade(BatchRepository batchRepository, SupplyRepository supplyRepository) {
        this.batchRepository = batchRepository;
        this.supplyRepository = supplyRepository;
    }

    @Override
    public long getTotalActiveBatches() {
        return batchRepository.count();
    }

    @Override
    public long getLowStockBatchCount() {
        return batchRepository.findAll().stream()
            .filter(b -> b.getCurrentQuantity() < b.getMinStock())
            .count();
    }

    @Override
    public long getNearExpiryBatchCount(int withinDays) {
        Map<String, Boolean> perishableMap = supplyRepository.findAll().stream()
            .collect(Collectors.toMap(Supply::getId, Supply::isPerishable));
        List<Batch> batches = batchRepository.findAll();
        return batches.stream().filter(b -> {
            boolean perishable = perishableMap.getOrDefault(b.getSupplyId(), false);
            if (!perishable || b.getExpirationDate() == null) return false;
            try {
                long days = ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.parse(b.getExpirationDate()));
                return days <= withinDays;
            } catch (Exception e) { return false; }
        }).count();
    }
}
