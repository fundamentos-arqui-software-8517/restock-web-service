package com.restock.communications.interfaces.rest;

import com.restock.communications.application.internal.commandservices.NotificationCommandService;
import com.restock.communications.application.internal.queryservices.NotificationQueryService;
import com.restock.communications.domain.model.Notification;
import com.restock.communications.interfaces.rest.resources.CreateNotificationResource;
import com.restock.communications.interfaces.rest.resources.NotificationResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@Tag(name = "Communications - Notifications", description = "Notification management")
@CrossOrigin(origins = "*")
public class NotificationController {

    private final NotificationCommandService commandService;
    private final NotificationQueryService queryService;

    public NotificationController(NotificationCommandService commandService, NotificationQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @Operation(summary = "Get notifications (optionally filtered by businessId)")
    @GetMapping
    public ResponseEntity<List<NotificationResource>> getAll(
        @RequestParam(required = false) String businessId,
        @RequestParam(required = false, defaultValue = "false") boolean unreadOnly) {
        List<Notification> results = businessId != null
            ? (unreadOnly ? queryService.findUnreadByBusinessId(businessId) : queryService.findByBusinessId(businessId))
            : queryService.findAll();
        return ResponseEntity.ok(results.stream().map(this::toResource).toList());
    }

    @Operation(summary = "Get notification by ID")
    @GetMapping("/{id}")
    public ResponseEntity<NotificationResource> getById(@PathVariable String id) {
        return queryService.findById(id).map(n -> ResponseEntity.ok(toResource(n))).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a notification")
    @PostMapping
    public ResponseEntity<NotificationResource> create(@Valid @RequestBody CreateNotificationResource resource) {
        return ResponseEntity.ok(toResource(commandService.create(resource)));
    }

    @Operation(summary = "Mark notification as read")
    @PatchMapping("/{id}/read")
    public ResponseEntity<NotificationResource> markAsRead(@PathVariable String id) {
        return commandService.markAsRead(id).map(n -> ResponseEntity.ok(toResource(n))).orElse(ResponseEntity.notFound().build());
    }

    private NotificationResource toResource(Notification n) {
        return new NotificationResource(n.getId(), n.getBusinessId(), n.getTitle(), n.getMessage(),
            n.getType(), n.isRead(), n.getCreatedAt(), n.getRelatedEntityId());
    }
}
