package com.uitopic.restock.platform.resources.interfaces.rest.controllers;

import com.uitopic.restock.platform.resources.domain.model.aggregates.CustomSupply;
import com.uitopic.restock.platform.resources.domain.model.entities.Supply;
import com.uitopic.restock.platform.resources.domain.model.queries.GetAllCustomSuppliesQuery;
import com.uitopic.restock.platform.resources.domain.model.queries.GetCustomSuppliesByAccountIdQuery;
import com.uitopic.restock.platform.resources.domain.model.queries.GetCustomSupplyByIdQuery;
import com.uitopic.restock.platform.resources.domain.model.valueobjects.StockRange;
import com.uitopic.restock.platform.resources.domain.services.CustomSupplyCommandService;
import com.uitopic.restock.platform.resources.domain.services.CustomSupplyQueryService;
import com.uitopic.restock.platform.shared.domain.model.valueobjects.AccountId;
import com.uitopic.restock.platform.shared.domain.model.valueobjects.Money;
import com.uitopic.restock.platform.shared.domain.model.valueobjects.UnitMeasurement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomSuppliesControllerTests {

    private CustomSupplyCommandService commandService;
    private CustomSupplyQueryService queryService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        commandService = mock(CustomSupplyCommandService.class);
        queryService = mock(CustomSupplyQueryService.class);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new CustomSuppliesController(commandService, queryService))
                .build();
    }

    @Test
    void getAll_withoutAccountId_returnsAllCustomSupplies() throws Exception {
        when(queryService.handle(any(GetAllCustomSuppliesQuery.class)))
                .thenReturn(List.of(customSupply("custom-supply-1", "account-1", "Flour")));

        mockMvc.perform(get("/api/v1/custom-supplies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("custom-supply-1"))
                .andExpect(jsonPath("$[0].name").value("Flour"))
                .andExpect(jsonPath("$[0].accountId").value("account-1"))
                .andExpect(jsonPath("$[0].supply.id").value("supply-custom-supply-1"));

        verify(queryService).handle(any(GetAllCustomSuppliesQuery.class));
    }

    @Test
    void getAll_withAccountId_returnsAccountScopedCustomSupplies() throws Exception {
        when(queryService.handle(any(GetCustomSuppliesByAccountIdQuery.class)))
                .thenReturn(List.of(customSupply("custom-supply-2", "account-2", "Sugar")));

        mockMvc.perform(get("/api/v1/custom-supplies").param("accountId", "account-2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("custom-supply-2"))
                .andExpect(jsonPath("$[0].name").value("Sugar"))
                .andExpect(jsonPath("$[0].accountId").value("account-2"));

        verify(queryService).handle(argThat((GetCustomSuppliesByAccountIdQuery query) ->
                query.accountId().accountId().equals("account-2")
        ));
    }

    @Test
    void getById_whenCustomSupplyExists_returnsCustomSupply() throws Exception {
        when(queryService.handle(any(GetCustomSupplyByIdQuery.class)))
                .thenReturn(Optional.of(customSupply("custom-supply-3", "account-3", "Coffee")));

        mockMvc.perform(get("/api/v1/custom-supplies/custom-supply-3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("custom-supply-3"))
                .andExpect(jsonPath("$.name").value("Coffee"))
                .andExpect(jsonPath("$.accountId").value("account-3"));

        verify(queryService).handle(argThat((GetCustomSupplyByIdQuery query) ->
                query.customSupplyId().equals("custom-supply-3")
        ));
    }

    @Test
    void getById_whenCustomSupplyDoesNotExist_returnsNotFound() throws Exception {
        when(queryService.handle(any(GetCustomSupplyByIdQuery.class)))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/custom-supplies/missing-id"))
                .andExpect(status().isNotFound());
    }

    private CustomSupply customSupply(String id, String accountId, String name) {
        return CustomSupply.builder()
                .id(id)
                .accountId(new AccountId(accountId))
                .name(name)
                .supplyId("supply-" + id)
                .supply(new Supply("supply-" + id, "Base " + name, "Base " + name, "ingredients", false))
                .stockRange(new StockRange(5.0, 50.0))
                .unitPrice(new Money(new BigDecimal("12.50"), "PEN"))
                .description(name + " description")
                .unitMeasurement(new UnitMeasurement("kilogram", "kg"))
                .build();
    }
}
