package com.restock.arm.application.internal.queryservices;

import com.restock.arm.domain.model.CustomSupply;
import com.restock.arm.domain.repositories.CustomSupplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomSupplyQueryService {

    private final CustomSupplyRepository repository;

    public CustomSupplyQueryService(CustomSupplyRepository repository) {
        this.repository = repository;
    }

    public List<CustomSupply> findAll() {
        return repository.findAll();
    }

    public Optional<CustomSupply> findById(String id) {
        return repository.findById(id);
    }

    public List<CustomSupply> findByBusinessId(String businessId) {
        return repository.findByBusinessId(businessId);
    }
}
