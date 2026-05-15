package com.restock.profiles.interfaces.rest.transform;

import com.restock.profiles.domain.model.Business;
import com.restock.profiles.interfaces.rest.resources.BusinessResource;
import com.restock.profiles.interfaces.rest.resources.CreateBusinessResource;
import org.springframework.stereotype.Component;

@Component
public class BusinessAssembler {

    public BusinessResource toResource(Business business) {
        return new BusinessResource(
            business.getId(),
            business.getUserId(),
            business.getRuc(),
            business.getPictureUrl(),
            business.getCompanyName(),
            business.getMainLocation()
        );
    }

    public Business toEntity(CreateBusinessResource resource) {
        return Business.builder()
            .userId(resource.userId())
            .ruc(resource.ruc())
            .pictureUrl(resource.pictureUrl())
            .companyName(resource.companyName())
            .mainLocation(resource.mainLocation())
            .build();
    }
}
