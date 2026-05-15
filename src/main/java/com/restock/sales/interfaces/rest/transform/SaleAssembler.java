package com.restock.sales.interfaces.rest.transform;

import com.restock.sales.domain.model.Sale;
import com.restock.sales.interfaces.rest.resources.SaleItemResource;
import com.restock.sales.interfaces.rest.resources.SaleResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SaleAssembler {

    public SaleResource toResource(Sale sale) {
        List<SaleItemResource> items = sale.getItems() == null ? List.of() :
            sale.getItems().stream()
                .map(i -> new SaleItemResource(i.getSupplyId(), i.getSupplyName(),
                    i.getQuantity(), i.getUnitPrice(), i.getSubtotal()))
                .toList();
        return new SaleResource(sale.getId(), sale.getBusinessId(), sale.getBranchId(),
            sale.getSoldAt(), sale.getTotalAmount(), items);
    }
}
