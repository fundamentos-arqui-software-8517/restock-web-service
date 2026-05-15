package com.restock.sales.application.internal.commandservices;

import com.restock.sales.domain.model.Sale;
import com.restock.sales.domain.repositories.SaleRepository;
import com.restock.sales.interfaces.rest.resources.CreateSaleResource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleCommandService {

    private final SaleRepository saleRepository;

    public SaleCommandService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public Sale register(CreateSaleResource resource) {
        List<Sale.SaleItem> items = resource.items().stream()
            .map(i -> Sale.SaleItem.builder()
                .supplyId(i.supplyId()).supplyName(i.supplyName())
                .quantity(i.quantity()).unitPrice(i.unitPrice())
                .subtotal(i.quantity() * i.unitPrice()).build())
            .toList();
        double total = items.stream().mapToDouble(Sale.SaleItem::getSubtotal).sum();
        Sale sale = Sale.builder()
            .businessId(resource.businessId())
            .branchId(resource.branchId())
            .totalAmount(total)
            .items(items)
            .build();
        return saleRepository.save(sale);
    }
}
