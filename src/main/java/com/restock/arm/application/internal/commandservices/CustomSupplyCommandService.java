package com.restock.arm.application.internal.commandservices;

import com.restock.arm.domain.model.CustomSupply;
import com.restock.arm.domain.repositories.CustomSupplyRepository;
import com.restock.arm.interfaces.rest.resources.CreateCustomSupplyResource;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomSupplyCommandService {

    private final CustomSupplyRepository repository;

    public CustomSupplyCommandService(CustomSupplyRepository repository) {
        this.repository = repository;
    }

    public CustomSupply create(CreateCustomSupplyResource resource) {
        CustomSupply cs = CustomSupply.builder()
            .supplyId(resource.supplyId())
            .businessId(resource.businessId())
            .nameOverride(resource.nameOverride())
            .subtitle(resource.subtitle())
            .category(resource.category())
            .uomLabel(resource.uomLabel())
            .perishable(resource.perishable())
            .minStock(resource.minStock())
            .maxStock(resource.maxStock())
            .build();
        return repository.save(cs);
    }

    public Optional<CustomSupply> update(String id, CreateCustomSupplyResource resource) {
        return repository.findById(id).map(existing -> {
            existing.setSupplyId(resource.supplyId());
            existing.setBusinessId(resource.businessId());
            existing.setNameOverride(resource.nameOverride());
            existing.setSubtitle(resource.subtitle());
            existing.setCategory(resource.category());
            existing.setUomLabel(resource.uomLabel());
            existing.setPerishable(resource.perishable());
            existing.setMinStock(resource.minStock());
            existing.setMaxStock(resource.maxStock());
            return repository.save(existing);
        });
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
