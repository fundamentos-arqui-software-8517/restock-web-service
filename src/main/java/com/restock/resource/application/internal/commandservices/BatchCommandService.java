package com.restock.resource.application.internal.commandservices;

import com.restock.resource.domain.model.Batch;
import com.restock.resource.domain.repositories.BatchRepository;
import com.restock.resource.interfaces.rest.resources.CreateBatchResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BatchCommandService {

    private final BatchRepository batchRepository;

    public BatchCommandService(BatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    public Batch create(CreateBatchResource resource) {
        Batch batch = Batch.builder()
            .supplyId(resource.supplyId())
            .branchId(resource.branchId())
            .currentQuantity(resource.currentQuantity())
            .expirationDate(resource.expirationDate())
            .minStock(resource.minStock())
            .maxStock(resource.maxStock())
            .build();
        return batchRepository.save(batch);
    }

    public Optional<Batch> update(String id, CreateBatchResource resource) {
        return batchRepository.findById(id).map(existing -> {
            existing.setSupplyId(resource.supplyId());
            existing.setBranchId(resource.branchId());
            existing.setCurrentQuantity(resource.currentQuantity());
            existing.setExpirationDate(resource.expirationDate());
            existing.setMinStock(resource.minStock());
            existing.setMaxStock(resource.maxStock());
            return batchRepository.save(existing);
        });
    }

    public Optional<Batch> updateQuantity(String id, double newQuantity) {
        return batchRepository.findById(id).map(existing -> {
            existing.setCurrentQuantity(newQuantity);
            return batchRepository.save(existing);
        });
    }

    public void delete(String id) {
        batchRepository.deleteById(id);
    }

    public List<Batch> findAll() {
        return batchRepository.findAll();
    }

    public Optional<Batch> findById(String id) {
        return batchRepository.findById(id);
    }
}
