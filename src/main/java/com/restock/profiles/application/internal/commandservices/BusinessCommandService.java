package com.restock.profiles.application.internal.commandservices;

import com.restock.profiles.domain.model.Business;
import com.restock.profiles.domain.repositories.BusinessRepository;
import com.restock.profiles.interfaces.rest.resources.CreateBusinessResource;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BusinessCommandService {

    private final BusinessRepository businessRepository;

    public BusinessCommandService(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    public Business create(CreateBusinessResource resource) {
        Business business = Business.builder()
            .userId(resource.userId())
            .ruc(resource.ruc())
            .pictureUrl(resource.pictureUrl())
            .companyName(resource.companyName())
            .mainLocation(resource.mainLocation())
            .build();
        return businessRepository.save(business);
    }

    public Optional<Business> update(String id, CreateBusinessResource resource) {
        return businessRepository.findById(id).map(existing -> {
            existing.setUserId(resource.userId());
            existing.setRuc(resource.ruc());
            existing.setPictureUrl(resource.pictureUrl());
            existing.setCompanyName(resource.companyName());
            existing.setMainLocation(resource.mainLocation());
            return businessRepository.save(existing);
        });
    }

    public void delete(String id) {
        businessRepository.deleteById(id);
    }
}
