package com.uitopic.restock.platform.resources.infrastructure.config;

import com.uitopic.restock.platform.resources.domain.model.aggregates.CustomSupply;
import com.uitopic.restock.platform.resources.domain.model.entities.Supply;
import com.uitopic.restock.platform.resources.domain.model.valueobjects.StockRange;
import com.uitopic.restock.platform.resources.interfaces.rest.resources.CustomSupplyResource;
import com.uitopic.restock.platform.resources.interfaces.rest.resources.SupplyResource;
import com.uitopic.restock.platform.shared.domain.model.valueobjects.AccountId;
import com.uitopic.restock.platform.shared.domain.model.valueobjects.Money;
import com.uitopic.restock.platform.shared.domain.model.valueobjects.UnitMeasurement;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CacheConfigTests {

    @Test
    void redisObjectMapperRoundTripsCacheableLists() throws Exception {
        var mapper = CacheConfig.redisObjectMapper();
        var supplies = new ArrayList<>(List.of(customSupply()));

        var serialized = mapper.writeValueAsBytes(supplies);
        var deserialized = mapper.readValue(serialized, Object.class);

        assertThat(deserialized)
                .isInstanceOf(List.class)
                .asList()
                .singleElement()
                .isInstanceOfSatisfying(CustomSupply.class, supply -> {
                    assertThat(supply.getId()).isEqualTo("custom-supply-id");
                    assertThat(supply.getAccountId().accountId()).isEqualTo("account-id");
                    assertThat(supply.getSupply().getId()).isEqualTo("supply-id");
                    assertThat(supply.getStockRange().minStock()).isEqualTo(10.0);
                    assertThat(supply.getUnitPrice().getCurrencyCode()).isEqualTo("PEN");
                });
    }

    @Test
    void redisObjectMapperRoundTripsRestResources() throws Exception {
        var mapper = CacheConfig.redisObjectMapper();
        var supplies = new ArrayList<>(List.of(customSupplyResource()));

        var serialized = mapper.writeValueAsBytes(supplies);
        var deserialized = mapper.readValue(serialized, Object.class);

        assertThat(deserialized)
                .isInstanceOf(List.class)
                .asList()
                .containsExactly(customSupplyResource());
    }

    private CustomSupply customSupply() {
        return CustomSupply.builder()
                .id("custom-supply-id")
                .accountId(new AccountId("account-id"))
                .name("Flour")
                .supplyId("supply-id")
                .supply(new Supply("supply-id", "Base flour", "Base wheat flour", "ingredients", false))
                .stockRange(new StockRange(10.0, 50.0))
                .unitPrice(new Money(new BigDecimal("12.50"), "PEN"))
                .description("Wheat flour")
                .unitMeasurement(new UnitMeasurement("kilogram", "kg"))
                .build();
    }

    private CustomSupplyResource customSupplyResource() {
        return new CustomSupplyResource(
                "custom-supply-id",
                "Flour",
                "Wheat flour",
                "12.50",
                "PEN",
                10.0,
                50.0,
                "kilogram",
                "kg",
                "https://example.com/flour.png",
                "flour-public-id",
                "account-id",
                new SupplyResource(
                        "supply-id",
                        "Base flour",
                        "Base wheat flour",
                        "ingredients",
                        false
                )
        );
    }
}
