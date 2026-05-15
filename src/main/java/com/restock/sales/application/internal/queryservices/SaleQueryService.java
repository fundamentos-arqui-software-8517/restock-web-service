package com.restock.sales.application.internal.queryservices;

import com.restock.sales.domain.model.Sale;
import com.restock.sales.domain.repositories.SaleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaleQueryService {

    private final SaleRepository saleRepository;

    public SaleQueryService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public List<Sale> findAll() { return saleRepository.findAll(); }
    public Optional<Sale> findById(String id) { return saleRepository.findById(id); }
    public List<Sale> findByBusinessId(String businessId) { return saleRepository.findByBusinessId(businessId); }
}
