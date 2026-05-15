package com.restock.resource.application.internal.commandservices;

import com.restock.resource.domain.model.Supply;
import com.restock.resource.domain.repositories.SupplyRepository;
import com.restock.resource.interfaces.rest.resources.CreateSupplyResource;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SupplyCommandService {

    private final SupplyRepository supplyRepository;

    public SupplyCommandService(SupplyRepository supplyRepository) {
        this.supplyRepository = supplyRepository;
    }

    public Supply create(CreateSupplyResource resource) {
        Supply supply = Supply.builder()
            .name(resource.name())
            .description(resource.description())
            .category(resource.category())
            .uomLabel(resource.uomLabel())
            .perishable(resource.perishable())
            .build();
        return supplyRepository.save(supply);
    }

    public Optional<Supply> update(String id, CreateSupplyResource resource) {
        return supplyRepository.findById(id).map(existing -> {
            existing.setName(resource.name());
            existing.setDescription(resource.description());
            existing.setCategory(resource.category());
            existing.setUomLabel(resource.uomLabel());
            existing.setPerishable(resource.perishable());
            return supplyRepository.save(existing);
        });
    }

    public void delete(String id) {
        supplyRepository.deleteById(id);
    }
}
