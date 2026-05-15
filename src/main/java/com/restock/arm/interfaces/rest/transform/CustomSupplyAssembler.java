package com.restock.arm.interfaces.rest.transform;

import com.restock.arm.domain.model.CustomSupply;
import com.restock.arm.interfaces.rest.resources.CreateCustomSupplyResource;
import com.restock.arm.interfaces.rest.resources.CustomSupplyResource;
import org.springframework.stereotype.Component;

@Component
public class CustomSupplyAssembler {

    public CustomSupplyResource toResource(CustomSupply cs) {
        return new CustomSupplyResource(
            cs.getId(), cs.getSupplyId(), cs.getBusinessId(), cs.getNameOverride(),
            cs.getSubtitle(), cs.getCategory(), cs.getUomLabel(), cs.isPerishable(),
            cs.getMinStock(), cs.getMaxStock()
        );
    }

    public CustomSupply toEntity(CreateCustomSupplyResource resource) {
        return CustomSupply.builder()
            .supplyId(resource.supplyId()).businessId(resource.businessId())
            .nameOverride(resource.nameOverride()).subtitle(resource.subtitle())
            .category(resource.category()).uomLabel(resource.uomLabel())
            .perishable(resource.perishable()).minStock(resource.minStock()).maxStock(resource.maxStock())
            .build();
    }
}
