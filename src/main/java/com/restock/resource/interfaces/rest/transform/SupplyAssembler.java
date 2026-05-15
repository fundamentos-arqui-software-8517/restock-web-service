package com.restock.resource.interfaces.rest.transform;

import com.restock.resource.domain.model.Supply;
import com.restock.resource.interfaces.rest.resources.CreateSupplyResource;
import com.restock.resource.interfaces.rest.resources.SupplyResource;
import org.springframework.stereotype.Component;

@Component
public class SupplyAssembler {

    public SupplyResource toResource(Supply supply) {
        return new SupplyResource(supply.getId(), supply.getName(), supply.getDescription(),
            supply.getCategory(), supply.getUomLabel(), supply.isPerishable());
    }

    public Supply toEntity(CreateSupplyResource resource) {
        return Supply.builder()
            .name(resource.name()).description(resource.description())
            .category(resource.category()).uomLabel(resource.uomLabel())
            .perishable(resource.perishable()).build();
    }
}
