package com.restock.resource.application.internal.queryservices;

import com.restock.resource.domain.model.Supply;
import com.restock.resource.domain.repositories.BatchRepository;
import com.restock.resource.domain.repositories.SupplyRepository;
import com.restock.resource.interfaces.rest.resources.BatchInventoryRootResource;
import com.restock.resource.interfaces.rest.transform.BatchInventoryAssembler;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BatchInventoryQueryService {

    private final BatchRepository batchRepository;
    private final SupplyRepository supplyRepository;
    private final BatchInventoryAssembler assembler;

    public BatchInventoryQueryService(BatchRepository batchRepository,
                                       SupplyRepository supplyRepository,
                                       BatchInventoryAssembler assembler) {
        this.batchRepository = batchRepository;
        this.supplyRepository = supplyRepository;
        this.assembler = assembler;
    }

    public BatchInventoryRootResource getBatchInventory() {
        Map<String, Supply> supplyMap = supplyRepository.findAll()
            .stream().collect(Collectors.toMap(Supply::getId, s -> s));
        return assembler.toRootResource(batchRepository.findAll(), supplyMap);
    }
}
