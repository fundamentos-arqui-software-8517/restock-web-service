package com.uitopic.restock.platform.resources.application.internal.queryservices;

import com.uitopic.restock.platform.resources.domain.model.aggregates.CustomSupply;
import com.uitopic.restock.platform.resources.domain.model.entities.Supply;
import com.uitopic.restock.platform.resources.domain.model.queries.GetAllCustomSuppliesQuery;
import com.uitopic.restock.platform.resources.domain.model.queries.GetCustomSuppliesByAccountIdQuery;
import com.uitopic.restock.platform.resources.domain.model.valueobjects.StockRange;
import com.uitopic.restock.platform.resources.domain.repositories.CustomSupplyRepository;
import com.uitopic.restock.platform.resources.domain.services.CustomSupplyQueryService;
import com.uitopic.restock.platform.shared.domain.model.valueobjects.AccountId;
import com.uitopic.restock.platform.shared.domain.model.valueobjects.Money;
import com.uitopic.restock.platform.shared.domain.model.valueobjects.UnitMeasurement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(classes = {
        CustomSupplyQueryServiceImpl.class,
        CustomSupplyQueryServiceCacheIntegrationTests.CacheTestConfiguration.class
})
class CustomSupplyQueryServiceCacheIntegrationTests {

    private final CustomSupplyQueryService queryService;
    private final CustomSupplyRepository repository;
    private final CacheManager cacheManager;

    @Autowired
    CustomSupplyQueryServiceCacheIntegrationTests(
            CustomSupplyQueryService queryService,
            CustomSupplyRepository repository,
            CacheManager cacheManager
    ) {
        this.queryService = queryService;
        this.repository = repository;
        this.cacheManager = cacheManager;
    }

    @BeforeEach
    void setUp() {
        reset(repository);
        clearCache("custom-supplies-all");
        clearCache("custom-supplies-by-account");
    }

    @Test
    void handleGetAll_usesCacheAfterFirstRepositoryRead() {
        var customSupply = customSupply("custom-supply-1", "account-1", "Flour");
        when(repository.findAll()).thenReturn(List.of(customSupply));

        var firstResult = queryService.handle(new GetAllCustomSuppliesQuery());
        var secondResult = queryService.handle(new GetAllCustomSuppliesQuery());

        assertThat(firstResult).hasSize(1);
        assertThat(secondResult).hasSize(1);
        assertThat(secondResult.getFirst().getId()).isEqualTo("custom-supply-1");
        verify(repository, times(1)).findAll();
    }

    @Test
    void handleGetByAccount_usesAccountSpecificCacheKey() {
        var accountId = new AccountId("account-2");
        var customSupply = customSupply("custom-supply-2", accountId.accountId(), "Sugar");
        when(repository.findByAccountId(accountId)).thenReturn(List.of(customSupply));

        var firstResult = queryService.handle(new GetCustomSuppliesByAccountIdQuery(accountId));
        var secondResult = queryService.handle(new GetCustomSuppliesByAccountIdQuery(accountId));

        assertThat(firstResult).hasSize(1);
        assertThat(secondResult).hasSize(1);
        assertThat(secondResult.getFirst().getAccountId()).isEqualTo(accountId);
        verify(repository, times(1)).findByAccountId(accountId);
    }

    private void clearCache(String cacheName) {
        var cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        }
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

    @Configuration
    @EnableCaching
    static class CacheTestConfiguration {

        @Bean
        CustomSupplyRepository customSupplyRepository() {
            return mock(CustomSupplyRepository.class);
        }

        @Bean
        CacheManager cacheManager() {
            return new ConcurrentMapCacheManager("custom-supplies-all", "custom-supplies-by-account");
        }
    }
}
