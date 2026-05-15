package com.restock.sales.interfaces.acl;

import com.restock.analytics.application.outboundservices.acl.ExternalSalesContextFacade;
import com.restock.sales.domain.repositories.SaleRepository;
import org.springframework.stereotype.Service;

/**
 * ACL inbound adapter — Sales domain implements Analytics domain's outbound port.
 */
@Service
public class SalesContextFacade implements ExternalSalesContextFacade {

    private final SaleRepository saleRepository;

    public SalesContextFacade(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public long getTotalSalesCount() {
        return saleRepository.count();
    }

    @Override
    public double getTotalRevenue() {
        return saleRepository.findAll().stream()
            .mapToDouble(s -> s.getTotalAmount())
            .sum();
    }
}
