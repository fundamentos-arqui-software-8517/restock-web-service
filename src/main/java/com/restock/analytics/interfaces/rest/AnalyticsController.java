package com.restock.analytics.interfaces.rest;

import com.restock.analytics.application.internal.queryservices.AnalyticsQueryService;
import com.restock.analytics.interfaces.rest.resources.DashboardResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/analytics")
@Tag(name = "Analytics", description = "Business analytics and dashboard metrics")
@CrossOrigin(origins = "*")
public class AnalyticsController {

    private final AnalyticsQueryService queryService;

    public AnalyticsController(AnalyticsQueryService queryService) {
        this.queryService = queryService;
    }

    @Operation(summary = "Get dashboard analytics summary")
    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResource> getDashboard() {
        return ResponseEntity.ok(queryService.getDashboard());
    }
}
