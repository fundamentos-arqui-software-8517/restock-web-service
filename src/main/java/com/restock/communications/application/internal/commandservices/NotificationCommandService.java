package com.restock.communications.application.internal.commandservices;

import com.restock.communications.domain.model.Notification;
import com.restock.communications.domain.repositories.NotificationRepository;
import com.restock.communications.interfaces.rest.resources.CreateNotificationResource;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationCommandService {

    private final NotificationRepository repository;

    public NotificationCommandService(NotificationRepository repository) {
        this.repository = repository;
    }

    public Notification create(CreateNotificationResource resource) {
        Notification n = Notification.builder()
            .businessId(resource.businessId())
            .title(resource.title())
            .message(resource.message())
            .type(resource.type() != null ? resource.type() : "INFO")
            .read(false)
            .relatedEntityId(resource.relatedEntityId())
            .build();
        return repository.save(n);
    }

    public Optional<Notification> markAsRead(String id) {
        return repository.findById(id).map(existing -> {
            existing.setRead(true);
            return repository.save(existing);
        });
    }
}
