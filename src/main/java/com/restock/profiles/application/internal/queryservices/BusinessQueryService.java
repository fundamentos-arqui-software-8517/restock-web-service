package com.restock.profiles.application.internal.queryservices;

import com.restock.profiles.domain.model.Business;
import com.restock.profiles.domain.repositories.BusinessRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessQueryService {

    private final BusinessRepository businessRepository;

    public BusinessQueryService(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    public List<Business> findAll() {
        return businessRepository.findAll();
    }

    public Optional<Business> findById(String id) {
        return businessRepository.findById(id);
    }
}
